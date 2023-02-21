package com.mozhimen.componentk.camerak;

import android.graphics.SurfaceTexture;

import com.mozhimen.componentk.camerak.bases.CameraApi;
import com.mozhimen.componentk.camerak.commons.CameraPreviewCallback;
import com.mozhimen.componentk.camerak.commons.CapturePhotoCallback;
import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;
import com.mozhimen.componentk.camerak.mos.CameraFacing;
import com.mozhimen.componentk.camerak.mos.CameraSize;

/**
 * @ClassName RealCamera
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:22
 * @Version 1.0
 */
public class RealCamera extends CameraApi {
    private CameraApi delegate;

    public RealCamera(CameraApi cameraApi) {
        this.delegate = cameraApi;
    }

    public void openCamera(final CameraFacing facingType) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.openCamera(facingType);
            }
        });
    }

    public void release() {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.release();
            }
        });
    }

    public void addPreviewCallbackWithBuffer(final CameraPreviewCallback callback) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.addPreviewCallbackWithBuffer(callback);
            }
        });
    }

    public void removePreviewCallbackWithBuffer(final CameraPreviewCallback callback) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.removePreviewCallbackWithBuffer(callback);
            }
        });
    }

    public void clearPreviewCallbackWithBuffer() {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.clearPreviewCallbackWithBuffer();
            }
        });
    }

    public void setPreviewOrientation(final int orientation) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setPreviewOrientation(orientation);
            }
        });
    }

    public void setPreviewSize(final CameraSize size) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setPreviewSize(size);
            }
        });
    }

    public void startPreview(final SurfaceTexture surfacetexture) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.startPreview(surfacetexture);
            }
        });
    }

    public void stopPreview() {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.stopPreview();
            }
        });
    }

    public void setFlash(final CameraFlash flash) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setFlash(flash);
            }
        });
    }

    public void setFocusMode(final CameraFocus focus) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setFocusMode(focus);
            }
        });
    }

    public void setExposureCompensation(final int exposureCompensation) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setExposureCompensation(exposureCompensation);
            }
        });
    }

    public void setPhotoSize(final CameraSize size) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.setPhotoSize(size);
            }
        });
    }

    public void capturePhoto(final CapturePhotoCallback callback) {
        this.delegate.cameraHandler.post(new Runnable() {
            public void run() {
                RealCamera.this.delegate.capturePhoto(callback);
            }
        });
    }
}

