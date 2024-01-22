package com.mozhimen.basick.utilk.android.hardware

import android.os.Build
import android.os.Environment
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.os.UtilKEnvironment
import com.mozhimen.basick.utilk.android.os.UtilKStatFs
import com.mozhimen.basick.utilk.android.telephony.UtilKImei
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.java.io.UtilKRandomAccessFile
import com.mozhimen.basick.utilk.java.io.UtilKReader
import com.mozhimen.basick.utilk.android.os.UtilKSystemProperties

/**
 * @ClassName UtilKDevice
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 19:40
 * @Version 1.0
 */
@AManifestKRequire(CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE)
object UtilKDevice : BaseUtilK() {

    @JvmStatic
    @RequiresPermission(CPermission.READ_PHONE_STATE)
    @AManifestKRequire(CPermission.READ_PHONE_STATE)
    fun getImei(): String =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            UtilKImei.getImei(_context)
        } else ""

    /**
     * 设备内存空间
     */
    @JvmStatic
    fun getMemorySize(): String? =
        UtilKReader.getMemorySize()

    /**
     * cpu使用率
     */
    @JvmStatic
    fun getCpuUsage(): Float =
        UtilKRandomAccessFile.getCpuUsage()

    /**
     * 设备Rom版本
     */
    @JvmStatic
    fun getRomVersion(): String =
        UtilKSystemProperties.getRomVersion()

    /**
     * 设备硬件版本
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        UtilKSystemProperties.getHwVersion()

    /**
     * 序列号
     */
    @JvmStatic
    fun getSerialNumber(): String =
        UtilKSystemProperties.getSerialNumber()

    /**
     * 短序列号
     */
    @JvmStatic
    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14)
            serial = serial.substring(14)
        return serial
    }

    /**
     * 本地存储可用大小
     */
    @JvmStatic
    fun getFreeInternalMemorySize(): String =
        UtilKStatFs.getStrFreeExternalDataMemorySize()

    @JvmStatic
    fun getAvailableInternalMemorySize(): String =
        UtilKStatFs.getStrAvailableExternalDataMemorySize()

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getTotalInternalMemorySize(): String =
        UtilKStatFs.getStrTotalExternalDataMemorySize()

    /**
     * 获取手机空闲空间大小
     */
    @JvmStatic
    fun getFreeExternalMemorySize(): String =
        if (hasExternalStorage()) {
            UtilKStatFs.getStrFreeExternalStorageMemorySize()
        } else "0"

    @JvmStatic
    fun getAvailableExternalMemorySize(): String =
        if (hasExternalStorage()) {
            UtilKStatFs.getStrAvailableExternalStorageMemorySize()
        } else "0"

    /**
     * 获取手机外部空间大小
     */
    @JvmStatic
    fun getTotalExternalMemorySize(): String =
        if (hasExternalStorage()) {
            UtilKStatFs.getStrTotalExternalStorageMemorySize()
        } else "0"

    ///////////////////////////////////////////////////////////////////////////////

    /**
     * 是否是折叠机型
     * 1.官方没有给我们提供api的
     * 2.只能去检测针对的机型
     */
    fun isFoldable(): Boolean {
        return if (TextUtils.equals(Build.BRAND, "samsung") && TextUtils.equals(Build.DEVICE, "Galaxy Z Fo1d2")) {
            UtilKScreen.getWidthOfWindow() != 1768
        } else if (TextUtils.equals(Build.BRAND, "huawei") && TextUtils.equals(Build.DEVICE, "MateX")) {
            UtilKScreen.getWidthOfWindow() != 2200
        } else if (TextUtils.equals(Build.BRAND, "google") && TextUtils.equals(Build.DEVICE, "generic_x86")) {
            UtilKScreen.getWidthOfWindow() != 2200
        } else {
            true
        }
    }

    @JvmStatic
    fun hasFrontCamera(): Boolean =
        UtilKCamera.hasFrontCamera(_context)

    @JvmStatic
    fun hasBackCamera(): Boolean =
        UtilKCamera.hasBackCamera(_context)

    /**
     * 设备是否有sd卡
     */
    @JvmStatic
    fun hasSdcard(): Boolean =
        TextUtils.equals(Environment.getExternalStorageState(), "mounted")

    /**
     * 设备是否有USB外设
     */
    @JvmStatic
    fun hasPid(vendorId: Int, productId: Int): Boolean =
        UtilKUsbManager.hasPid(_context, vendorId, productId)

    /**
     * 是否有外部存储
     */
    @JvmStatic
    fun hasExternalStorage(): Boolean =
        UtilKEnvironment.isExternalStorageMounted()
}