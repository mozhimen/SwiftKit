package com.mozhimen.swiftmk.widget.scan;

import java.lang.System;

/**
 * @ClassName QRScanView
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:18
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0002J\u0010\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0002J\u0006\u0010&\u001a\u00020\tJ\u0010\u0010\'\u001a\u00020 2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0018\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020 H\u0002J\u0012\u0010-\u001a\u00020 2\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\u0018\u0010.\u001a\u00020 2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\u0014J(\u00101\u001a\u00020 2\u0006\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020\t2\u0006\u00104\u001a\u00020\t2\u0006\u00105\u001a\u00020\tH\u0014J\b\u00106\u001a\u00020 H\u0002R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2 = {"Lcom/mozhimen/swiftmk/widget/scan/QRScanView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mAnimDelayTime", "mAnimTime", "mBorderColor", "mBorderSize", "mCustomScanLineDrawable", "Landroid/graphics/drawable/Drawable;", "mFramingRect", "Landroid/graphics/Rect;", "mIsScanLineReverse", "", "mMoveStepDistance", "mOriginQRCodeScanLineBitmap", "Landroid/graphics/Bitmap;", "mPaint", "Landroid/graphics/Paint;", "mRectHeight", "mRectWidth", "mScanLineBitmap", "mScanLineTop", "", "afterInitCustomAttrs", "", "calFramingRect", "drawBorderLine", "canvas", "Landroid/graphics/Canvas;", "drawScanLine", "getScanViewHeight", "init", "initCustomAttr", "attr", "typedArray", "Landroid/content/res/TypedArray;", "moveScanLine", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onSizeChanged", "w", "h", "oldw", "oldh", "refreshScanBox", "swiftmk_debug"})
public final class QRScanView extends android.view.View {
    private android.graphics.drawable.Drawable mCustomScanLineDrawable;
    private int mBorderSize = 0;
    private int mBorderColor = 0;
    private int mAnimTime = 1000;
    private boolean mIsScanLineReverse = false;
    private android.graphics.Bitmap mOriginQRCodeScanLineBitmap;
    private android.graphics.Paint mPaint;
    private android.graphics.Bitmap mScanLineBitmap;
    private float mScanLineTop = 0.0F;
    private int mMoveStepDistance = 0;
    private int mAnimDelayTime = 0;
    private android.graphics.Rect mFramingRect;
    private int mRectWidth = 0;
    private int mRectHeight = 0;
    
    public QRScanView(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    public QRScanView(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public QRScanView(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final void init(@org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
    }
    
    private final void initCustomAttr(int attr, android.content.res.TypedArray typedArray) {
    }
    
    private final void afterInitCustomAttrs() {
    }
    
    private final void refreshScanBox() {
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.Nullable()
    android.graphics.Canvas canvas) {
    }
    
    /**
     * 画边框线
     */
    private final void drawBorderLine(android.graphics.Canvas canvas) {
    }
    
    /**
     * 画扫描线
     */
    private final void drawScanLine(android.graphics.Canvas canvas) {
    }
    
    /**
     * 移动扫描线的位置
     */
    private final void moveScanLine() {
    }
    
    @java.lang.Override()
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    
    private final void calFramingRect() {
    }
    
    public final int getScanViewHeight() {
        return 0;
    }
    
    @java.lang.Override()
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }
}