package com.mozhimen.componentk.camerak.preview;

import android.graphics.Matrix;

/**
 * @ClassName ScaleManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:44
 * @Version 1.0
 */
public class ScaleManager {
    private int mDisplayDir;
    private PreviewSize mViewSize;
    private PreviewSize mDisplayContentSize;
    private PreviewSize mOriContentSize;
    private Matrix mMatrix;
    private boolean mIsMirror;
    private boolean mExChangeWH;
    private boolean mIsFrontCamera;

    public ScaleManager(PreviewSize viewSize, PreviewSize contentSize, boolean isMirror, boolean exChangeWH, int displayDir, boolean isFrontCamera) {
        if (contentSize != null && viewSize != null) {
            this.mViewSize = viewSize.clone();
            this.mDisplayContentSize = contentSize.clone();
            this.mOriContentSize = contentSize.clone();
            this.mExChangeWH = exChangeWH;
            if (exChangeWH) {
                this.mDisplayContentSize.setWidth(this.mDisplayContentSize.getHeight() + this.mDisplayContentSize.getWidth());
                this.mDisplayContentSize.setHeight(this.mDisplayContentSize.getWidth() - this.mDisplayContentSize.getHeight());
                this.mDisplayContentSize.setWidth(this.mDisplayContentSize.getWidth() - this.mDisplayContentSize.getHeight());
            }

            this.mDisplayDir = displayDir;
            this.mIsFrontCamera = isFrontCamera;
            this.mIsMirror = isMirror;
        } else {
            throw new IllegalArgumentException("ScaleManager argus is illegal");
        }
    }

    public Matrix getScaleMatrix(ScalableType scalableType) {
        Matrix matrix;
        switch(scalableType) {
            case NONE:
                matrix = this.getNoScale();
                break;
            case FIT_XY:
                matrix = this.fitXY();
                break;
            case FIT_CENTER:
                matrix = this.fitCenter();
                break;
            case FIT_START:
                matrix = this.fitStart();
                break;
            case FIT_END:
                matrix = this.fitEnd();
                break;
            case LEFT_TOP:
                matrix = this.getOriginalScale(ScaleType.LEFT_TOP);
                break;
            case LEFT_CENTER:
                matrix = this.getOriginalScale(ScaleType.LEFT_CENTER);
                break;
            case LEFT_BOTTOM:
                matrix = this.getOriginalScale(ScaleType.LEFT_BOTTOM);
                break;
            case CENTER_TOP:
                matrix = this.getOriginalScale(ScaleType.CENTER_TOP);
                break;
            case CENTER:
                matrix = this.getOriginalScale(ScaleType.CENTER);
                break;
            case CENTER_BOTTOM:
                matrix = this.getOriginalScale(ScaleType.CENTER_BOTTOM);
                break;
            case RIGHT_TOP:
                matrix = this.getOriginalScale(ScaleType.RIGHT_TOP);
                break;
            case RIGHT_CENTER:
                matrix = this.getOriginalScale(ScaleType.RIGHT_CENTER);
                break;
            case RIGHT_BOTTOM:
                matrix = this.getOriginalScale(ScaleType.RIGHT_BOTTOM);
                break;
            case LEFT_TOP_CROP:
                matrix = this.getCropScale(ScaleType.LEFT_TOP);
                break;
            case LEFT_CENTER_CROP:
                matrix = this.getCropScale(ScaleType.LEFT_CENTER);
                break;
            case LEFT_BOTTOM_CROP:
                matrix = this.getCropScale(ScaleType.LEFT_BOTTOM);
                break;
            case CENTER_TOP_CROP:
                matrix = this.getCropScale(ScaleType.CENTER_TOP);
                break;
            case CENTER_CROP:
                matrix = this.getCropScale(ScaleType.CENTER);
                break;
            case CENTER_BOTTOM_CROP:
                matrix = this.getCropScale(ScaleType.CENTER_BOTTOM);
                break;
            case RIGHT_TOP_CROP:
                matrix = this.getCropScale(ScaleType.RIGHT_TOP);
                break;
            case RIGHT_CENTER_CROP:
                matrix = this.getCropScale(ScaleType.RIGHT_CENTER);
                break;
            case RIGHT_BOTTOM_CROP:
                matrix = this.getCropScale(ScaleType.RIGHT_BOTTOM);
                break;
            case START_INSIDE:
                matrix = this.startInside();
                break;
            case CENTER_INSIDE:
                matrix = this.centerInside();
                break;
            case END_INSIDE:
                matrix = this.endInside();
                break;
            default:
                matrix = null;
        }

        this.mMatrix = new Matrix();
        if (this.mIsMirror) {
            this.mMatrix.postScale(-1.0F, 1.0F, (float)this.mViewSize.getWidth() / 2.0F, 0.0F);
        }

        this.mMatrix.postConcat(matrix);
        return this.mMatrix;
    }

    public ScaleManager clone() {
        ScaleManager scaleManager = new ScaleManager(this.mViewSize, this.mOriContentSize, this.mIsMirror, this.mExChangeWH, this.mDisplayDir, this.mIsFrontCamera);
        scaleManager.setMatrix(this.mMatrix);
        return scaleManager;
    }

    private Matrix getMatrix(float sx, float sy, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy, px, py);
        return matrix;
    }

    private Matrix getMatrix(float sx, float sy, ScaleType pivotPoint) {
        switch(pivotPoint) {
            case LEFT_TOP:
                return this.getMatrix(sx, sy, 0.0F, 0.0F);
            case LEFT_CENTER:
                return this.getMatrix(sx, sy, 0.0F, (float)this.mViewSize.getHeight() / 2.0F);
            case LEFT_BOTTOM:
                return this.getMatrix(sx, sy, 0.0F, (float)this.mViewSize.getHeight());
            case CENTER_TOP:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, 0.0F);
            case CENTER:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, (float)this.mViewSize.getHeight() / 2.0F);
            case CENTER_BOTTOM:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth() / 2.0F, (float)this.mViewSize.getHeight());
            case RIGHT_TOP:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), 0.0F);
            case RIGHT_CENTER:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), (float)this.mViewSize.getHeight() / 2.0F);
            case RIGHT_BOTTOM:
                return this.getMatrix(sx, sy, (float)this.mViewSize.getWidth(), (float)this.mViewSize.getHeight());
            default:
                throw new IllegalArgumentException("Illegal PivotPoint");
        }
    }

    private Matrix getNoScale() {
        float sx = (float)this.mDisplayContentSize.getWidth() / (float)this.mViewSize.getWidth();
        float sy = (float)this.mDisplayContentSize.getHeight() / (float)this.mViewSize.getHeight();
        return this.getMatrix(sx, sy, ScaleType.LEFT_TOP);
    }

    private Matrix getFitScale(ScaleType pivotPoint) {
        float sx = (float)this.mViewSize.getWidth() / (float)this.mDisplayContentSize.getWidth();
        float sy = (float)this.mViewSize.getHeight() / (float)this.mDisplayContentSize.getHeight();
        float minScale = Math.min(sx, sy);
        sx = minScale / sx;
        sy = minScale / sy;
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix fitXY() {
        return this.getMatrix(1.0F, 1.0F, ScaleType.LEFT_TOP);
    }

    private Matrix fitStart() {
        return this.getFitScale(ScaleType.LEFT_TOP);
    }

    private Matrix fitCenter() {
        return this.getFitScale(ScaleType.CENTER);
    }

    private Matrix fitEnd() {
        return this.getFitScale(ScaleType.RIGHT_BOTTOM);
    }

    private Matrix getOriginalScale(ScaleType pivotPoint) {
        float sx = (float)this.mDisplayContentSize.getWidth() / (float)this.mViewSize.getWidth();
        float sy = (float)this.mDisplayContentSize.getHeight() / (float)this.mViewSize.getHeight();
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix getCropScale(ScaleType pivotPoint) {
        float sx = (float)this.mViewSize.getWidth() / (float)this.mDisplayContentSize.getWidth();
        float sy = (float)this.mViewSize.getHeight() / (float)this.mDisplayContentSize.getHeight();
        float maxScale = Math.max(sx, sy);
        sx = maxScale / sx;
        sy = maxScale / sy;
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix getInsideScale(ScaleType pivotPoint) {
        float sx = (float)this.mViewSize.getWidth() / (float)this.mDisplayContentSize.getWidth();
        float sy = (float)this.mViewSize.getHeight() / (float)this.mDisplayContentSize.getHeight();
        float minScale = Math.min(sx, sy);
        sx = minScale / sx;
        sy = minScale / sy;
        return this.getMatrix(sx, sy, pivotPoint);
    }

    private Matrix startInside() {
        return this.getInsideScale(ScaleType.LEFT_TOP);
    }

    private Matrix centerInside() {
        return this.getInsideScale(ScaleType.CENTER);
    }

    private Matrix endInside() {
        return this.getInsideScale(ScaleType.RIGHT_BOTTOM);
    }

    public PreviewSize getViewSize() {
        return this.mViewSize;
    }

    public PreviewSize getContentSize() {
        return this.mOriContentSize;
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public void setMatrix(Matrix matrix) {
        this.mMatrix = new Matrix(matrix);
    }

    public boolean isExChangeWH() {
        return this.mExChangeWH;
    }

    public void setExChangeWH(boolean exChangeWH) {
        this.mExChangeWH = exChangeWH;
    }

    public boolean isFrontCamera() {
        return this.mIsFrontCamera;
    }

    public void exChangeOriContentSize() {
        this.mOriContentSize.setWidth(this.mOriContentSize.getHeight() + this.mOriContentSize.getWidth());
        this.mOriContentSize.setHeight(this.mOriContentSize.getWidth() - this.mOriContentSize.getHeight());
        this.mOriContentSize.setWidth(this.mOriContentSize.getWidth() - this.mOriContentSize.getHeight());
        this.mExChangeWH = !this.mExChangeWH;
    }

    public boolean isIsMirror() {
        return this.mIsMirror;
    }
}
