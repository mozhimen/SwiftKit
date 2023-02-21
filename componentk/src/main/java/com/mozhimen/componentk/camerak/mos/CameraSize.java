package com.mozhimen.componentk.camerak.mos;

import android.util.Size;

import androidx.annotation.RequiresApi;

/**
 * @ClassName CameraSize
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:19
 * @Version 1.0
 */
public class CameraSize implements Comparable<CameraSize> {
    private int width;
    private int height;

    public CameraSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int area() {
        return this.width * this.height;
    }

    public float aspectRatio() {
        return this.width != 0 && this.height != 0 ? (float)this.width / (float)this.height : 1.0F;
    }

    @RequiresApi(
            api = 21
    )
    public Size getSize() {
        return new Size(this.width, this.height);
    }

    public int compareTo(CameraSize other) {
        int areaDiff = this.width * this.height - other.width * other.height;
        if (areaDiff > 0) {
            return 1;
        } else {
            return areaDiff < 0 ? -1 : 0;
        }
    }
}