package com.mozhimen.swiftmk.helper.os;

import java.lang.System;

/**
 * @ClassName CompatDetector
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 17:36
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/mozhimen/swiftmk/helper/os/CompatDetector;", "", "()V", "mTag", "", "closeAndroidPDialog", "", "swiftmk_debug"})
public final class CompatDetector {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.os.CompatDetector INSTANCE = null;
    private static final java.lang.String mTag = "CompatDetector";
    
    private CompatDetector() {
        super();
    }
    
    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    public final void closeAndroidPDialog() {
    }
}