package com.mozhimen.abilitymk.restfulmk.helpers

import android.util.Log
import com.mozhimen.abilitymk.cachemk.CacheMK
import com.mozhimen.abilitymk.restfulmk.annors.CacheStrategyMK
import com.mozhimen.abilitymk.restfulmk.commons.CallMK
import com.mozhimen.abilitymk.restfulmk.commons.CallbackMK
import com.mozhimen.abilitymk.restfulmk.commons.InterceptorMK
import com.mozhimen.abilitymk.restfulmk.mos.RequestMK
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
import com.mozhimen.basicsmk.executormk.ExecutorMK
import com.mozhimen.basicsmk.utilmk.UtilMKMHandler

/**
 * 代理CallFactory创建出来的Call对象, 从而实现拦截器的派发动作
 */
class Scheduler(
    private val callFactory: CallMK.Factory,
    private val interceptors: MutableList<InterceptorMK>
) {
    private val TAG = "Scheduler>>>>>"

    fun newCall(requestMK: RequestMK): CallMK<*> {
        val newCall: CallMK<*> = callFactory.newCall(requestMK)
        return ProxyCall(newCall, requestMK)
    }

    internal inner class ProxyCall<T>(
        private val delegate: CallMK<T>,
        private val requestMK: RequestMK
    ) : CallMK<T> {
        override fun execute(): ResponseMK<T> {
            dispatchInterceptor(requestMK, null)
            if (requestMK.cacheStrategyMK == CacheStrategyMK.CACHE_FIRST) {
                val cacheResponseMK = readCache<T>(requestMK.getCacheKey())
                if (cacheResponseMK.data != null) {
                    return cacheResponseMK
                }
            }

            val responseMK = delegate.execute()
            saveCacheIfNeed(responseMK)
            dispatchInterceptor(requestMK, responseMK)
            return responseMK
        }

        override fun enqueue(callbackMK: CallbackMK<T>) {
            dispatchInterceptor(requestMK, null)
            if (requestMK.cacheStrategyMK == CacheStrategyMK.CACHE_FIRST) {
                ExecutorMK.execute({
                    val cacheResponseMK = readCache<T>(requestMK.getCacheKey())
                    if (cacheResponseMK.data != null) {
                        //抛到主线程
                        UtilMKMHandler.instance.sendAtFrontOfQueue {
                            callbackMK.onSuccess(cacheResponseMK)
                        }

                        Log.d(TAG, "enqueue: cache: " + requestMK.getCacheKey())
                    }
                })
            }

            delegate.enqueue(object : CallbackMK<T> {
                override fun onSuccess(responseMK: ResponseMK<T>) {
                    dispatchInterceptor(requestMK, responseMK)
                    saveCacheIfNeed(responseMK)
                    callbackMK.onSuccess(responseMK)
                    Log.d(TAG, "enqueue remote ${requestMK.getCacheKey()}")
                }

                override fun onFail(throwable: Throwable) {
                    callbackMK.onFail(throwable)
                }
            })
        }

        private fun <T> readCache(cacheKey: String): ResponseMK<T> {
            //cacheMK 查询缓存 需要提供一个cache key
            //request de url+参数
            val cache = CacheMK.getCache<T>(cacheKey)
            val cacheResponse = ResponseMK<T>()
            cacheResponse.data = cache
            cacheResponse.code = ResponseMK.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
        }

        private fun saveCacheIfNeed(response: ResponseMK<T>) {
            if (requestMK.cacheStrategyMK == CacheStrategyMK.CACHE_FIRST
                || requestMK.cacheStrategyMK == CacheStrategyMK.NET_CACHE
            ) {
                if (response.data != null) {
                    ExecutorMK.execute(runnable = {
                        CacheMK.saveCache(requestMK.getCacheKey(), response.data)
                    })
                }
            }
        }

        private fun dispatchInterceptor(requestMK: RequestMK, responseMK: ResponseMK<T>?) {
            if (interceptors.size <= 0)
                return
            InterceptorChain(requestMK, responseMK).dispatch()
        }

        inner class InterceptorChain(
            private val requestMK: RequestMK,
            private val responseMK: ResponseMK<T>?
        ) :
            InterceptorMK.Chain {
            private var callIndex = 0

            override val isRequestPeriod: Boolean
                get() = responseMK == null

            override fun request(): RequestMK {
                return requestMK
            }

            override fun response(): ResponseMK<*>? {
                return responseMK
            }

            fun dispatch() {
                val interceptor: InterceptorMK = interceptors[callIndex]
                val intercept = interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }
        }
    }
}