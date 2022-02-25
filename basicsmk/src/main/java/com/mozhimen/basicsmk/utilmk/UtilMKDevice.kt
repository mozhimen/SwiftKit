package com.mozhimen.basicsmk.utilmk

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Camera
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Environment
import android.text.TextUtils
import java.lang.reflect.Method
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * @ClassName UtilMKDevice
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 19:40
 * @Version 1.0
 */
object UtilMKDevice {
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

    fun getRomVersion(): String {
        return getSystemProperties(KEY_ROM_VERSION, "")
    }

    fun getHwVersion(): String {
        return getSystemProperties(KEY_HW_VERSION, "")
    }

    fun getDeviceVersion(): String {
        return getSystemProperties(KEY_DISPLAY_ID, "")
    }

    fun getDeviceId(): String {
        return getSystemProperties(KEY_DEVICE_ID, "")
    }

    fun getDeviceName(): String {
        return getSystemProperties(KEY_DEVICE_NAME, "")
    }

    fun getDeviceModel(): String {
        return getSystemProperties(KEY_DEVICE_MODEL, "")
    }

    fun getDeviceBrand(): String {
        return getSystemProperties(KEY_DEVICE_BRAND, "")
    }

    fun getDeviceBoard(): String {
        return getSystemProperties(KEY_DEVICE_BOARD, "")
    }

    fun getManufacturer(): String {
        return getSystemProperties(KEY_DEVICE_MANUFACTURER, "")
    }

    fun getSerialNumber(): String {
        return getSystemProperties(KEY_SERIAL_NUMBER, "")
    }

    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14) {
            serial = serial.substring(14, serial.length)
        }
        return serial
    }

    fun isHasSdcard(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return TextUtils.equals(state, "mounted")
    }

    fun isHasUSBDevice(vid: Int, pid: Int): Boolean {
        var hasFingerPrintReader = false
        @SuppressLint("WrongConstant") val mUsbManager: UsbManager = UtilMKGlobal.instance.getApp()!!.getSystemService("usb") as UsbManager
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

    fun isHasFrontCamera(): Boolean {
        return hasCameras(1)
    }

    fun isHasBackCamera(): Boolean {
        return hasCameras(0)
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
}