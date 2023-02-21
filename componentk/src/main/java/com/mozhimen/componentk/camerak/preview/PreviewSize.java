package com.mozhimen.componentk.camerak.preview;

/**
 * @ClassName PreviewSize
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:44
 * @Version 1.0
 */
public class PreviewSize {
    private int mWidth;
    private int mHeight;

    public PreviewSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public PreviewSize clone() {
        return new PreviewSize(this.mWidth, this.mHeight);
    }
}
