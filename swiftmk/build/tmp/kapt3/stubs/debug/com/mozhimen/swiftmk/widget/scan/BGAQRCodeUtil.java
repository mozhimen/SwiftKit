package com.mozhimen.swiftmk.widget.scan;

import java.lang.System;

/**
 * @ClassName BGAQRCodeUtil
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:18
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ>\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tJ\u001e\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tJ\u0010\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u001a\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0016\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000bJ\u0010\u0010$\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\u0012\u0010%\u001a\u0004\u0018\u00010\u00062\b\u0010&\u001a\u0004\u0018\u00010\u001eJ\u000e\u0010\'\u001a\u00020(2\u0006\u0010!\u001a\u00020\"J\u000e\u0010)\u001a\u00020\t2\u0006\u0010!\u001a\u00020\"J\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"J\u001a\u0010,\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010-\u001a\u00020\tJ\u0016\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\u000fJ\u000e\u00101\u001a\u00020\u001c2\u0006\u0010\u0003\u001a\u00020\u0004J\u0016\u00102\u001a\u00020\t2\u0006\u0010!\u001a\u00020\"2\u0006\u00103\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/mozhimen/swiftmk/widget/scan/BGAQRCodeUtil;", "", "()V", "debug", "", "adjustPhotoRotation", "Landroid/graphics/Bitmap;", "inputBitmap", "orientationDegree", "", "calculateFingerSpacing", "", "event", "Landroid/view/MotionEvent;", "calculateFocusMeteringArea", "Landroid/graphics/Rect;", "coefficient", "originFocusCenterX", "originFocusCenterY", "originFocusWidth", "originFocusHeight", "previewViewWidth", "previewViewHeight", "clamp", "value", "min", "max", "d", "", "msg", "", "tag", "dp2px", "context", "Landroid/content/Context;", "dpValue", "e", "getDecodeAbleBitmap", "picturePath", "getScreenResolution", "Landroid/graphics/Point;", "getStatusBarHeight", "isDebug", "isPortrait", "makeTintBitmap", "tintColor", "printRect", "prefix", "rect", "setDebug", "sp2px", "spValue", "swiftmk_debug"})
public final class BGAQRCodeUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.widget.scan.BGAQRCodeUtil INSTANCE = null;
    private static boolean debug = false;
    
    private BGAQRCodeUtil() {
        super();
    }
    
    public final void setDebug(boolean debug) {
    }
    
    public final boolean isDebug() {
        return false;
    }
    
    public final void d(@org.jetbrains.annotations.Nullable()
    java.lang.String msg) {
    }
    
    public final void printRect(@org.jetbrains.annotations.NotNull()
    java.lang.String prefix, @org.jetbrains.annotations.NotNull()
    android.graphics.Rect rect) {
    }
    
    public final void d(@org.jetbrains.annotations.Nullable()
    java.lang.String tag, @org.jetbrains.annotations.Nullable()
    java.lang.String msg) {
    }
    
    public final void e(@org.jetbrains.annotations.Nullable()
    java.lang.String msg) {
    }
    
    /**
     * 是否为竖屏
     */
    public final boolean isPortrait(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Point getScreenResolution(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final int getStatusBarHeight(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return 0;
    }
    
    public final int dp2px(@org.jetbrains.annotations.NotNull()
    android.content.Context context, float dpValue) {
        return 0;
    }
    
    public final int sp2px(@org.jetbrains.annotations.NotNull()
    android.content.Context context, float spValue) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap adjustPhotoRotation(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap inputBitmap, int orientationDegree) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap makeTintBitmap(@org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap inputBitmap, int tintColor) {
        return null;
    }
    
    /**
     * 计算对焦和测光区域
     *
     * @param coefficient        比率
     * @param originFocusCenterX 对焦中心点X
     * @param originFocusCenterY 对焦中心点Y
     * @param originFocusWidth   对焦宽度
     * @param originFocusHeight  对焦高度
     * @param previewViewWidth   预览宽度
     * @param previewViewHeight  预览高度
     */
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Rect calculateFocusMeteringArea(float coefficient, float originFocusCenterX, float originFocusCenterY, int originFocusWidth, int originFocusHeight, int previewViewWidth, int previewViewHeight) {
        return null;
    }
    
    public final int clamp(int value, int min, int max) {
        return 0;
    }
    
    /**
     * 计算手指间距
     */
    public final float calculateFingerSpacing(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent event) {
        return 0.0F;
    }
    
    /**
     * 将本地图片文件转换成可解码二维码的 Bitmap。为了避免图片太大，这里对图片进行了压缩。感谢 https://github.com/devilsen 提的 PR
     *
     * @param picturePath 本地图片文件路径
     */
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getDecodeAbleBitmap(@org.jetbrains.annotations.Nullable()
    java.lang.String picturePath) {
        return null;
    }
}