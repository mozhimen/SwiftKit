package com.mozhimen.componentk.camerak;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.usb.UsbDevice;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.NonNull;

import com.lgh.uvccamera.UVCCameraProxy;
import com.lgh.uvccamera.bean.PicturePath;
import com.lgh.uvccamera.callback.ConnectCallback;
import com.lgh.uvccamera.callback.PictureCallback;
import com.lgh.uvccamera.callback.PreviewCallback;
import com.lgh.uvccamera.config.CameraConfig;
import com.lgh.uvccamera.utils.LogUtil;
import com.mozhimen.componentk.camerak.bases.CameraApi;
import com.mozhimen.componentk.camerak.commons.CallBackEvents;
import com.mozhimen.componentk.camerak.commons.CameraPreviewCallback;
import com.mozhimen.componentk.camerak.commons.CapturePhotoCallback;
import com.mozhimen.componentk.camerak.cons.CameraFlash;
import com.mozhimen.componentk.camerak.cons.CameraFocus;
import com.mozhimen.componentk.camerak.mos.CameraFacing;
import com.mozhimen.componentk.camerak.mos.CameraSize;
import com.mozhimen.componentk.camerak.mos.IAttributes;
import com.serenegiant.usb.Size;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName CameraUvc
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:24
 * @Version 1.0
 */
public class CameraUvc extends CameraApi {
    private static final String TAG = "CameraUvc";
    private static final int DEFAULT_PREVIEW_WIDTH = 640;
    private static final int DEFAULT_PREVIEW_HEIGHT = 480;
    private UVCCameraProxy camera = null;
    private CameraUvc.CameraUvcAttributes cameraAttributes = null;
    private HashSet<CameraPreviewCallback> mCustomPreviewCallbacks;
    private Context context;
    private byte[] mCallbackBuffer;

    public CameraUvc(@NonNull CallBackEvents callBackEvents, Context context, Handler handler) {
        this.cameraHandler = new CameraHandler(handler);
        this.callBackEvents = callBackEvents;
        this.context = context;
    }

    public synchronized void openCamera(CameraFacing cameraFacing) {
        try {
            this.camera = new UVCCameraProxy(this.context);
            this.camera.getConfig().isDebug(true).setPicturePath(PicturePath.APPCACHE).setDirName("uvccamera").setProductId(cameraFacing.pid).setVendorId(cameraFacing.vid);
            this.cameraAttributes = new CameraUvc.CameraUvcAttributes(this.camera.getConfig(), this.camera.getSupportedPreviewSizes(), cameraFacing);
            this.camera.setConnectCallback(new ConnectCallback() {
                public void onAttached(UsbDevice usbDevice) {
                    LogUtil.i("UsbDevice-> onAttached");
                    CameraUvc.this.camera.requestPermission(usbDevice);
                }

                public void onGranted(UsbDevice usbDevice, boolean granted) {
                    if (granted) {
                        LogUtil.i("UsbDevice-> onGranted");
                        CameraUvc.this.camera.connectDevice(usbDevice);
                    }

                }

                public void onConnected(UsbDevice usbDevice) {
                    CameraUvc.this.camera.openCamera();
                }

                public void onCameraOpened() {
                    LogUtil.i("UsbDevice-> onCameraOpened");
                    CameraUvc.this.camera.startPreview();
                    if (CameraUvc.this.callBackEvents != null) {
                        CameraUvc.this.callBackEvents.onPreviewStarted();
                    }

                }

                public void onDetached(UsbDevice usbDevice) {
                    CameraUvc.this.camera.closeCamera();
                    if (CameraUvc.this.callBackEvents != null) {
                        CameraUvc.this.callBackEvents.onCameraClose();
                    }

                }

                public void onUsbCameraError(String msg) {
                    CameraUvc.this.camera.closeCamera();
                    if (CameraUvc.this.callBackEvents != null) {
                        CameraUvc.this.callBackEvents.onCameraError(msg);
                    }

                }
            });
            if (this.callBackEvents != null) {
                this.callBackEvents.onCameraOpen(this.cameraAttributes);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            if (this.callBackEvents != null) {
                this.callBackEvents.onCameraError("open camera error!" + var3.getMessage());
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
            this.camera.clearCache();
            this.camera.setPreviewCallback((PreviewCallback)null);
            this.camera.setPreviewTexture((TextureView)null);
            this.camera.setPreviewSurfaceTexture((SurfaceTexture)null);
            LogUtil.i("onSurfaceTextureDestroyed");
            this.camera.unregisterReceiver();
            this.camera.closeCamera();
            this.camera.closeDevice();
            this.camera = null;
        }

        this.cameraAttributes = null;
        if (this.callBackEvents != null) {
            this.callBackEvents.onCameraClose();
        }

    }

    public synchronized void setPreviewOrientation(int degrees) {
        if (this.camera != null) {
            this.camera.setPreviewRotation((float)degrees);
        }

    }

    public synchronized void setPreviewSize(CameraSize size) {
        if (this.camera != null) {
            this.camera.setPreviewSize(size.getWidth(), size.getHeight());
            int lenght = size.getWidth() * size.getHeight() * 3 / 2;
            this.mCallbackBuffer = new byte[lenght];
        }

    }

    public synchronized void startPreview(SurfaceTexture surfacetexture) {
        if (this.camera != null) {
            this.doInitPatameters(surfacetexture);
        }

    }

    private void doInitPatameters(SurfaceTexture surfaceTexture) {
        String errorMsg = null;
        if (this.camera != null && this.cameraAttributes != null) {
            try {
                this.camera.setPreviewCallback(new PreviewCallback() {
                    public void onPreviewFrame(byte[] yuv) {
                        if (CameraUvc.this.mCustomPreviewCallbacks != null) {
                            Iterator iterator = CameraUvc.this.mCustomPreviewCallbacks.iterator();

                            while(iterator.hasNext()) {
                                CameraPreviewCallback previewCallback = (CameraPreviewCallback)iterator.next();
                                System.arraycopy(yuv, 0, CameraUvc.this.mCallbackBuffer, 0, yuv.length);
                                if (previewCallback != null) {
                                    previewCallback.onCallBackPreview(CameraUvc.this.mCallbackBuffer);
                                }
                            }
                        }

                    }
                });
                this.camera.setPreviewSurfaceTexture(surfaceTexture);
                this.camera.startPreview();
            } catch (RuntimeException var7) {
                var7.printStackTrace();
                errorMsg = "camera get parameters error, e->" + var7.getClass().getSimpleName() + ", msg->" + var7.getMessage();
                Log.e("CameraManager", errorMsg);
            } finally {
                if (errorMsg != null && this.callBackEvents != null) {
                    this.callBackEvents.onPreviewError(errorMsg);
                }

            }
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
        }

    }

    public synchronized void setFocusMode(CameraFocus focus) {
        if (this.camera != null) {
        }

    }

    public synchronized void setExposureCompensation(int exposureCompensation) {
        if (this.camera != null) {
        }

    }

    public synchronized void setPhotoSize(CameraSize size) {
        if (this.camera != null) {
        }

    }

    public synchronized void capturePhoto(final CapturePhotoCallback callback) {
        if (this.camera != null) {
            this.camera.setPictureTakenCallback(new PictureCallback() {
                public void onPictureTaken(String path) {
                    try {
                        Bitmap bp = CameraUvc.getBitmap(CameraUvc.this.context.getContentResolver(), Uri.parse(path));
                        callback.onCallBackPhoto(CameraUvc.bitmapToNv21(bp, bp.getWidth(), bp.getHeight()));
                    } catch (IOException var3) {
                        callback.onCallBackPhoto((byte[])null);
                    }

                }
            });
            this.camera.takePicture("takepicture.jpg");
        }

    }

    public static final Bitmap getBitmap(ContentResolver cr, Uri url) throws FileNotFoundException, IOException {
        InputStream input = cr.openInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }

    public static byte[] bitmapToNv21(Bitmap src, int width, int height) {
        if (src != null && src.getWidth() >= width && src.getHeight() >= height) {
            int[] argb = new int[width * height];
            src.getPixels(argb, 0, width, 0, 0, width, height);
            return argbToNv21(argb, width, height);
        } else {
            return null;
        }
    }

    private static byte[] argbToNv21(int[] argb, int width, int height) {
        int frameSize = width * height;
        int yIndex = 0;
        int uvIndex = frameSize;
        int index = 0;
        byte[] nv21 = new byte[width * height * 3 / 2];

        for(int j = 0; j < height; ++j) {
            for(int i = 0; i < width; ++i) {
                int R = (argb[index] & 16711680) >> 16;
                int G = (argb[index] & '\uff00') >> 8;
                int B = argb[index] & 255;
                int Y = (66 * R + 129 * G + 25 * B + 128 >> 8) + 16;
                int U = (-38 * R - 74 * G + 112 * B + 128 >> 8) + 128;
                int V = (112 * R - 94 * G - 18 * B + 128 >> 8) + 128;
                nv21[yIndex++] = (byte)(Y < 0 ? 0 : (Y > 255 ? 255 : Y));
                if (j % 2 == 0 && index % 2 == 0 && uvIndex < nv21.length - 2) {
                    nv21[uvIndex++] = (byte)(V < 0 ? 0 : (V > 255 ? 255 : V));
                    nv21[uvIndex++] = (byte)(U < 0 ? 0 : (U > 255 ? 255 : U));
                }

                ++index;
            }
        }

        return nv21;
    }

    class CameraUvcAttributes extends IAttributes {
        public CameraUvcAttributes(CameraConfig config, List<Size> previewList, CameraFacing cameraFacing) {
            this.facing = cameraFacing;
            this.orientation = -1;
            this.photoSize = new ArrayList();
            this.previewSize = new ArrayList();
            Iterator var5 = previewList.iterator();

            while(var5.hasNext()) {
                Size size = (Size)var5.next();
                this.previewSize.add(new CameraSize(size.width, size.height));
            }

            this.flashes = new ArrayList();
            this.focusList = new ArrayList();
        }
    }
}
