package com.mozhimen.abilityk.restfulk.helpers

import android.util.Log
import com.mozhimen.abilityk.cachek.CacheK
import com.mozhimen.abilityk.restfulk.annors.CacheStrategy
import com.mozhimen.abilityk.restfulk.commons.CallK
import com.mozhimen.abilityk.restfulk.commons.Callback
import com.mozhimen.abilityk.restfulk.commons.InterceptorK
import com.mozhimen.abilityk.restfulk.mos.RequestK
import com.mozhimen.abilityk.restfulk.mos.ResponseK
import com.mozhimen.basicsk.executork.ExecutorK
import com.mozhimen.basicsk.utilk.UtilKMHandler

/**
 * 代理CallFactory创建出来的Call对象, 从而实现拦截器的派发动作
 */
class Scheduler(
    private val callFactory: CallK.Factory,
    private val interceptors: MutableList<InterceptorK>
) {
    private val TAG = "Scheduler>>>>>"

    fun newCall(requestK: RequestK): CallK<*> {
        val newCall: CallK<*> = callFactory.newCall(requestK)
        return ProxyCall(newCall, requestK)
    }

    internal inner class ProxyCall<T>(
        private val delegate: CallK<T>,
        private val requestK: RequestK
    ) : CallK<T> {
        override fun execute(): ResponseK<T> {
            dispatchInterceptor(requestK, null)
            if (requestK.cacheStrategyK == CacheStrategy.CACHE_FIRST) {
                val cacheResponseK = readCache<T>(requestK.getCacheKey())
                if (cacheResponseK.data != null) {
                    return cacheResponseK
                }
            }

            val responseK = delegate.execute()
            saveCacheIfNeed(responseK)
            dispatchInterceptor(requestK, responseK)
            return responseK
        }

        override fun enqueue(callback: Callback<T>) {
            dispatchInterceptor(requestK, null)
            if (requestK.cacheStrategyK == CacheStrategy.CACHE_FIRST) {
                ExecutorK.execute({
                    val cacheResponseK = readCache<T>(requestK.getCacheKey())
                    if (cacheResponseK.data != null) {
                        //抛到主线程
                        UtilKMHandler.instance.sendAtFrontOfQueue {
                            callback.onSuccess(cacheResponseK)
                        }

                        Log.d(TAG, "enqueue: cache: " + requestK.getCacheKey())
                    }
                })
            }

            delegate.enqueue(object : Callback<T> {
                override fun onSuccess(responseK: ResponseK<T>) {
                    dispatchInterceptor(requestK, responseK)
                    saveCacheIfNeed(responseK)
                    callback.onSuccess(responseK)
                    Log.d(TAG, "enqueue remote ${requestK.getCacheKey()}")
                }

                override fun onFail(throwable: Throwable) {
                    callback.onFail(throwable)
                }
            })
        }

        private fun <T> readCache(cacheKey: String): ResponseK<T> {
            //cacheK 查询缓存 需要提供一个cache key
            //request de url+参数
            val cache = CacheK.getCache<T>(cacheKey)
            val cacheResponse = ResponseK<T>()
            cacheResponse.data = cache
            cacheResponse.code = ResponseK.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
        }

        private fun saveCacheIfNeed(response: ResponseK<T>) {
            if (requestK.cacheStrategyK == CacheStrategy.CACHE_FIRST
                || requestK.cacheStrategyK == CacheStrategy.NET_CACHE
            ) {
                if (response.data != null) {
                    ExecutorK.execute(runnable = {
                        CacheK.saveCache(requestK.getCacheKey(), response.data)
                    })
                }
            }
        }

        private fun dispatchInterceptor(requestK: RequestK, responseK: ResponseK<T>?) {
            if (interceptors.size <= 0)
                return
            InterceptorChain(requestK, responseK).dispatch()
        }

        inner class InterceptorChain(
            private val requestK: RequestK,
            private val responseK: ResponseK<T>?
        ) :
            InterceptorK.Chain {
            private var callIndex = 0

            override val isRequestPeriod: Boolean
                get() = responseK == null

            override fun request(): RequestK {
                return requestK
            }

            override fun response(): ResponseK<*>? {
                return responseK
            }

            fun dispatch() {
                val interceptor: InterceptorK = interceptors[callIndex]
                val intercept = interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }
        }
    }
}