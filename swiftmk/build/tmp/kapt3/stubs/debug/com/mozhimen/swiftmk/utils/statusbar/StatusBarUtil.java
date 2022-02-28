package com.mozhimen.swiftmk.utils.statusbar;

import java.lang.System;

/**
 * @ClassName StatusBarUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\u001e\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\u0016\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0004J\u001a\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0016\u001a\u00020\u0004H\u0002J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/mozhimen/swiftmk/utils/statusbar/StatusBarUtil;", "", "()V", "TYPE_FLYME", "", "TYPE_M", "TYPE_MIUI", "process", "", "activity", "Landroid/app/Activity;", "isFullScreen", "", "setCommonUI", "setFlymeUI", "dark", "setImmersiveStatusBar", "fontIconDark", "setMiuiUI", "setStatusBarColor", "colorId", "setStatusBarFontIconDark", "type", "setTranslucentStatus", "ViewType", "swiftmk_debug"})
public final class StatusBarUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.statusbar.StatusBarUtil INSTANCE = null;
    private static final int TYPE_MIUI = 0;
    private static final int TYPE_FLYME = 1;
    private static final int TYPE_M = 3;
    
    private StatusBarUtil() {
        super();
    }
    
    /**
     * 作用: 设置状态栏透明
     */
    @android.annotation.TargetApi(value = 19)
    public final void setTranslucentStatus(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 修改状态栏颜色,支持4.4以上的版本
     */
    public final void setStatusBarColor(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, int colorId) {
    }
    
    /**
     * 状态栏字体和图标是否是深色
     */
    public final void setImmersiveStatusBar(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, boolean fontIconDark, boolean isFullScreen) {
    }
    
    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带SYSTEM_UI_LAYOUT_FULLSCREEN这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     */
    private final void process(android.app.Activity activity, boolean isFullScreen) {
    }
    
    /**
     * 设置文字颜色
     */
    private final void setStatusBarFontIconDark(android.app.Activity activity, @com.mozhimen.swiftmk.utils.statusbar.StatusBarUtil.ViewType()
    int type) {
    }
    
    /**
     * 设置MIUI样式字体
     */
    private final void setMiuiUI(android.app.Activity activity, boolean dark) {
    }
    
    /**
     * 设置Flyme样式字体
     */
    private final void setFlymeUI(android.app.Activity activity, boolean dark) {
    }
    
    /**
     * 设置6.0字体
     */
    private final void setCommonUI(android.app.Activity activity) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0080\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2 = {"Lcom/mozhimen/swiftmk/utils/statusbar/StatusBarUtil$ViewType;", "", "swiftmk_debug"})
    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    public static abstract @interface ViewType {
    }
}