package com.mozhimen.basick.utilk

import android.annotation.SuppressLint
import android.hardware.Camera
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Environment
import android.text.TextUtils
import android.util.Log
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
    private val TAG = "UtilKDevice>>>>>"
    private const val KEY_SYSTEM_PROPERTIES = "android.os.SystemProperties"
    private const val KEY_ROM_VERSION = "ro.product.rom.version"
    private const val KEY_HW_VERSION = "ro.product.hw.version"
    private const val KEY_DISPLAY_ID = "ro.build.display.id"
    private const val KEY_DEVICE_ID = "ro.product.id"
    private const val KEY_DEVICE_NAME = "ro.product.name"
    private const val KEY_DEVICE_MODEL = "ro.product.model"
    private const val KEY_DEVICE_BRAND = "ro.product.brand"
    private const val KEY_DEVICE_BOARD = "ro.product.board"
    private const val KEY_DEVICE_MANUFACTURER = "ro.product.manufacturer"
    private const val KEY_SERIAL_NUMBER = "ro.serialno"

    /**
     * 获取设备IP
     * @return String?
     */
    fun getDeviceIP(): String? {
        var hostIP: String? = null
        try {
            val nis: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            var ia: InetAddress
            while (true) {
                while (nis.hasMoreElements()) {
                    val ni: NetworkInterface = nis.nextElement() as NetworkInterface
                    val ias: Enumeration<InetAddress> = ni.inetAddresses
                    while (ias.hasMoreElements()) {
                        ia = ias.nextElement() as InetAddress
                        if (ia !is Inet6Address) {
                            val ip: String = ia.hostAddress
                            if ("127.0.0.1" != ip) {
                                hostIP = ia.hostAddress
                                break
                            }
                        }
                    }
                }
                return hostIP
            }
        } catch (var6: SocketException) {
            var6.printStackTrace()
            return hostIP
        }
    }

    /**
     * 获取Rom型号
     * @return String
     */
    fun getRomVersion(): String {
        return getSystemProperties(KEY_ROM_VERSION, "")
    }

    /**
     * 获取HW
     * @return String
     */
    fun getHwVersion(): String {
        return getSystemProperties(KEY_HW_VERSION, "")
    }

    /**
     * 获取设备版本
     * @return String
     */
    fun getDeviceVersion(): String {
        return getSystemProperties(KEY_DISPLAY_ID, "")
    }

    /**
     * 获取设备ID
     * @return String
     */
    fun getDeviceId(): String {
        return getSystemProperties(KEY_DEVICE_ID, "")
    }

    /**
     * 获取设备名称
     * @return String
     */
    fun getDeviceName(): String {
        return getSystemProperties(KEY_DEVICE_NAME, "")
    }

    /**
     * 获取设备
     * @return String
     */
    fun getDeviceModel(): String {
        return getSystemProperties(KEY_DEVICE_MODEL, "")
    }

    /**
     * 获取设备公司
     * @return String
     */
    fun getDeviceBrand(): String {
        return getSystemProperties(KEY_DEVICE_BRAND, "")
    }

    /**
     * 获取设备证书
     * @return String
     */
    fun getDeviceBoard(): String {
        return getSystemProperties(KEY_DEVICE_BOARD, "")
    }

    /**
     * 获取设备厂商
     * @return String
     */
    fun getManufacturer(): String {
        return getSystemProperties(KEY_DEVICE_MANUFACTURER, "")
    }

    /**
     * 获取设备序列号
     * @return String
     */
    fun getSerialNumber(): String {
        return getSystemProperties(KEY_SERIAL_NUMBER, "")
    }

    /**
     * 获取设备短序列号
     * @return String
     */
    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14) {
            serial = serial.substring(14, serial.length)
        }
        return serial
    }

    /**
     * 是否有sd卡
     * @return Boolean
     */
    fun isHasSdcard(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return TextUtils.equals(state, "mounted")
    }

    /**
     * 是否有USB设备连接
     * @param vid Int
     * @param pid Int
     * @return Boolean
     */
    fun isHasUSBDevice(vid: Int, pid: Int): Boolean {
        var hasFingerPrintReader = false
        @SuppressLint("WrongConstant") val mUsbManager: UsbManager = UtilKGlobal.instance.getApp()!!.getSystemService("usb") as UsbManager
        val var5: Iterator<UsbDevice> = mUsbManager.deviceList.values.iterator()
        while (var5.hasNext()) {
            val device: UsbDevice = var5.next()
            if (device.vendorId == vid && device.productId == pid) {
                hasFingerPrintReader = true
                break
            }
        }
        return hasFingerPrintReader
    }

    /**
     * 是否有前置摄像头
     * @return Boolean
     */
    fun isHasFrontCamera(): Boolean {
        return hasCameras(1)
    }

    /**
     * 是否有后置摄像头
     * @return Boolean
     */
    fun isHasBackCamera(): Boolean {
        return hasCameras(0)
    }

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @SuppressLint("PrivateApi", "SoonBlockedPrivateApi", "DiscouragedPrivateApi")
    fun closeAndroidPDialog() {
        try {
            val cls = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = cls.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
        }
    }

    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @SuppressLint("PrivateApi")
    fun setSystemProperties(key: String?, value: String?) {
        try {
            val c = Class.forName(KEY_SYSTEM_PROPERTIES)
            val set: Method = c.getMethod("set", String::class.java, String::class.java)
            set.invoke(c, key, value)
        } catch (var4: Exception) {
            var4.printStackTrace()
        }
    }

    /**
     * 获取首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @SuppressLint("PrivateApi")
    fun getSystemProperties(key: String?, defaultValue: String): String {
        return try {
            val c = Class.forName(KEY_SYSTEM_PROPERTIES)
            val get: Method = c.getMethod("get", String::class.java)
            get.invoke(c, key) as String
        } catch (var4: Exception) {
            var4.printStackTrace()
            defaultValue
        }
    }

    private fun hasCameras(cameraId: Int): Boolean {
        var hasCamera = false
        val info = Camera.CameraInfo()
        for (i in 0 until Camera.getNumberOfCameras()) {
            Camera.getCameraInfo(i, info)
            if (info.facing == cameraId) {
                hasCamera = true
                break
            }
        }
        return hasCamera
    }
}