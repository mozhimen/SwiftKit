package com.mozhimen.basick.utilk.device

import android.hardware.usb.UsbDevice
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.device.camera.UtilKCamera
import com.mozhimen.basick.utilk.log.et
import com.mozhimen.basick.utilk.os.UtilKEnvironment
import com.mozhimen.basick.utilk.os.UtilKPath
import com.mozhimen.basick.utilk.os.UtilKSystemProperties
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.RandomAccessFile

/**
 * @ClassName UtilKDevice
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 19:40
 * @Version 1.0
 */
@AManifestKRequire(CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE)
object UtilKDevice {
    private const val TAG = "UtilKDevice>>>>>"
    private val _context by lazy { UtilKApplication.instance.get() }

    private const val PATH_MEMINFO = "/proc/meminfo"
    private const val PATH_CPU_USED = "/proc/stat"

    @JvmStatic
    fun isHasFrontCamera(): Boolean =
        UtilKCamera.isHasFrontCamera(_context)

    @JvmStatic
    fun isHasBackCamera(): Boolean =
        UtilKCamera.isHasBackCamera(_context)

    /**
     * 设备内存空间
     * @return String
     */
    @JvmStatic
    fun getMemorySize(): String {
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
            e.message?.et(TAG)
        }
        return Formatter.formatFileSize(_context, memorySize) // Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * cpu使用率
     * @return Float
     */
    @JvmStatic
    fun getCpuUsed(): Float {
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

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
        UtilKSystemProperties.getRomVersion()

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        UtilKSystemProperties.getHardwareVersion()

    /**
     * 序列号
     * @return String
     */
    @JvmStatic
    fun getSerialNumber(): String =
        UtilKSystemProperties.getSerialNumber()

    /**
     * 短序列号
     * @return String
     */
    @JvmStatic
    fun getSerialNumberShort(): String {
        var serial = getSerialNumber()
        if (!TextUtils.isEmpty(serial) && serial.length > 14) {
            serial = serial.substring(14, serial.length)
        }
        return serial
    }

    /**
     * 设备是否有sd卡
     * @return Boolean
     */
    @JvmStatic
    fun isHasSdcard(): Boolean =
        TextUtils.equals(Environment.getExternalStorageState(), "mounted")

    /**
     * 设备是否有USB外设
     * @param vid Int
     * @param pid Int
     * @return Boolean
     */
    @JvmStatic
    fun isHasPid(vid: Int, pid: Int): Boolean {
        val devices: Iterator<UsbDevice> = UtilKUsbManager.get(_context).deviceList.values.iterator()
        while (devices.hasNext()) {
            val usbDevice: UsbDevice = devices.next()
            if (usbDevice.vendorId == vid && usbDevice.productId == pid) return true
        }
        return false
    }

    /**
     * 是否有外部存储
     * @return Boolean
     */
    @JvmStatic
    fun isHasExternalStorage(): Boolean =
        UtilKEnvironment.isExternalStorageMounted()

    /**
     * 本地存储可用大小
     * @return String?
     */
    @JvmStatic
    fun getFreeInternalMemorySize(): String? {
        val statFs = StatFs(UtilKPath.Absolute.External.getDataDir())
        val blockSize = statFs.blockSizeLong
        val availableBlocks = statFs.availableBlocksLong
        return Formatter.formatFileSize(_context, availableBlocks * blockSize)
    }

    /**
     * 获取手机内部空间大小
     * @return String
     */
    @JvmStatic
    fun getTotalInternalMemorySize(): String {
        val statFs = StatFs(UtilKPath.Absolute.External.getDataDir())//Gets the Android data directory
        val blockSize = statFs.blockSizeLong //每个block 占字节数
        val totalBlocks = statFs.availableBlocksLong //block总数
        return Formatter.formatFileSize(_context, totalBlocks * blockSize)
    }

    /**
     * 获取手机空闲空间大小
     * @return String
     */
    @JvmStatic
    fun getFreeExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val statFs = StatFs(UtilKPath.Absolute.External.getStorageDir())
            val blockSize = statFs.blockSizeLong
            val availableBlocks = statFs.availableBlocksLong
            Formatter.formatFileSize(_context, availableBlocks * blockSize)
        } else "0"
    }

    /**
     * 获取手机外部空间大小
     * @return String
     */
    @JvmStatic
    fun getTotalExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val statFs = StatFs(UtilKPath.Absolute.External.getStorageDir())
            val blockSize = statFs.blockSizeLong
            val totalBlocks = statFs.blockCountLong
            Formatter.formatFileSize(_context, totalBlocks * blockSize)
        } else "0"
    }
}