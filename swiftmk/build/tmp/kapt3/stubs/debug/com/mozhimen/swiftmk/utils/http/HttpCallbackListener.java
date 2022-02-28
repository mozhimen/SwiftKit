package com.mozhimen.swiftmk.utils.http;

import java.lang.System;

/**
 * @ClassName HttpUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/15 11:19
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/mozhimen/swiftmk/utils/http/HttpCallbackListener;", "", "onError", "", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onFinish", "response", "", "swiftmk_debug"})
public abstract interface HttpCallbackListener {
    
    public abstract void onFinish(@org.jetbrains.annotations.NotNull()
    java.lang.String response);
    
    public abstract void onError(@org.jetbrains.annotations.NotNull()
    java.lang.Exception e);
}