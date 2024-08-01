package com.mozhimen.basick.elemk.android.webkit

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Message
import android.view.KeyEvent
import android.webkit.ClientCertRequest
import android.webkit.HttpAuthHandler
import android.webkit.RenderProcessGoneDetail
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseWebViewClient
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/31
 * @Version 1.0
 */
open class BaseWebViewClient : WebViewClient(), IUtilK {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        UtilKLogWrapper.d(TAG, "shouldOverrideUrlLoading: url ${if (UtilKBuildVersion.isAfterV_21_5_L()) request!!.url else "version"}")
        return super.shouldOverrideUrlLoading(view, request)
    }

//    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//        UtilKLogWrapper.d(TAG, "shouldOverrideUrlLoading: url $url")
//        return super.shouldOverrideUrlLoading(view, url)
//    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        UtilKLogWrapper.v(TAG, "shouldInterceptRequest: ")
        return super.shouldInterceptRequest(view, request)
    }

//    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
//        UtilKLogWrapper.d(TAG, "shouldInterceptRequest: url $url")
//        return super.shouldInterceptRequest(view, url)
//    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        UtilKLogWrapper.v(TAG, "shouldOverrideKeyEvent: ")
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        UtilKLogWrapper.d(TAG, "onReceivedHttpError: ")
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        UtilKLogWrapper.d(TAG, "onReceivedSslError: ")
        super.onReceivedSslError(view, handler, error)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        UtilKLogWrapper.v(TAG, "onLoadResource: url $url")
        super.onLoadResource(view, url)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        UtilKLogWrapper.d(TAG, "onPageCommitVisible: url $url")
        super.onPageCommitVisible(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        UtilKLogWrapper.d(TAG, "onPageFinished: url $url")
        super.onPageFinished(view, url)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        UtilKLogWrapper.d(TAG, "onReceivedClientCertRequest: ")
        super.onReceivedClientCertRequest(view, request)
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        UtilKLogWrapper.d(TAG, "onRenderProcessGone: ")
        return super.onRenderProcessGone(view, detail)
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        UtilKLogWrapper.v(TAG, "onUnhandledKeyEvent: ")
        super.onUnhandledKeyEvent(view, event)
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        UtilKLogWrapper.d(TAG, "onFormResubmission: ")
        super.onFormResubmission(view, dontResend, resend)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        UtilKLogWrapper.d(TAG, "onPageStarted: url $url")
        super.onPageStarted(view, url, favicon)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        UtilKLogWrapper.d(TAG, "onReceivedError: error $error")
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        UtilKLogWrapper.d(TAG, "onReceivedHttpAuthRequest: host $host realm $realm")
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        UtilKLogWrapper.d(TAG, "onReceivedLoginRequest: realm $realm account $account args $args")
        super.onReceivedLoginRequest(view, realm, account, args)
    }

    override fun onSafeBrowsingHit(view: WebView?, request: WebResourceRequest?, threatType: Int, callback: SafeBrowsingResponse?) {
        UtilKLogWrapper.d(TAG, "onSafeBrowsingHit: threatType $threatType")
        super.onSafeBrowsingHit(view, request, threatType, callback)
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        UtilKLogWrapper.d(TAG, "onScaleChanged: oldScale $oldScale newScale $newScale")
        super.onScaleChanged(view, oldScale, newScale)
    }

//    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
//        UtilKLogWrapper.d(TAG, "onReceivedError: errorCode $errorCode description $description failingUrl $failingUrl")
//        super.onReceivedError(view, errorCode, description, failingUrl)
//    }
//
//    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
//        UtilKLogWrapper.d(TAG, "onTooManyRedirects: cancelMsg $cancelMsg continueMsg $continueMsg")
//        super.onTooManyRedirects(view, cancelMsg, continueMsg)
//    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        UtilKLogWrapper.d(TAG, "doUpdateVisitedHistory: url $url isReload $isReload")
        super.doUpdateVisitedHistory(view, url, isReload)
    }
}