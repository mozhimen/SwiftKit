package com.mozhimen.componentk.camerak;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

/**
 * @ClassName CameraHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:21
 * @Version 1.0
 */
public class CameraHandler extends Handler {
    public static CameraHandler get() {
        HandlerThread cameraThread = new HandlerThread("CameraHandler@" + System.currentTimeMillis());
        cameraThread.start();
        return new CameraHandler(cameraThread);
    }

    public CameraHandler(@NonNull HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        handlerThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            }
        });
    }

    public CameraHandler(@NonNull Handler handler) {
        super(handler.getLooper());
    }
}
