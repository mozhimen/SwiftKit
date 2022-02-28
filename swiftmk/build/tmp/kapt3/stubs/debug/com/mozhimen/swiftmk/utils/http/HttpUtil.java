package com.mozhimen.swiftmk.utils.http;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b\u00a8\u0006\f"}, d2 = {"Lcom/mozhimen/swiftmk/utils/http/HttpUtil;", "", "()V", "sendHttpRequest", "", "address", "", "listener", "Lcom/mozhimen/swiftmk/utils/http/HttpCallbackListener;", "sendOkHttpRequest", "callback", "Lokhttp3/Callback;", "swiftmk_debug"})
public final class HttpUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.http.HttpUtil INSTANCE = null;
    
    private HttpUtil() {
        super();
    }
    
    /**
     * 作用: 网络请求回调
     * 用法:
     * HttpUtil.sendHttpRequest(address, object : HttpCallbackListener {
     * override fun onFinish(response: String) {//得到服务器返回的具体内容}
     * override fun onError(e: Exception) {//在这里对异常情况进行处理} })
     */
    public final void sendHttpRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    com.mozhimen.swiftmk.utils.http.HttpCallbackListener listener) {
    }
    
    /**
     * 作用: 网络请求回调
     * 用法:
     * HttpUtil.sendOkHttpRequest(address, object : Callback {
     * override fun onResponse(call: Call, response: Response) {
     * //得到服务器返回的具体内容val responseData = response.body?.string()}
     * override fun onFailure(call: Call, e: IOException) {//在这里对异常情况进行处理} })
     */
    public final void sendOkHttpRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    okhttp3.Callback callback) {
    }
}