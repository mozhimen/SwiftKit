package com.mozhimen.app.abilityk.restfulk

import android.util.Log
import com.mozhimen.abilityk.restfulk.commons.InterceptorK

/**
 * @ClassName BizInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:07
 * @Version 1.0
 */
class BizInterceptor : InterceptorK {
    private val TAG = "BizInterceptor>>>>>"

    override fun intercept(chain: InterceptorK.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val requestK = chain.request()
            requestK.addHeaders("auth-token", "xxx")
        } else if (chain.response() != null) {
            Log.d(TAG, "intercept: ${chain.request().endPointUrl()}")
            Log.d(TAG, "intercept: ${chain.response()!!.rawData}")
        }
        return false
    }
}