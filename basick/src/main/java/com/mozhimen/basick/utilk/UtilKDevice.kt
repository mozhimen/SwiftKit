package com.mozhimen.basick.utilk

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import com.mozhimen.basick.logk.LogK
import java.lang.reflect.Method
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * @ClassName UtilKDevice
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 19:40
 * @Version 1.0
 */
object UtilKDevice {
    private const val TAG = "UtilKDevice>>>>>"
    private const val DEVICE_SYSTEM_PROPERTIES = "android.os.SystemProperties"
    private const val DEVICE_ROM_VERSION = "ro.product.rom.version"
    private const val DEVICE_HW_VERSION = "ro.product.hw.version"
    private const val DEVICE_SERIAL_NUMBER = "ro.serialno"
    private const val NO_DEFINED = "unknown"

    //设备IP
    fun getDeviceIP(): String {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface: NetworkInterface = networkInterfaces.nextElement() as NetworkInterface
                val inetAddresses: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address) {
                        val ip = inetAddress.hostAddress
                        if ("127.0.0.1" != ip) {
                            return inetAddress.hostAddress ?: NO_DEFINED
                        }
                    }
                }
            }
            return NO_DEFINED
        } catch (e: SocketException) {
            LogK.et(TAG, "getDeviceIP SocketException ${e.message}")
            e.printStackTrace()
            return NO_DEFINED
        }
    }

    //设备Rom版本
    fun getRomVersion(): String = getSystemProperties(DEVICE_ROM_VERSION, NO_DEFINED)

    //设备硬件版本
    fun getHardwareVersion(): String = getSystemProperties(DEVICE_HW_VERSION, NO_DEFINED)

    //序列号
    /**
     * getSerial requires READ_PHONE_STATE or READ_PRIVILEGED_PHONE_STATE permission
     * @return String
     */
    @SuppressLint("HardwareIds")
    fun getSerialNumber(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        NO_DEFINED
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Build.SERIAL
    } else {
        getSystemProperties(DEVICE_SERIAL_NUMBER, NO_DEFINED)
    }

    //短序列号
    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14) {
            serial = serial.substring(14, serial.length)
        }
        return serial
    }

    //设备是否有sd卡
    fun isHasSdcard(): Boolean = TextUtils.equals(Environment.getExternalStorageState(), "mounted")

    //设备是否有USB外设
    fun isHasUSB(vid: Int, pid: Int): Boolean {
        @SuppressLint("WrongConstant")
        val mUsbManager: UsbManager = UtilKGlobal.instance.getApp()!!.getSystemService("usb") as UsbManager
        val devices: Iterator<UsbDevice> = mUsbManager.deviceList.values.iterator()
        while (devices.hasNext()) {
            val device: UsbDevice = devices.next()
            if (device.vendorId == vid && device.productId == pid) {
                return true
            }
        }
        return false
    }

    //设备是否有前置摄像
    fun isHasFrontCamera(): Boolean = isHasCamera(true)

    //设备是否有后置摄像头
    fun isHasBackCamera(): Boolean = isHasCamera(false)

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi", "SoonBlockedPrivateApi", "DiscouragedPrivateApi")
    fun closeAndroidPDialog() {
        try {
            val clazz = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = clazz.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true

            val clazz1 = Class.forName("android.app.ActivityThread")
            val declaredMethod = clazz1.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val hiddenApiWarningShown = clazz1.getDeclaredField("mHiddenApiWarningShown")
            hiddenApiWarningShown.isAccessible = true
            hiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            LogK.et(TAG, "closeAndroidPDialog Exception ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @SuppressLint("PrivateApi")
    fun setSystemProperties(key: String, value: String) {
        try {
            val clazz = Class.forName(DEVICE_SYSTEM_PROPERTIES)
            val setMethod: Method = clazz.getMethod("set", String::class.java, String::class.java)
            setMethod.invoke(clazz, key, value)
        } catch (e: Exception) {
            LogK.et(TAG, "setSystemProperties Exception ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @SuppressLint("PrivateApi")
    fun getSystemProperties(key: String?, defaultValue: String): String {
        return try {
            val clazz = Class.forName(DEVICE_SYSTEM_PROPERTIES)
            val getMethod: Method = clazz.getMethod("get", String::class.java)
            (getMethod.invoke(clazz, key) as String).ifEmpty { defaultValue }
        } catch (e: Exception) {
            LogK.et(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }
    }

    private fun isHasCamera(isFront: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return UtilKGlobal.instance.getApp()!!.packageManager.hasSystemFeature(if (isFront) PackageManager.FEATURE_CAMERA_FRONT else PackageManager.FEATURE_CAMERA)
        } else {
            val info = Camera.CameraInfo()
            for (i in 0 until Camera.getNumberOfCameras()) {
                Camera.getCameraInfo(i, info)
                if (info.facing == if (isFront) Camera.CameraInfo.CAMERA_FACING_FRONT else Camera.CameraInfo.CAMERA_FACING_BACK) {
                    return true
                }
            }
            return false
        }
    }
}