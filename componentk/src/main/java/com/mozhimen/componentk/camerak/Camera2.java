package com.mozhimen.componentk.camerak;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.mozhimen.basick.utilk.bitmap.UtilKBitmapFormat;
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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Camera2
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 23:08
 * @Version 1.0
 */
public class Camera2 extends CameraApi {
    private static final String TAG = "Camera2";
    private static final int DEFAULT_PREVIEW_WIDTH = 640;
    private static final int DEFAULT_PREVIEW_HEIGHT = 480;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_PICTURE_TAKEN = 4;
    private CameraDevice mCameraDevice = null;
    private Camera2.Camera2Attributes mCameraAttributes = null;
    private HashSet<CameraPreviewCallback> mCustomPreviewCallbacks;
    private android.hardware.camera2.CameraManager cameraManager;
    private Context context;
    private ImageReader imageReader = null;
    private CameraFlash flash;
    private CameraFocus focus;
    private int exposureCompensation;
    private CapturePhotoCallback photoCallback;
    private CameraFacing cameraFacing;
    private CameraCaptureSession mCaptureSession;
    private CaptureRequest.Builder previewRequestBuilder;
    private boolean previewStarted;
    private int captureState;
    private int waitingFrames;
    private ImageReader.OnImageAvailableListener mOnImageAvailableListener;
    private CameraCaptureSession.CaptureCallback captureCallback;

    @RequiresApi(
            api = 21
    )
    public Camera2(@NonNull CallBackEvents callBackEvents, Context context) {
        this.flash = CameraFlash.OFF;
        this.focus = CameraFocus.AUTO;
        this.exposureCompensation = 0;
        this.photoCallback = null;
        this.cameraFacing = (new CameraFacing.Builder()).build();
        this.mCaptureSession = null;
        this.previewRequestBuilder = null;
        this.previewStarted = false;
        this.captureState = 0;
        this.waitingFrames = 0;
        this.mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
            private byte[] y;
            private byte[] u;
            private byte[] v;
            private byte[] nv21;
            private ReentrantLock lock = new ReentrantLock();

            public void onImageAvailable(ImageReader reader) {
                Image mImage = reader.acquireNextImage();

                try {
                    if (Camera2.this.mCustomPreviewCallbacks != null) {
                        for (Iterator iterator = Camera2.this.mCustomPreviewCallbacks.iterator(); iterator.hasNext(); mImage.close()) {
                            CameraPreviewCallback previewCallback = (CameraPreviewCallback) iterator.next();
                            if (mImage.getFormat() == 35) {
                                Image.Plane[] planes = mImage.getPlanes();
                                int width = mImage.getCropRect().width();
                                int height = mImage.getCropRect().height();
                                this.lock.lock();
                                if (this.y == null) {
                                    this.y = new byte[planes[0].getBuffer().limit() - planes[0].getBuffer().position()];
                                    this.u = new byte[planes[1].getBuffer().limit() - planes[1].getBuffer().position()];
                                    this.v = new byte[planes[2].getBuffer().limit() - planes[2].getBuffer().position()];
                                }

                                if (mImage.getPlanes()[0].getBuffer().remaining() == this.y.length) {
                                    planes[0].getBuffer().get(this.y);
                                    planes[1].getBuffer().get(this.u);
                                    planes[2].getBuffer().get(this.v);
                                    if (this.nv21 == null) {
                                        this.nv21 = new byte[planes[0].getRowStride() * height * 3 / 2];
                                    }

                                    if (this.y.length / this.u.length == 2) {
                                        UtilKBitmapFormat.yuv422Bytes2Yuv420spBytes(this.y, this.u, this.v, this.nv21, planes[0].getRowStride(), height);
                                    } else if (this.y.length / this.u.length == 4) {
                                        UtilKBitmapFormat.yuv420Bytes2Yuv420spBytes(this.y, this.u, this.v, this.nv21, planes[0].getRowStride(), height);
                                    }

                                    if (previewCallback != null) {
                                        previewCallback.onCallBackPreview(this.nv21);
                                    }
                                }

                                this.lock.unlock();
                            }
                        }
                    }
                } catch (IllegalStateException var8) {
                    Log.e("Camera2", var8.getMessage());
                }

                mImage.close();
            }
        };
        this.captureCallback = new CameraCaptureSession.CaptureCallback() {
            private void process(CaptureResult result) {
                int afState;
                switch (Camera2.this.captureState) {
                    case 0:
                        if (Camera2.this.imageReader != null) {
                            Image image = Camera2.this.imageReader.acquireLatestImage();
                            if (image != null) {
                                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                if (Camera2.this.photoCallback != null) {
                                    Camera2.this.photoCallback.onCallBackPhoto(bytes);
                                    Camera2.this.photoCallback = null;
                                }

                                image.close();
                            }
                        }
                    case 1:
                        afState = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
                        if (4 != afState && 5 != afState) {
                            if (0 == afState) {
                                Camera2.this.captureStillPicture();
                            } else if (Camera2.this.waitingFrames >= 5) {
                                Camera2.this.waitingFrames = 0;
                                Camera2.this.captureStillPicture();
                            } else {
                                Camera2.this.waitingFrames++;
                            }
                        } else {
                            Camera2.this.runPreCaptureSequence();
                        }
                    case 2:
                        afState = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                        if (afState == 5 || afState == 4) {
                            Camera2.this.captureState = 3;
                        }
                    case 3:
                        break;
                    default:
                        return;
                }

                afState = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                if (afState != 5) {
                    Camera2.this.captureState = 4;
                    Camera2.this.captureStillPicture();
                }

            }

            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                if (!Camera2.this.previewStarted) {
                    if (Camera2.this.callBackEvents != null) {
                        Camera2.this.callBackEvents.onPreviewStarted();
                    }

                    Camera2.this.previewStarted = true;
                }

                this.process(result);
            }

            public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                this.process(partialResult);
            }
        };
        this.cameraHandler = CameraHandler.get();
        this.callBackEvents = callBackEvents;
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

    @RequiresApi(
            api = 21
    )
    public synchronized void openCamera(final CameraFacing cameraFacing) {
        if (cameraFacing.facingType == FacingType.BACK) {
            cameraFacing.cameraId = 1;
        } else if (cameraFacing.facingType == FacingType.FRONT) {
            cameraFacing.cameraId = 0;
        }

        try {
            String[] cameraIds = this.cameraManager.getCameraIdList();

            for (int i = 0; i < cameraIds.length; ++i) {
                final String targetCameraId = cameraIds[i];
                final CameraCharacteristics cameraCharacteristics = this.cameraManager.getCameraCharacteristics(targetCameraId);
                if (Integer.valueOf(targetCameraId) == cameraFacing.cameraId) {
                    this.cameraManager.registerAvailabilityCallback(new CameraManager.AvailabilityCallback() {
                        public void onCameraAvailable(String cameraId) {
                            if (cameraId == targetCameraId) {
                                Camera2.this.cameraManager.unregisterAvailabilityCallback(this);

                                try {
                                    if (ActivityCompat.checkSelfPermission(Camera2.this.context, "android.permission.CAMERA") != 0) {
                                        return;
                                    }

                                    Camera2.this.cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                                        public void onOpened(CameraDevice cameraDevice) {
                                            Camera2.Camera2Attributes cameraAttributes = Camera2.this.new Camera2Attributes(cameraCharacteristics, cameraFacing);
                                            Camera2.this.mCameraDevice = cameraDevice;
                                            Camera2.this.mCameraAttributes = cameraAttributes;
                                            if (Camera2.this.callBackEvents != null) {
                                                Camera2.this.callBackEvents.onCameraOpen(cameraAttributes);
                                            }

                                        }

                                        public void onDisconnected(CameraDevice cameraDevice) {
                                            cameraDevice.close();
                                            Camera2.this.mCameraDevice = null;
                                            Camera2.this.mCameraAttributes = null;
                                            if (Camera2.this.callBackEvents != null) {
                                                Camera2.this.callBackEvents.onCameraClose();
                                            }

                                        }

                                        public void onError(CameraDevice cameraDevice, int error) {
                                            cameraDevice.close();
                                            Camera2.this.mCameraDevice = null;
                                            Camera2.this.mCameraAttributes = null;
                                            if (Camera2.this.callBackEvents != null) {
                                                Camera2.this.callBackEvents.onCameraError("open camera error!");
                                            }

                                        }
                                    }, Camera2.this.cameraHandler);
                                } catch (CameraAccessException var3) {
                                    var3.printStackTrace();
                                }
                            }

                        }

                        public void onCameraUnavailable(String cameraId) {
                            if (cameraId == targetCameraId) {
                            }

                        }
                    }, this.cameraHandler);
                }
            }
        } catch (Exception var6) {
            if (this.callBackEvents != null) {
                this.callBackEvents.onCameraError("open camera error!");
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

    @RequiresApi(
            api = 21
    )
    public synchronized void release() {
        this.cameraHandler.removeCallbacksAndMessages((Object) null);
        if (this.imageReader != null) {
            this.imageReader.close();
            this.imageReader = null;
        }

        if (this.mCaptureSession != null) {
            try {
                this.mCaptureSession.stopRepeating();
                this.mCaptureSession.abortCaptures();
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            } catch (CameraAccessException var2) {
                var2.printStackTrace();
            }
        }

        if (this.mCameraDevice != null) {
            this.mCameraDevice.close();
            this.mCameraDevice = null;
        }

        this.mCameraAttributes = null;
        this.previewStarted = false;
        if (this.callBackEvents != null) {
            this.callBackEvents.onCameraClose();
        }

    }

    public synchronized void setPreviewOrientation(int degrees) {
    }

    public synchronized void setPreviewSize(CameraSize size) {
        this.imageReader = ImageReader.newInstance(size.getWidth(), size.getHeight(), ImageFormat.YUV_420_888/*35*/, 2);
        this.imageReader.setOnImageAvailableListener(this.mOnImageAvailableListener, this.cameraHandler);
    }

    @RequiresApi(
            api = 21
    )
    public synchronized void startPreview(SurfaceTexture surfaceTexture) {
        if (this.mCameraDevice != null && this.imageReader != null) {
            final Surface surface = new Surface(surfaceTexture);

            try {
                this.mCameraDevice.createCaptureSession(Arrays.asList(surface, this.imageReader.getSurface()), new android.hardware.camera2.CameraCaptureSession.StateCallback() {
                    public void onConfigured(CameraCaptureSession captureSession) {
                        if (captureSession != null) {
                            Camera2.this.mCaptureSession = captureSession;
                        }

                        Camera2.this.getCaptureSession(captureSession, surface);
                    }

                    public void onClosed(CameraCaptureSession session) {
                        Camera2.this.getCaptureSession((CameraCaptureSession) null, (Surface) null);
                        super.onClosed(session);
                    }

                    public void onConfigureFailed(CameraCaptureSession captureSession) {
                        Camera2.this.getCaptureSession((CameraCaptureSession) null, (Surface) null);
                    }
                }, this.cameraHandler);
            } catch (CameraAccessException var4) {
                if (this.callBackEvents != null) {
                    this.callBackEvents.onPreviewError("CameraAccessException e->" + var4.getMessage());
                }
            }
        } else if (this.callBackEvents != null) {
            this.callBackEvents.onPreviewError("Is set PhotoSize?");
        }

    }

    private void getCaptureSession(CameraCaptureSession captureSession, Surface surface) {
        try {
            if (captureSession != null && surface != null) {
                CaptureRequest.Builder previewRequestBuilder = this.mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                previewRequestBuilder.addTarget(surface);
                previewRequestBuilder.set(CaptureRequest.FLASH_MODE, this.flash == CameraFlash.ON ? 2 : 0);
                int foucstype = 1;
                switch (this.focus) {
                    case AUTO:
                        foucstype = 1;
                        break;
                    case MACRO:
                        foucstype = 2;
                        break;
                    case EDOF:
                        foucstype = 5;
                        break;
                    case CONTINUOUS_VIDEO:
                        foucstype = 3;
                        break;
                    case CONTINUOUS_PICTURE:
                        foucstype = 4;
                        break;
                    case OFF:
                        foucstype = 0;
                }

                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(foucstype));
                previewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
                captureSession.setRepeatingRequest(previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
                this.previewRequestBuilder = previewRequestBuilder;
            } else if (this.callBackEvents != null) {
                this.callBackEvents.onPreviewError("captureSession is null!");
            }
        } catch (CameraAccessException var5) {
            if (this.callBackEvents != null) {
                this.callBackEvents.onPreviewError("CameraAccessException e->" + var5.getMessage());
            }
        }

    }

    public synchronized void stopPreview() {
        if (this.mCaptureSession != null) {
            try {
                this.mCaptureSession.stopRepeating();
                this.mCaptureSession.abortCaptures();
                this.mCaptureSession.close();
            } catch (Exception var5) {
            } finally {
                if (this.callBackEvents != null) {
                    this.callBackEvents.onPreviewStopped();
                }

            }
        }

        this.mCaptureSession = null;
        this.previewStarted = false;
    }

    public synchronized void setFlash(CameraFlash flash) {
        this.flash = flash;
    }

    public synchronized void setFocusMode(CameraFocus focus) {
        this.focus = focus;
    }

    public synchronized void setExposureCompensation(int exposureCompensation) {
        this.exposureCompensation = exposureCompensation;
    }

    @RequiresApi(
            api = 21
    )
    public synchronized void setPhotoSize(CameraSize size) {
    }

    public synchronized void capturePhoto(CapturePhotoCallback callback) {
        this.photoCallback = callback;
        if (this.cameraFacing.facingType == FacingType.BACK) {
            this.lockFocus();
        } else {
            this.captureStillPicture();
        }

    }

    private void lockFocus() {
        if (this.previewRequestBuilder != null && this.mCaptureSession != null) {
            try {
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                this.captureState = 1;
                this.waitingFrames = 0;
                this.mCaptureSession.capture(this.previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, null);
            } catch (Exception var2) {
            }
        }

    }

    private void runPreCaptureSequence() {
        if (this.previewRequestBuilder != null && this.mCaptureSession != null) {
            try {
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
                this.captureState = 2;
                this.mCaptureSession.capture(this.previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, null);
                this.previewRequestBuilder.set(CaptureRequest.FLASH_MODE, this.flash == CameraFlash.ON ? 2 : 0);
                this.mCaptureSession.setRepeatingRequest(this.previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
            } catch (CameraAccessException var2) {
            }
        }

    }

    private void captureStillPicture() {
        if (this.mCaptureSession != null && this.mCameraDevice != null && this.imageReader != null) {
            try {
                final CaptureRequest.Builder captureBuilder = this.mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(this.imageReader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                captureBuilder.set(CaptureRequest.FLASH_MODE, this.flash == CameraFlash.ON ? 2 : 0);
                long delay = this.flash == CameraFlash.ON ? 75L : 0L;
                this.cameraHandler.postDelayed(new Runnable() {
                    public void run() {
                        try {
                            Camera2.this.mCaptureSession.capture(captureBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                    Camera2.this.unlockFocus();
                                }
                            }, Camera2.this.cameraHandler);
                        } catch (CameraAccessException var2) {
                        }

                    }
                }, delay);
            } catch (CameraAccessException var4) {
            }
        }

    }

    private void unlockFocus() {
        if (this.previewRequestBuilder != null && this.mCaptureSession != null) {
            try {
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.mCaptureSession.capture(this.previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
                this.captureState = 0;
                this.previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, null);
                this.previewRequestBuilder.set(CaptureRequest.FLASH_MODE, this.flash == CameraFlash.ON ? 2 : 0);
                this.mCaptureSession.setRepeatingRequest(this.previewRequestBuilder.build(), this.captureCallback, this.cameraHandler);
            } catch (CameraAccessException var2) {
                var2.printStackTrace();
            }
        }

    }

    class Camera2Attributes extends IAttributes {
        @RequiresApi(api = 21)
        public Camera2Attributes(CameraCharacteristics cameraCharacteristics, CameraFacing cameraFacing) {
            this.facing = cameraFacing;
            this.orientation = (Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            this.photoSize = new ArrayList();
            StreamConfigurationMap map = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] photoSizes = map.getOutputSizes(256);
            Size[] previewSizes = photoSizes;
            int var7 = photoSizes.length;

            int var8;
            for (var8 = 0; var8 < var7; ++var8) {
                Size size = previewSizes[var8];
                this.photoSize.add(new CameraSize(size.getWidth(), size.getHeight()));
            }

            this.previewSize = new ArrayList();
            previewSizes = map.getOutputSizes(SurfaceHolder.class);
            Size[] var13 = previewSizes;
            var8 = previewSizes.length;

            for (int var16 = 0; var16 < var8; ++var16) {
                Size sizex = var13[var16];
                this.previewSize.add(new CameraSize(sizex.getWidth(), sizex.getHeight()));
            }

            this.flashes = new ArrayList();
            boolean flashSupported = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            if (flashSupported) {
                this.flashes.add(CameraFlash.OFF);
                this.flashes.add(CameraFlash.ON);
                this.flashes.add(CameraFlash.AUTO);
                this.flashes.add(CameraFlash.TORCH);
            }

            this.focusList = new ArrayList();
            int[] afAvailableModes = (int[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
            int[] var17 = afAvailableModes;
            int var18 = afAvailableModes.length;

            for (int var11 = 0; var11 < var18; ++var11) {
                int mode = var17[var11];
                switch (mode) {
                    case 1:
                        this.focusList.add(CameraFocus.AUTO);
                        break;
                    case 2:
                        this.focusList.add(CameraFocus.MACRO);
                        break;
                    case 3:
                        this.focusList.add(CameraFocus.CONTINUOUS_VIDEO);
                        break;
                    case 4:
                        this.focusList.add(CameraFocus.CONTINUOUS_PICTURE);
                        break;
                    case 5:
                        this.focusList.add(CameraFocus.EDOF);
                }
            }

        }
    }
}
