package com.mozhimen.swiftmk.helper.net;

import java.lang.System;

/**
 * @ClassName NetworkObserver
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/17 14:48
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a8\u0006\t"}, d2 = {"Lcom/mozhimen/swiftmk/helper/net/NetworkObserver;", "", "()V", "isMobileConnected", "", "context", "Landroid/content/Context;", "isNetConnected", "isWifiConnected", "swiftmk_debug"})
public final class NetworkObserver {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.net.NetworkObserver INSTANCE = null;
    
    private NetworkObserver() {
        super();
    }
    
    public final boolean isNetConnected(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        return false;
    }
    
    public final boolean isWifiConnected(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        return false;
    }
    
    public final boolean isMobileConnected(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        return false;
    }
}