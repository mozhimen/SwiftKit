package com.mozhimen.basick.utilk.android.webkit

import android.webkit.WebView

/**
 * @ClassName UtilKWebView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/7/18 23:51
 * @Version 1.0
 */
object UtilKWebView {
    @JvmStatic
    fun clearCache(webView: WebView) {
        webView.clearCache(true)
    }
}