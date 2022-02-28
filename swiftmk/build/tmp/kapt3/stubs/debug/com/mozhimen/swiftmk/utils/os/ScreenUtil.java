package com.mozhimen.swiftmk.utils.os;

import java.lang.System;

/**
 * @ClassName ScreenUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 9:16
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/mozhimen/swiftmk/utils/os/ScreenUtil;", "", "()V", "mTag", "", "captureScreen", "Landroid/graphics/Bitmap;", "activity", "Landroid/app/Activity;", "closeSoftInputKeyBoard", "", "getScreenHeight", "", "context", "Landroid/content/Context;", "getScreenWidth", "getStatusBarHeight", "getTitleHeight", "getViewDrawHeight", "getVirtualBarHeight", "swiftmk_debug"})
public final class ScreenUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.os.ScreenUtil INSTANCE = null;
    private static final java.lang.String mTag = "ScreenUtil";
    
    private ScreenUtil() {
        super();
    }
    
    /**
     * 获取状态栏高度1
     * 优点: 不需要在Activity的回调方法onWindowFocusChanged()执行后才能得到结果
     * 缺点: 不管你是否设置全屏模式,或是不是显示状态栏,高度是固定的;因为系统资源属性是固定的,真实的,不管你是否隐藏(隐藏或显示),他都在nali
     */
    public final int getStatusBarHeight(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    /**
     * 获取状态栏高度2
     * 优点: 依赖于WMS,是在界面构建后根据View获取的,所以高度是动态的
     * 缺点: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     */
    public final int getStatusBarHeight(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return 0;
    }
    
    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     */
    public final int getTitleHeight(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return 0;
    }
    
    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     */
    private final int getViewDrawHeight(android.app.Activity activity) {
        return 0;
    }
    
    /**
     * 获取屏幕宽度
     */
    public final int getScreenWidth(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    /**
     * 获取屏幕高度
     */
    public final int getScreenHeight(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    /**
     * 获取虚拟功能键的高度
     */
    public final int getVirtualBarHeight(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    /**
     * 截屏
     */
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap captureScreen(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return null;
    }
    
    /**
     * 关闭软键盘
     */
    public final void closeSoftInputKeyBoard(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
}