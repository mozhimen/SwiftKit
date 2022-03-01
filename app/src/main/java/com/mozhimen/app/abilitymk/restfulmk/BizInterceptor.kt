package com.mozhimen.app.abilitymk.restfulmk

import android.util.Log
import com.mozhimen.abilitymk.restfulmk.commons.InterceptorMK

/**
 * @ClassName BizInterceptor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:07
 * @Version 1.0
 */
class BizInterceptor : InterceptorMK {
    private val TAG = "BizInterceptor>>>>>"
    override fun intercept(chain: InterceptorMK.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val requestMK = chain.request()
            requestMK.addHeaders("auth-token", "xxx")
        } else if (chain.response() != null) {
            Log.d(TAG, "intercept: ${chain.request().endPointUrl()}")
            Log.d(TAG, "intercept: ${chain.response()!!.rawData}")
        }
        return false
    }
}