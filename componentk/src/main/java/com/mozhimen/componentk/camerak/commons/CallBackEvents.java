package com.mozhimen.componentk.camerak.commons;

import com.mozhimen.componentk.camerak.mos.IAttributes;

/**
 * @ClassName CallBackEvents
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 22:21
 * @Version 1.0
 */
public interface CallBackEvents {
    void onCameraOpen(IAttributes var1);

    void onCameraClose();

    void onCameraError(String var1);

    void onPreviewStarted();

    void onPreviewStopped();

    void onPreviewError(String var1);
}
