package com.mozhimen.basick.elemk.android.webkit

import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.GeolocationPermissions
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebStorage
import android.webkit.WebView
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseWebChromeClient
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/31
 * @Version 1.0
 */
open class BaseWebChromeClient : WebChromeClient(), IUtilK {

    override fun getDefaultVideoPoster(): Bitmap? {
        UtilKLogWrapper.d(TAG, "getDefaultVideoPoster: ")
        return super.getDefaultVideoPoster()
    }

    override fun getVideoLoadingProgressView(): View? {
        UtilKLogWrapper.d(TAG, "getVideoLoadingProgressView: ")
        return super.getVideoLoadingProgressView()
    }

    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) {
        UtilKLogWrapper.d(TAG, "getVisitedHistory: ")
        super.getVisitedHistory(callback)
    }

    override fun onGeolocationPermissionsHidePrompt() {
        UtilKLogWrapper.d(TAG, "onGeolocationPermissionsHidePrompt: ")
        super.onGeolocationPermissionsHidePrompt()
    }

    override fun onHideCustomView() {
        UtilKLogWrapper.d(TAG, "onHideCustomView: ")
        super.onHideCustomView()
    }

    override fun onCloseWindow(window: WebView?) {
        UtilKLogWrapper.d(TAG, "onCloseWindow: ")
        super.onCloseWindow(window)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        UtilKLogWrapper.d(TAG, "onConsoleMessage: consoleMessage $consoleMessage")
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onPermissionRequest(request: PermissionRequest?) {
        UtilKLogWrapper.d(TAG, "onPermissionRequest: ")
        super.onPermissionRequest(request)
    }

    override fun onPermissionRequestCanceled(request: PermissionRequest?) {
        UtilKLogWrapper.d(TAG, "onPermissionRequestCanceled: ")
        super.onPermissionRequestCanceled(request)
    }

    override fun onRequestFocus(view: WebView?) {
        UtilKLogWrapper.d(TAG, "onRequestFocus: ")
        super.onRequestFocus(view)
    }

    override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
        UtilKLogWrapper.d(TAG, "onGeolocationPermissionsShowPrompt: $origin")
        super.onGeolocationPermissionsShowPrompt(origin, callback)
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        UtilKLogWrapper.d(TAG, "onProgressChanged: newProgress $newProgress")
        super.onProgressChanged(view, newProgress)
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        UtilKLogWrapper.d(TAG, "onReceivedIcon: ")
        super.onReceivedIcon(view, icon)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        UtilKLogWrapper.d(TAG, "onReceivedTitle: title $title")
        super.onReceivedTitle(view, title)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        UtilKLogWrapper.d(TAG, "onShowCustomView: ")
        super.onShowCustomView(view, callback)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        UtilKLogWrapper.d(TAG, "onJsPrompt: message $message defaultValue $defaultValue result $result")
        return super.onJsPrompt(view, url, message, defaultValue, result)
    }

    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
        UtilKLogWrapper.d(TAG, "onCreateWindow: isDialog $isDialog isUserGesture $isUserGesture resultMsg $resultMsg")
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        UtilKLogWrapper.d(TAG, "onJsAlert: url $url message $message result $result")
        return super.onJsAlert(view, url, message, result)
    }

    override fun onJsBeforeUnload(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        UtilKLogWrapper.d(TAG, "onJsBeforeUnload: url $url message $message result $result")
        return super.onJsBeforeUnload(view, url, message, result)
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        UtilKLogWrapper.d(TAG, "onJsConfirm: url $url message $message result $result")
        return super.onJsConfirm(view, url, message, result)
    }

    override fun onReceivedTouchIconUrl(view: WebView?, url: String?, precomposed: Boolean) {
        UtilKLogWrapper.d(TAG, "onReceivedTouchIconUrl: url $url precomposed $precomposed")
        super.onReceivedTouchIconUrl(view, url, precomposed)
    }

    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
        UtilKLogWrapper.d(TAG, "onShowFileChooser: ")
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }

//    override fun onJsTimeout(): Boolean {
//        UtilKLogWrapper.d(TAG, "onJsTimeout: ")
//        return super.onJsTimeout()
//    }
//
//    override fun onExceededDatabaseQuota(url: String?, databaseIdentifier: String?, quota: Long, estimatedDatabaseSize: Long, totalQuota: Long, quotaUpdater: WebStorage.QuotaUpdater?) {
//        UtilKLogWrapper.d(TAG, "onExceededDatabaseQuota: url $url databaseIdentifier $databaseIdentifier quota $quota estimatedDatabaseSize $estimatedDatabaseSize totalQuota $totalQuota")
//        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater)
//    }
//
//    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
//        UtilKLogWrapper.d(TAG, "onConsoleMessage: message $message lineNumber $lineNumber sourceID $sourceID")
//        super.onConsoleMessage(message, lineNumber, sourceID)
//    }
//
//    override fun onShowCustomView(view: View?, requestedOrientation: Int, callback: CustomViewCallback?) {
//        UtilKLogWrapper.d(TAG, "onShowCustomView: requestedOrientation $requestedOrientation")
//        super.onShowCustomView(view, requestedOrientation, callback)
//    }
}