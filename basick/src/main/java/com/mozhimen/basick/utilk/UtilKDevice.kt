package com.mozhimen.basick.utilk

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.context.UtilKApplication

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.RandomAccessFile
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
@APermissionK([
    Manifest.permission.KILL_BACKGROUND_PROCESSES,
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.CAMERA
])
object UtilKDevice {
    private const val TAG = "UtilKDevice>>>>>"
    private val _context = UtilKApplication.instance.get()
    private const val PKG_ROM_VERSION = "ro.product.rom.version"
    private const val PKG_HW_VERSION = "ro.product.hw.version"
    private const val PKG_SERIAL_NUMBER = "ro.serialno"

    private const val PATH_MEMINFO = "/proc/meminfo"
    private const val PATH_CPU_USED = "/proc/stat"
    private const val NO_DEFINED = "unknown"

    //设备内存空间
    @JvmStatic
    fun getDeviceMemory(): String {
        val readerStr: String
        val arrayOfString: Array<String>
        var memorySize: Long = 0
        try {
            val localFileReader = FileReader(PATH_MEMINFO)
            val localBufferedReader = BufferedReader(
                localFileReader, 8192
            )
            readerStr = localBufferedReader.readLine() // 读取mem info第一行，系统总内存大小
            arrayOfString = readerStr.split("\\s+".toRegex()).toTypedArray()
            for (num in arrayOfString) {
                Log.e(readerStr, num + "\t")
            }
            memorySize = (Integer.valueOf(arrayOfString[1]).toInt() * 1024).toLong() // 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Formatter.formatFileSize(_context, memorySize) // Byte转换为KB或者MB，内存大小规格化
    }

    //cpu使用率
    @JvmStatic
    fun getDeviceCpuUsed(): Float {
        try {
            val reader = RandomAccessFile(PATH_CPU_USED, "r")
            var load = reader.readLine()
            var toks = load.split(" ".toRegex()).toTypedArray()
            val idle1 = toks[5].toLong()
            val cpu1 =
                toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            try {
                Thread.sleep(360)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            reader.seek(0)
            load = reader.readLine()
            reader.close()
            toks = load.split(" ".toRegex()).toTypedArray()
            val idle2 = toks[5].toLong()
            val cpu2 =
                toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            return (cpu2 - cpu1).toFloat() / (cpu2 + idle2 - (cpu1 + idle1))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return 0f
    }

    //设备IP
    @JvmStatic
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
            Log.e(TAG, "getDeviceIP SocketException ${e.message}")
            e.printStackTrace()
            return NO_DEFINED
        }
    }

    //设备Rom版本
    @JvmStatic
    fun getRomVersion(): String =
        UtilKCmd.getSystemProperties(PKG_ROM_VERSION, NO_DEFINED)

    //设备硬件版本
    @JvmStatic
    fun getHardwareVersion(): String =
        UtilKCmd.getSystemProperties(PKG_HW_VERSION, NO_DEFINED)

    //序列号
    /**
     * getSerial requires READ_PHONE_STATE or READ_PRIVILEGED_PHONE_STATE permission
     * @return String
     */
    @JvmStatic
    @SuppressLint("HardwareIds")
    fun getSerialNumber(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        NO_DEFINED
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Build.SERIAL
    } else {
        UtilKCmd.getSystemProperties(PKG_SERIAL_NUMBER, NO_DEFINED)
    }

    //短序列号
    @JvmStatic
    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14) {
            serial = serial.substring(14, serial.length)
        }
        return serial
    }

    //设备是否有sd卡
    @JvmStatic
    fun isHasSdcard(): Boolean =
        TextUtils.equals(Environment.getExternalStorageState(), "mounted")

    //设备是否有USB外设
    @JvmStatic
    fun isHasPid(vid: Int, pid: Int): Boolean {
        @SuppressLint("WrongConstant")
        val mUsbManager: UsbManager = _context.getSystemService("usb") as UsbManager
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
    @JvmStatic
    fun isHasFrontCamera(): Boolean =
        isHasCamera(true)

    //设备是否有后置摄像头
    @JvmStatic
    fun isHasBackCamera(): Boolean =
        isHasCamera(false)

    //是否有外部存储
    @JvmStatic
    fun isHasExternalStorage(): Boolean = Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED

    //本地存储可用大小
    @JvmStatic
    fun getFreeInternalMemorySize(): String? {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return Formatter.formatFileSize(_context, availableBlocks * blockSize)
    }

    //获取手机内部空间大小
    @JvmStatic
    fun getTotalInternalMemorySize(): String {
        val path = Environment.getDataDirectory() //Gets the Android data directory
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong //每个block 占字节数
        val totalBlocks = stat.availableBlocksLong //block总数
        return Formatter.formatFileSize(_context, totalBlocks * blockSize)
    }

    @JvmStatic
    fun getFreeExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            Formatter.formatFileSize(_context, availableBlocks * blockSize)
        } else "-1"
    }

    @JvmStatic
    fun getTotalExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            Formatter.formatFileSize(_context, totalBlocks * blockSize)
        } else "-1"
    }

    /**
     * 关闭Android9.0弹出框（Detected problems with API compatibility）
     */
    @JvmStatic
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
            Log.e(TAG, "closeAndroidPDialog Exception ${e.message}")
            e.printStackTrace()
        }
    }

    @JvmStatic
    private fun isHasCamera(isFront: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return _context.packageManager.hasSystemFeature(if (isFront) PackageManager.FEATURE_CAMERA_FRONT else PackageManager.FEATURE_CAMERA)
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