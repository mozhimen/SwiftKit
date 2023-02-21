package com.mozhimen.componentk.camerak;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.mozhimen.componentk.camerak.bases.CameraApi;
import com.mozhimen.componentk.camerak.commons.CallBackEvents;
import com.mozhimen.componentk.camerak.commons.CameraPreviewCallback;
import com.mozhimen.componentk.camerak.commons.CapturePhotoCallback;
import com.mozhimen.componentk.camerak.cons.CameraApiType;
import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;
import com.mozhimen.componentk.camerak.mos.CameraFacing;
import com.mozhimen.componentk.camerak.mos.CameraSize;
import com.mozhimen.componentk.camerak.mos.IAttributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName CameraManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:12
 * @Version 1.0
 */
public class CameraManager extends CameraApi {
    private static Map<Integer, CameraManager> sCameraManagerMap = new ConcurrentHashMap();
    private RealCamera mCameraInstance;
    private CameraFacing mFacing;
    private CallBackEvents mCallBackEvents;
    private CallBackEvents self_callBackEvents = new CallBackEvents() {
        public void onCameraOpen(IAttributes cameraAttributes) {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onCameraOpen(cameraAttributes);
            }

        }

        public void onCameraClose() {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onCameraClose();
            }

        }

        public void onCameraError(String errorMsg) {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onCameraError(errorMsg);
            }

        }

        public void onPreviewStarted() {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onPreviewStarted();
            }

        }

        public void onPreviewStopped() {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onPreviewStopped();
            }

        }

        public void onPreviewError(String errorMsg) {
            if (CameraManager.this.mCallBackEvents != null) {
                CameraManager.this.mCallBackEvents.onPreviewError(errorMsg);
            }

        }
    };

    public static CameraManager getInstance(CameraFacing facing, CameraApiType cameraApiType, Context context) {
        return getInstance(facing, cameraApiType, context, (Handler) null, (Lifecycle) null);
    }

    public static CameraManager getInstance(CameraFacing facing, CameraApiType cameraApiType, Context context, Handler handler) {
        return getInstance(facing, cameraApiType, context, handler, (Lifecycle) null);
    }

    public static CameraManager getInstance(CameraFacing facing, CameraApiType cameraApiType, Context context, Handler handler, Lifecycle lifecycle) {
        Class var5 = CameraManager.class;
        synchronized (CameraManager.class) {
            CameraManager cameraManager = (CameraManager) sCameraManagerMap.get(facing.cameraId);
            if (cameraManager == null) {
                cameraManager = new CameraManager(facing, cameraApiType, context, handler, lifecycle);
                sCameraManagerMap.put(facing.cameraId, cameraManager);
            }

            return cameraManager;
        }
    }

    public CameraManager(@NonNull CameraFacing facing, @NonNull CameraApiType cameraApiType, Context context, Handler handler, Lifecycle lifecycle) {
        this.mFacing = facing;
        if (cameraApiType == CameraApiType.CAMERAUVC) {
            this.mCameraInstance = new RealCamera(new CameraUvc(this.self_callBackEvents, context, handler));
        } else {
            if (cameraApiType == CameraApiType.AUTO) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (context != null && lifecycle != null) {
                        this.mCameraInstance = new RealCamera(new Camera2(this.self_callBackEvents, context));
                        return;
                    }

                    if (context != null) {
                        this.mCameraInstance = new RealCamera(new Camera2(this.self_callBackEvents, context));
                        return;
                    }
                }
            } else if (cameraApiType == CameraApiType.CAMERA2) {
                if (Build.VERSION.SDK_INT >= 21 && context != null) {
                    this.mCameraInstance = new RealCamera(new Camera2(this.self_callBackEvents, context));
                    return;
                }
            } else if (cameraApiType == CameraApiType.CAMERAX && Build.VERSION.SDK_INT >= 21 && context != null && lifecycle != null) {
                this.mCameraInstance = new RealCamera(new Camera2(this.self_callBackEvents, context));
                return;
            }

            this.mCameraInstance = new RealCamera(new Camera1(this.self_callBackEvents));
        }
    }

    public void setCallBackEvents(CallBackEvents mCallBackEvents) {
        this.mCallBackEvents = mCallBackEvents;
    }

    public void openCamera() {
        this.mCameraInstance.openCamera(this.mFacing);
    }

    public void openCamera(CameraFacing facingType) {
        this.mFacing = facingType;
        this.mCameraInstance.openCamera(facingType);
    }

    public void release() {
        this.mCameraInstance.release();
    }

    public void addPreviewCallbackWithBuffer(CameraPreviewCallback callback) {
        this.mCameraInstance.addPreviewCallbackWithBuffer(callback);
    }

    public void removePreviewCallbackWithBuffer(CameraPreviewCallback callback) {
        this.mCameraInstance.removePreviewCallbackWithBuffer(callback);
    }

    public void clearPreviewCallbackWithBuffer() {
        this.mCameraInstance.clearPreviewCallbackWithBuffer();
    }

    public void setPreviewOrientation(int orientation) {
        this.mCameraInstance.setPreviewOrientation(orientation);
    }

    public void setPreviewSize(CameraSize size) {
        this.mCameraInstance.setPreviewSize(size);
    }

    public void startPreview(SurfaceTexture surfacetexture) {
        this.mCameraInstance.startPreview(surfacetexture);
    }

    public void stopPreview() {
        this.mCameraInstance.stopPreview();
    }

    public void setFlash(CameraFlash flash) {
        this.mCameraInstance.setFlash(flash);
    }

    public void setFocusMode(CameraFocus focus) {
        this.mCameraInstance.setFocusMode(focus);
    }

    public void setExposureCompensation(int exposureCompensation) {
        this.mCameraInstance.setExposureCompensation(exposureCompensation);
    }

    public void setPhotoSize(CameraSize size) {
        this.mCameraInstance.setPhotoSize(size);
    }

    public void capturePhoto(CapturePhotoCallback callback) {
        this.mCameraInstance.capturePhoto(callback);
    }
}
