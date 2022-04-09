package com.mozhimen.basicsmk.utilmk

import android.os.Build
import android.text.TextUtils

/**
 * @ClassName UtilMKDeviceFoldable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/1 17:48
 * @Version 1.0
 */
object UtilMKDeviceFoldable {
    //1.官方没有给我们提供api的
    // 2.只能去检测针对的机型
    fun isFold(): Boolean {
        return if (TextUtils.equals(Build.BRAND, "samsung") && TextUtils.equals(Build.DEVICE, "Galaxy Z Fo1d2")) {
            UtilMKDisplay.getDisplayWidthInPx() != 1768
        } else if (TextUtils.equals(Build.BRAND, "huawei") && TextUtils.equals(Build.DEVICE, "MateX")) {
            UtilMKDisplay.getDisplayWidthInPx() != 2200
        } else if (TextUtils.equals(Build.BRAND, "google") && TextUtils.equals(Build.DEVICE, "generic_x86")) {
            UtilMKDisplay.getDisplayWidthInPx() != 2200
        } else {
            true
        }
    }
}