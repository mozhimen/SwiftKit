package com.mozhimen.abilityk.restfulk.helpers

import android.util.Log
import com.mozhimen.basicsk.cachek.CacheK
import com.mozhimen.abilityk.restfulk.annors._CacheStrategy
import com.mozhimen.abilityk.restfulk.commons._ICall
import com.mozhimen.abilityk.restfulk.commons._ICallback
import com.mozhimen.abilityk.restfulk.commons._Interceptor
import com.mozhimen.abilityk.restfulk.mos._Request
import com.mozhimen.abilityk.restfulk.mos._Response
import com.mozhimen.basicsk.executork.ExecutorK
import com.mozhimen.basicsk.extsk.sendAtFrontOfQueue
import com.mozhimen.basicsk.utilk.UtilKHandler

/**
 * 代理CallFactory创建出来的Call对象, 从而实现拦截器的派发动作
 */
class Scheduler(
    private val ICallIFactory: _ICall.IFactory,
    private val Interceptors: MutableList<_Interceptor>
) {
    private val TAG = "Scheduler>>>>>"

    fun newCall(request: _Request): _ICall<*> {
        val newICall: _ICall<*> = ICallIFactory.newCall(request)
        return ProxyICall(newICall, request)
    }

    internal inner class ProxyICall<T>(
        private val delegate: _ICall<T>,
        private val requestK: _Request
    ) : _ICall<T> {
        override fun execute(): _Response<T> {
            dispatchInterceptor(requestK, null)
            if (requestK.cacheStrategyK == _CacheStrategy.CACHE_FIRST) {
                val cacheResponseK = readCache<T>(requestK.getCacheKey())
                if (cacheResponseK.data != null) {
                    return cacheResponseK
                }
            }

            val response = delegate.execute()
            saveCacheIfNeed(response)
            dispatchInterceptor(requestK, response)
            return response
        }

        override fun enqueue(ICallback: _ICallback<T>) {
            dispatchInterceptor(requestK, null)
            if (requestK.cacheStrategyK == _CacheStrategy.CACHE_FIRST) {
                ExecutorK.execute({
                    val cacheResponseK = readCache<T>(requestK.getCacheKey())
                    if (cacheResponseK.data != null) {
                        //抛到主线程
                        UtilKHandler(this@Scheduler).sendAtFrontOfQueue { ICallback.onSuccess(cacheResponseK) }
                        Log.d(TAG, "enqueue: cache: " + requestK.getCacheKey())
                    }
                })
            }

            delegate.enqueue(object : _ICallback<T> {
                override fun onSuccess(response: _Response<T>) {
                    dispatchInterceptor(requestK, response)
                    saveCacheIfNeed(response)
                    ICallback.onSuccess(response)
                    Log.d(TAG, "enqueue remote ${requestK.getCacheKey()}")
                }

                override fun onFail(throwable: Throwable) {
                    ICallback.onFail(throwable)
                }
            })
        }

        private fun <T> readCache(cacheKey: String): _Response<T> {
            //cacheK 查询缓存 需要提供一个cache key
            //request de url+参数
            val cache = CacheK.getCache<T>(cacheKey)
            val cacheResponse = _Response<T>()
            cacheResponse.data = cache
            cacheResponse.code = _Response.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
        }

        private fun saveCacheIfNeed(response: _Response<T>) {
            if (requestK.cacheStrategyK == _CacheStrategy.CACHE_FIRST
                || requestK.cacheStrategyK == _CacheStrategy.NET_CACHE
            ) {
                if (response.data != null) {
                    ExecutorK.execute(runnable = {
                        CacheK.saveCache(requestK.getCacheKey(), response.data)
                    })
                }
            }
        }

        private fun dispatchInterceptor(requestK: _Request, response: _Response<T>?) {
            if (Interceptors.size <= 0)
                return
            InterceptorIChain(requestK, response).dispatch()
        }

        inner class InterceptorIChain(
            private val requestK: _Request,
            private val response: _Response<T>?
        ) :
            _Interceptor.IChain {
            private var callIndex = 0

            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): _Request {
                return requestK
            }

            override fun response(): _Response<*>? {
                return response
            }

            fun dispatch() {
                val Interceptor: _Interceptor = Interceptors[callIndex]
                val intercept = Interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < Interceptors.size) {
                    dispatch()
                }
            }
        }
    }
}