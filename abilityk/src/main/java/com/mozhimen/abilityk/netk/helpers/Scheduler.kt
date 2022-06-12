package com.mozhimen.abilityk.netk.helpers

import android.util.Log
import com.mozhimen.basick.cachek.CacheK
import com.mozhimen.abilityk.netk.annors._CacheStrategy
import com.mozhimen.abilityk.netk.commons.INetKCall
import com.mozhimen.abilityk.netk.commons.INetKListener
import com.mozhimen.abilityk.netk.commons.INetKInterceptor
import com.mozhimen.abilityk.netk.mos.NetKRequest
import com.mozhimen.abilityk.netk.mos.NetKResponse
import com.mozhimen.basick.eventk.EventKHandler
import com.mozhimen.basick.executork.ExecutorK
import com.mozhimen.basick.extsk.sendMsgAtFrontOfQueue

/**
 * 代理CallFactory创建出来的Call对象, 从而实现拦截器的派发动作
 */
class Scheduler(
    private val _factory: INetKCall.INetKFactory,
    private val _interceptors: MutableList<INetKInterceptor>
) {
    private val TAG = "Scheduler>>>>>"

    fun newCall(request: NetKRequest): INetKCall<*> {
        val newCall: INetKCall<*> = _factory.newCall(request)
        return NetKCallProxy(newCall, request)
    }

    internal inner class NetKCallProxy<T>(
        private val _delegate: INetKCall<T>,
        private val _request: NetKRequest
    ) : INetKCall<T> {
        override fun execute(): NetKResponse<T> {
            dispatchInterceptor(_request, null)
            if (_request.cacheStrategy == _CacheStrategy.CACHE_FIRST) {
                val cacheResponse = readCache<T>(_request.getCacheKey())
                if (cacheResponse.data != null) {
                    return cacheResponse
                }
            }

            val response = _delegate.execute()
            saveCacheIfNeed(response)
            dispatchInterceptor(_request, response)
            return response
        }

        override fun enqueue(callback: INetKListener<T>) {
            dispatchInterceptor(_request, null)
            if (_request.cacheStrategy == _CacheStrategy.CACHE_FIRST) {
                ExecutorK.execute(TAG, runnable = {
                    val cacheResponseK = readCache<T>(_request.getCacheKey())
                    if (cacheResponseK.data != null) {
                        //抛到主线程
                        EventKHandler(this@Scheduler).sendMsgAtFrontOfQueue {
                            callback.onSuccess(
                                cacheResponseK
                            )
                        }
                        Log.d(TAG, "enqueue cacheStrategy key ${_request.getCacheKey()}")
                    }
                })
            }

            _delegate.enqueue(object : INetKListener<T> {
                override fun onSuccess(response: NetKResponse<T>) {
                    dispatchInterceptor(_request, response)
                    saveCacheIfNeed(response)
                    callback.onSuccess(response)
                    Log.d(TAG, "enqueue save cache cacheKey ${_request.getCacheKey()}")
                }

                override fun onFail(throwable: Throwable) {
                    callback.onFail(throwable)
                }
            })
        }

        private fun <T> readCache(cacheKey: String): NetKResponse<T> {
            //cacheK 查询缓存 需要提供一个cache key
            //request de url+参数
            val cache = CacheK.getCache<T>(cacheKey)
            val cacheResponse = NetKResponse<T>()
            cacheResponse.data = cache
            cacheResponse.code = StatusParser.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
        }

        private fun saveCacheIfNeed(response: NetKResponse<T>) {
            if (_request.cacheStrategy == _CacheStrategy.CACHE_FIRST
                || _request.cacheStrategy == _CacheStrategy.NET_CACHE
            ) {
                if (response.data != null) {
                    ExecutorK.execute(TAG, runnable = {
                        CacheK.saveCache(_request.getCacheKey(), response.data)
                    })
                }
            }
        }

        private fun dispatchInterceptor(request: NetKRequest, response: NetKResponse<T>?) {
            if (_interceptors.size <= 0)
                return
            InterceptorChain(request, response).dispatch()
        }

        inner class InterceptorChain(
            private val _request: NetKRequest,
            private val _response: NetKResponse<T>?
        ) :
            INetKInterceptor.INetKChain {
            private var _callIndex = 0//代表的是 分发的第几个拦截器

            override val isRequestPeriod: Boolean
                get() = _response == null

            override fun request(): NetKRequest {
                return _request
            }

            override fun response(): NetKResponse<*>? {
                return _response
            }

            fun dispatch() {
                val interceptor: INetKInterceptor = _interceptors[_callIndex]
                val intercept = interceptor.intercept(this)
                _callIndex++
                if (!intercept && _callIndex < _interceptors.size) {
                    dispatch()
                }
            }
        }
    }
}