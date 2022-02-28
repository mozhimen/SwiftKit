package com.mozhimen.swiftmk.widget.layout;

import java.lang.System;

/**
 * @ClassName BlurredLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:18
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004B\u001b\b\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007B#\b\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0018\u001a\u00020\u0019H\u0007J\u0006\u0010\u001a\u001a\u00020\u0019J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003J\b\u0010\u001d\u001a\u00020\u0019H\u0014J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\rH\u0002J\u0012\u0010!\u001a\u00020\u00192\b\u0010\"\u001a\u0004\u0018\u00010\u0012H\u0007J\u0012\u0010!\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010$H\u0007J\u000e\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\tJ\u000e\u0010\'\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\tJ\b\u0010(\u001a\u00020\u0019H\u0002J\u0018\u0010)\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u0006\u0010*\u001a\u00020\u0019R\u000e\u0010\u000b\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0003X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/mozhimen/swiftmk/widget/layout/BlurredLayout;", "Landroid/widget/RelativeLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "ALPHA_MAX_VALUE", "blurredImageView", "Landroid/widget/ImageView;", "isDisableBlurred", "", "isMove", "mBlurredBitmap", "Landroid/graphics/Bitmap;", "mContext", "mOriginBitmap", "mTag", "", "originImageView", "disableBlurredEffect", "", "enableBlurredEffect", "init", "initAttr", "onFinishInflate", "setBlurredHeight", "height", "imageView", "setBlurredImageView", "blurredBitmap", "blurDrawable", "Landroid/graphics/drawable/Drawable;", "setBlurredLevel", "level", "setBlurredTop", "setImageView", "setMove", "showBlurredImage", "swiftmk_debug"})
public final class BlurredLayout extends android.widget.RelativeLayout {
    private final int ALPHA_MAX_VALUE = 255;
    private final java.lang.String mTag = "BlurredLayout";
    private android.content.Context mContext;
    private boolean isMove = false;
    private boolean isDisableBlurred = false;
    private android.graphics.Bitmap mOriginBitmap;
    private android.graphics.Bitmap mBlurredBitmap;
    private android.widget.ImageView blurredImageView;
    private android.widget.ImageView originImageView;
    
    public BlurredLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    public BlurredLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    public BlurredLayout(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyle) {
        super(null);
    }
    
    private final void init(android.content.Context context) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    private final void initAttr(android.content.Context context, android.util.AttributeSet attrs) {
    }
    
    /**
     * 设置背景图片的移动效果
     */
    private final void setMove(android.content.Context context, boolean isMove) {
    }
    
    /**
     * 改变图片的高度
     */
    private final void setBlurredHeight(int height, android.widget.ImageView imageView) {
    }
    
    @java.lang.Override()
    protected void onFinishInflate() {
    }
    
    /**
     * 填充ImageView
     */
    private final void setImageView() {
    }
    
    /**
     * 添加待模糊图片
     */
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    public final void setBlurredImageView(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap blurredBitmap) {
    }
    
    /**
     * 添加待模糊图片2
     */
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    public final void setBlurredImageView(@org.jetbrains.annotations.Nullable()
    android.graphics.drawable.Drawable blurDrawable) {
    }
    
    /**
     * 设置模糊程度
     */
    public final void setBlurredLevel(int level) {
    }
    
    /**
     * 设置图片上移的距离
     */
    public final void setBlurredTop(int height) {
    }
    
    /**
     * 显示模糊的图片
     */
    public final void showBlurredImage() {
    }
    
    /**
     * 禁用模糊效果
     */
    @android.annotation.SuppressLint(value = {"Range"})
    public final void disableBlurredEffect() {
    }
    
    /**
     * 启用模糊效果
     */
    public final void enableBlurredEffect() {
    }
}