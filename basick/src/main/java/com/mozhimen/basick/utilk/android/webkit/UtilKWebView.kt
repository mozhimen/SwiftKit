package com.mozhimen.basick.utilk.android.webkit

import android.content.Context
import android.webkit.WebView
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import java.io.File


/**
 * @ClassName UtilKWebView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/7/18 23:51
 * @Version 1.0
 */
object UtilKWebView {
    @JvmStatic
    fun clearMatches(webView: WebView) {
        webView.clearMatches()
    }

    @JvmStatic
    fun clearCache(webView: WebView) {
        webView.clearCache(true)
    }

    // 清除WebView的历史记录
    @JvmStatic
    fun clearHistory(webView: WebView) {
        webView.clearHistory()
    }

    @JvmStatic
    fun clearFormData(webView: WebView) {
        webView.clearFormData()
    }

    // 包括LocalStorage和SessionStorage）
    @JvmStatic
    fun clearSslPreferences(webView: WebView) {
        webView.clearSslPreferences()
    }

    @JvmStatic
    fun deleteDatabase_ofWebView(context: Context) {
        context.deleteDatabase("webview.db")
    }

    @JvmStatic
    fun deleteDatabaseFile_ofWebView(context: Context) {
        val webViewFilesDir = File(context.filesDir, "webview")
        if (webViewFilesDir.exists()) {
            UtilKFileDir.delete(webViewFilesDir)
        }
    }

    @JvmStatic
    fun deleteDatabase_ofWebViewCache(context: Context) {
        context.deleteDatabase("webviewCache.db")
    }

    @JvmStatic
    fun deleteDatabaseFile_ofWebViewCache(context: Context) {
        val webViewFilesDir = File(context.filesDir, "webview")
        if (webViewFilesDir.exists()) {
            UtilKFileDir.delete(webViewFilesDir)
        }
    }
}