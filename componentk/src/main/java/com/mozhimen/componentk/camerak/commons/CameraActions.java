package com.mozhimen.componentk.camerak.commons;

import android.graphics.SurfaceTexture;

import com.mozhimen.componentk.camerak.mos.CameraFacing;
import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;
import com.mozhimen.componentk.camerak.mos.CameraSize;

/**
 * @ClassName CameraActions
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:15
 * @Version 1.0
 */
public interface CameraActions {
    void openCamera(CameraFacing var1);

    void release();

    void addPreviewCallbackWithBuffer(CameraPreviewCallback var1);

    void removePreviewCallbackWithBuffer(CameraPreviewCallback var1);

    void clearPreviewCallbackWithBuffer();

    void setPreviewOrientation(int var1);

    void setPreviewSize(CameraSize var1);

    void startPreview(SurfaceTexture var1);

    void stopPreview();

    void setFlash(CameraFlash var1);

    void setFocusMode(CameraFocus var1);

    void setExposureCompensation(int var1);

    void setPhotoSize(CameraSize var1);

    void capturePhoto(CapturePhotoCallback var1);
}
