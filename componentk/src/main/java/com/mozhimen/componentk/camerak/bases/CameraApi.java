package com.mozhimen.componentk.camerak.bases;

import androidx.lifecycle.LifecycleObserver;

import com.mozhimen.componentk.camerak.CameraHandler;
import com.mozhimen.componentk.camerak.commons.CallBackEvents;
import com.mozhimen.componentk.camerak.commons.CameraActions;

/**
 * @ClassName CameraApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:15
 * @Version 1.0
 */
public abstract class CameraApi implements CameraActions, LifecycleObserver {
    public CameraHandler cameraHandler;
    protected CallBackEvents callBackEvents;

    public CameraApi() {
    }
}

