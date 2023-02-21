package com.mozhimen.componentk.camerak;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mozhimen.componentk.camerak.bases.CameraApi;
import com.mozhimen.componentk.camerak.commons.CallBackEvents;
import com.mozhimen.componentk.camerak.commons.CameraPreviewCallback;
import com.mozhimen.componentk.camerak.commons.CapturePhotoCallback;
import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;
import com.mozhimen.componentk.camerak.cons.FacingType;
import com.mozhimen.componentk.camerak.mos.CameraFacing;
import com.mozhimen.componentk.camerak.mos.CameraSize;
import com.mozhimen.componentk.camerak.mos.IAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @ClassName Camera1
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 23:21
 * @Version 1.0
 */
public class Camera1 extends CameraApi {
    private static final String TAG = "Camera1";
    private static final int DEFAULT_PREVIEW_WIDTH = 640;
    private static final int DEFAULT_PREVIEW_HEIGHT = 480;
    private Camera camera = null;
    private Camera1.Camera1Attributes cameraAttributes = null;
    private Camera.PreviewCallback mPreviewCallback = null;
    private HashSet<CameraPreviewCallback> mCustomPreviewCallbacks;
    private byte[] mBuffer;
    private byte[] mCallbackBuffer;

    public Camera1(@NonNull CallBackEvents callBackEvents) {
        this.cameraHandler = CameraHandler.get();
        this.callBackEvents = callBackEvents;
    }

    public synchronized void openCamera(CameraFacing cameraFacing) {
        if (this.camera != null) {
            this.camera.setPreviewCallback((Camera.PreviewCallback)null);
            this.camera.setPreviewCallbackWithBuffer((Camera.PreviewCallback)null);

            try {
                this.camera.setPreviewTexture((SurfaceTexture)null);
            } catch (IOException var8) {
                var8.printStackTrace();
                Log.e("Camera1", var8.getMessage());
            }

            this.camera.release();
            this.camera = null;
        }

        if (cameraFacing.facingType == FacingType.BACK) {
            cameraFacing.cameraId = 0;
        } else if (cameraFacing.facingType == FacingType.FRONT) {
            cameraFacing.cameraId = 1;
        }

        try {
            int numberOfCameras = Camera.getNumberOfCameras();

            for(int i = 0; i < numberOfCameras; ++i) {
                if (i == cameraFacing.cameraId) {
                    Camera camera = Camera.open(i);
                    Camera.Parameters cameraParameters = camera.getParameters();
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, cameraInfo);
                    Camera1.Camera1Attributes cameraAttributes = new Camera1.Camera1Attributes(cameraInfo, cameraParameters, cameraFacing);
                    this.camera = camera;
                    this.cameraAttributes = cameraAttributes;
                    if (this.callBackEvents != null) {
                        this.callBackEvents.onCameraOpen(cameraAttributes);
                    }
                }
            }

            this.mPreviewCallback = new Camera.PreviewCallback() {
                public void onPreviewFrame(byte[] data, Camera camera) {
                    if (Camera1.this.mCustomPreviewCallbacks != null) {
                        Iterator iterator = Camera1.this.mCustomPreviewCallbacks.iterator();

                        while(iterator.hasNext()) {
                            CameraPreviewCallback previewCallback = (CameraPreviewCallback)iterator.next();
                            System.arraycopy(data, 0, Camera1.this.mCallbackBuffer, 0, data.length);
                            if (previewCallback != null) {
                                previewCallback.onCallBackPreview(Camera1.this.mCallbackBuffer);
                            }
                        }
                    }

                    Camera1.this.addCallbackBuffer(camera, Camera1.this.mBuffer);
                }
            };
        } catch (Exception var9) {
            var9.printStackTrace();
            if (this.callBackEvents != null) {
                this.callBackEvents.onCameraError("open camera error!" + var9.getMessage());
            }
        }

    }

    public synchronized void addPreviewCallbackWithBuffer(CameraPreviewCallback callback) {
        if (this.mCustomPreviewCallbacks == null) {
            this.mCustomPreviewCallbacks = new HashSet();
        }

        this.mCustomPreviewCallbacks.add(callback);
    }

    public synchronized void removePreviewCallbackWithBuffer(CameraPreviewCallback callback) {
        if (this.mCustomPreviewCallbacks != null) {
            this.mCustomPreviewCallbacks.remove(callback);
        }

    }

    public void clearPreviewCallbackWithBuffer() {
        if (this.mCustomPreviewCallbacks != null) {
            this.mCustomPreviewCallbacks.clear();
        }

    }

    public synchronized void release() {
        if (this.camera != null) {
            this.camera.setPreviewCallback((Camera.PreviewCallback)null);
            this.camera.setPreviewCallbackWithBuffer((Camera.PreviewCallback)null);

            try {
                this.camera.setPreviewTexture((SurfaceTexture)null);
            } catch (IOException var2) {
                var2.printStackTrace();
                Log.e("Camera1", var2.getMessage());
            }

            this.camera.release();
            this.camera = null;
        }

        this.cameraAttributes = null;
        if (this.callBackEvents != null) {
            this.callBackEvents.onCameraClose();
        }

    }

    public synchronized void setPreviewOrientation(int degrees) {
        if (this.camera != null) {
            this.camera.setDisplayOrientation(degrees);
        }

    }

    public synchronized void setPreviewSize(CameraSize size) {
        if (this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.setPreviewSize(size.getWidth(), size.getHeight());
            this.camera.setParameters(parameters);
        }

    }

    public synchronized void startPreview(SurfaceTexture surfacetexture) {
        if (this.camera != null) {
            this.doInitPatameters(surfacetexture);
            this.camera.startPreview();
        }

    }

    private void doInitPatameters(SurfaceTexture surfaceTexture) {
        String errorMsg = null;
        if (this.camera != null && this.cameraAttributes != null) {
            try {
                Camera.Parameters parameters = this.camera.getParameters();
                if (parameters != null) {
                    Camera.Size camerasize = parameters.getPreviewSize();
                    if (camerasize == null) {
                        parameters.setPreviewSize(640, 480);
                        camerasize = parameters.getPreviewSize();
                    }

                    int size = camerasize.width * camerasize.height * 3 / 2;
                    if (this.mBuffer == null || this.mBuffer.length != size) {
                        this.mBuffer = new byte[size];
                        this.mCallbackBuffer = new byte[size];
                    }

                    parameters.setPreviewSize(camerasize.width, camerasize.height);
                    parameters.setPreviewFormat(17);
                    parameters.setPictureFormat(256);
                    if (parameters.isAutoExposureLockSupported()) {
                        parameters.setAutoExposureLock(true);
                    }

                    this.camera.setPreviewTexture(surfaceTexture);
                    this.camera.setParameters(parameters);
                    this.addCallbackBuffer(this.camera, this.mBuffer);
                    if (this.mPreviewCallback != null) {
                        this.camera.setPreviewCallbackWithBuffer(this.mPreviewCallback);
                    }

                    if (this.callBackEvents != null) {
                        this.callBackEvents.onPreviewStarted();
                    }
                } else {
                    errorMsg = "getParameters is null";
                }
            } catch (RuntimeException var10) {
                var10.printStackTrace();
                errorMsg = "camera get parameters error, e->" + var10.getClass().getSimpleName() + ", msg->" + var10.getMessage();
                Log.e("CameraManager", errorMsg);
            } catch (IOException var11) {
                var11.printStackTrace();
                errorMsg = "camera set preview texture error, e->" + var11.getClass().getSimpleName() + ", msg->" + var11.getMessage();
                Log.e("CameraManager", errorMsg);
            } finally {
                if (errorMsg != null && this.callBackEvents != null) {
                    this.callBackEvents.onPreviewError(errorMsg);
                }

            }
        }

    }

    private void addCallbackBuffer(Camera camera, byte[] buffer) {
        if (camera != null) {
            camera.addCallbackBuffer(buffer);
        }

    }

    public synchronized void stopPreview() {
        if (this.camera != null) {
            this.camera.stopPreview();
            if (this.callBackEvents != null) {
                this.callBackEvents.onPreviewStopped();
            }
        }

    }

    public synchronized void setFlash(CameraFlash flash) {
        if (this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            switch(flash) {
                case OFF:
                    parameters.setFlashMode("off");
                    break;
                case ON:
                    parameters.setFlashMode("on");
                    break;
                case AUTO:
                    parameters.setFlashMode("auto");
                    break;
                case TORCH:
                    parameters.setFlashMode("torch");
                    break;
                default:
                    parameters.setFlashMode("off");
            }

            try {
                this.camera.setParameters(parameters);
            } catch (Exception var4) {
            }
        }

    }

    public synchronized void setFocusMode(CameraFocus focus) {
        if (this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            switch(focus) {
                case AUTO:
                    parameters.setFocusMode("auto");
                    break;
                case INFINITY:
                    parameters.setFocusMode("infinity");
                    break;
                case MACRO:
                    parameters.setFocusMode("macro");
                    break;
                case FIXED:
                    parameters.setFocusMode("fixed");
                    break;
                case EDOF:
                    parameters.setFocusMode("edof");
                    break;
                case CONTINUOUS_PICTURE:
                    parameters.setFocusMode("continuous-picture");
                    break;
                case CONTINUOUS_VIDEO:
                    parameters.setFocusMode("continuous-video");
                    break;
                default:
                    parameters.setFocusMode("auto");
            }

            try {
                this.camera.setParameters(parameters);
            } catch (Exception var4) {
            }
        }

    }

    public synchronized void setExposureCompensation(int exposureCompensation) {
        if (this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.setExposureCompensation(exposureCompensation);

            try {
                this.camera.setParameters(parameters);
            } catch (Exception var4) {
            }
        }

    }

    public synchronized void setPhotoSize(CameraSize size) {
        if (this.camera != null) {
            Camera.Parameters parameters = this.camera.getParameters();
            parameters.setPictureSize(size.getWidth(), size.getHeight());

            try {
                this.camera.setParameters(parameters);
            } catch (Exception var4) {
            }
        }

    }

    public synchronized void capturePhoto(final CapturePhotoCallback callback) {
        if (this.camera != null) {
            this.camera.takePicture((Camera.ShutterCallback)null, (Camera.PictureCallback)null, new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                    callback.onCallBackPhoto(data);
                    camera.startPreview();
                }
            });
        }

    }

    class Camera1Attributes extends IAttributes {
        public Camera1Attributes(Camera.CameraInfo cameraInfo, @NonNull Camera.Parameters cameraParameters, CameraFacing cameraFacing) {
            this.facing = cameraFacing;
            this.orientation = cameraInfo.orientation;
            this.photoSize = new ArrayList();
            Iterator var5 = cameraParameters.getSupportedPictureSizes().iterator();

            Camera.Size size;
            while(var5.hasNext()) {
                size = (Camera.Size)var5.next();
                this.photoSize.add(new CameraSize(size.width, size.height));
            }

            this.previewSize = new ArrayList();
            var5 = cameraParameters.getSupportedPreviewSizes().iterator();

            while(var5.hasNext()) {
                size = (Camera.Size)var5.next();
                this.previewSize.add(new CameraSize(size.width, size.height));
            }

            this.flashes = new ArrayList();
            byte var8;
            String mode;
            if (cameraParameters.getSupportedFlashModes() != null && cameraParameters.getSupportedFlashModes() != null) {
                var5 = cameraParameters.getSupportedFlashModes().iterator();

                while(var5.hasNext()) {
                    mode = (String)var5.next();
                    var8 = -1;
                    switch(mode.hashCode()) {
                        case 3551:
                            if (mode.equals("on")) {
                                var8 = 1;
                            }
                            break;
                        case 109935:
                            if (mode.equals("off")) {
                                var8 = 0;
                            }
                            break;
                        case 3005871:
                            if (mode.equals("auto")) {
                                var8 = 2;
                            }
                            break;
                        case 110547964:
                            if (mode.equals("torch")) {
                                var8 = 3;
                            }
                    }

                    switch(var8) {
                        case 0:
                            this.flashes.add(CameraFlash.OFF);
                            break;
                        case 1:
                            this.flashes.add(CameraFlash.ON);
                            break;
                        case 2:
                            this.flashes.add(CameraFlash.AUTO);
                            break;
                        case 3:
                            this.flashes.add(CameraFlash.TORCH);
                            break;
                        default:
                            this.flashes.add(CameraFlash.OFF);
                    }
                }
            }

            this.focusList = new ArrayList();
            if (cameraParameters.getSupportedFocusModes() != null) {
                var5 = cameraParameters.getSupportedFocusModes().iterator();

                while(var5.hasNext()) {
                    mode = (String)var5.next();
                    var8 = -1;
                    switch(mode.hashCode()) {
                        case -194628547:
                            if (mode.equals("continuous-video")) {
                                var8 = 5;
                            }
                            break;
                        case 3005871:
                            if (mode.equals("auto")) {
                                var8 = 0;
                            }
                            break;
                        case 3108534:
                            if (mode.equals("edof")) {
                                var8 = 3;
                            }
                            break;
                        case 97445748:
                            if (mode.equals("fixed")) {
                                var8 = 4;
                            }
                            break;
                        case 103652300:
                            if (mode.equals("macro")) {
                                var8 = 2;
                            }
                            break;
                        case 173173288:
                            if (mode.equals("infinity")) {
                                var8 = 1;
                            }
                    }

                    switch(var8) {
                        case 0:
                            this.focusList.add(CameraFocus.AUTO);
                            break;
                        case 1:
                            this.focusList.add(CameraFocus.INFINITY);
                            break;
                        case 2:
                            this.focusList.add(CameraFocus.MACRO);
                            break;
                        case 3:
                            this.focusList.add(CameraFocus.EDOF);
                            break;
                        case 4:
                            this.focusList.add(CameraFocus.FIXED);
                            break;
                        case 5:
                            this.focusList.add(CameraFocus.CONTINUOUS_VIDEO);
                            break;
                        default:
                            this.flashes.add(CameraFlash.AUTO);
                    }
                }
            }

        }
    }
}

