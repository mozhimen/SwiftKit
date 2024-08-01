package com.mozhimen.basick.utilk.android.webkit

import android.webkit.CookieManager
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode


/**
 * @ClassName UtilKCookieManager
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/31
 * @Version 1.0
 */
object UtilKCookieManager {
    @JvmStatic
    fun get(): CookieManager =
        CookieManager.getInstance()

    @JvmStatic
    fun setAcceptCookie(boolean: Boolean) {
        get().setAcceptCookie(boolean)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun removeAllCookies() {
        get().removeAllCookies(null)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun flush() {
        get().flush()
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun setAcceptThirdPartyCookies(webView: WebView, boolean: Boolean) {
        get().setAcceptThirdPartyCookies(webView, boolean)
    }

    @JvmStatic
    fun setCookie(url: String, value: String) {
        get().setCookie(url, value)
    }
}