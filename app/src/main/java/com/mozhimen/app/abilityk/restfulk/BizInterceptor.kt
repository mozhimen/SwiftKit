package com.mozhimen.app.abilityk.restfulk

import android.util.Log
import com.mozhimen.abilityk.restfulk.commons._Interceptor

/**
 * @ClassName BizInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:07
 * @Version 1.0
 */
class BizInterceptor : _Interceptor {
    private val TAG = "BizInterceptor>>>>>"

    override fun intercept(IChain: _Interceptor.IChain): Boolean {
        if (IChain.isRequestPeriod) {
            val requestK = IChain.request()
            requestK.addHeaders("auth-token", "xxx")
        } else if (IChain.response() != null) {
            Log.d(TAG, "intercept: ${IChain.request().endPointUrl()}")
            Log.d(TAG, "intercept: ${IChain.response()!!.rawData}")
        }
        return false
    }
}