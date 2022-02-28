package com.mozhimen.swiftmk.helper.statusbar;

import java.lang.System;

/**
 * @ClassName BarTintManager
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:14
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0006J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/mozhimen/swiftmk/helper/statusbar/BarTintManager;", "", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "xBarAvailable", "", "xBarTintEnabled", "xBarTintView", "Landroid/view/View;", "getBarHeight", "", "setBarTintColor", "", "colorId", "setBarTintEnabled", "enabled", "setupBarView", "viewGroup", "Landroid/view/ViewGroup;", "Companion", "swiftmk_debug"})
public final class BarTintManager {
    private boolean xBarAvailable = false;
    private boolean xBarTintEnabled = false;
    private android.view.View xBarTintView;
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.statusbar.BarTintManager.Companion Companion = null;
    public static final int DEFAULT_TINT_COLOR = -1728053248;
    
    public BarTintManager(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        super();
    }
    
    /**
     * 初始化状态栏
     */
    private final void setupBarView(android.app.Activity activity, android.view.ViewGroup viewGroup) {
    }
    
    /**
     * 获取状态栏高度
     */
    private final int getBarHeight(android.app.Activity activity) {
        return 0;
    }
    
    /**
     * 显示状态栏
     */
    public final void setBarTintEnabled(boolean enabled) {
    }
    
    /**
     * 设置状态栏颜色
     */
    public final void setBarTintColor(int colorId) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/mozhimen/swiftmk/helper/statusbar/BarTintManager$Companion;", "", "()V", "DEFAULT_TINT_COLOR", "", "swiftmk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}