package com.mozhimen.basick.utilk.device

import android.annotation.SuppressLint
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.UtilKCmd
import com.mozhimen.basick.utilk.context.UtilKApplication

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*

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
    private val _context = UtilKApplication.instance.get()
    private const val PKG_ROM_VERSION = "ro.product.rom.version"
    private const val PKG_HW_VERSION = "ro.product.hw.version"
    private const val PKG_SERIAL_NUMBER = "ro.serialno"

    private const val PATH_MEMINFO = "/proc/meminfo"
    private const val PATH_CPU_USED = "/proc/stat"
    private const val NO_DEFINED = "unknown"

    /**
     * 设备内存空间
     * @return String
     */
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

    /**
     * cpu使用率
     * @return Float
     */
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

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
        UtilKCmd.getSystemProperties(PKG_ROM_VERSION, NO_DEFINED)

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        UtilKCmd.getSystemProperties(PKG_HW_VERSION, NO_DEFINED)

    /**
     * 序列号
     * @return String
     */
    @JvmStatic
    fun getSerialNumber(): String = if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
        NO_DEFINED
    } else if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
        Build.SERIAL
    } else {
        UtilKCmd.getSystemProperties(PKG_SERIAL_NUMBER, NO_DEFINED)
    }

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

    /**
     * 是否有外部存储
     * @return Boolean
     */
    @JvmStatic
    fun isHasExternalStorage(): Boolean = Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED

    /**
     * 本地存储可用大小
     * @return String?
     */
    @JvmStatic
    fun getFreeInternalMemorySize(): String? {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return Formatter.formatFileSize(_context, availableBlocks * blockSize)
    }

    /**
     * 获取手机内部空间大小
     * @return String
     */
    @JvmStatic
    fun getTotalInternalMemorySize(): String {
        val path = Environment.getDataDirectory() //Gets the Android data directory
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong //每个block 占字节数
        val totalBlocks = stat.availableBlocksLong //block总数
        return Formatter.formatFileSize(_context, totalBlocks * blockSize)
    }

    /**
     * 获取手机空闲空间大小
     * @return String
     */
    @JvmStatic
    fun getFreeExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            Formatter.formatFileSize(_context, availableBlocks * blockSize)
        } else "-1"
    }

    /**
     * 获取手机外部空间大小
     * @return String
     */
    @JvmStatic
    fun getTotalExternalMemorySize(): String {
        return if (isHasExternalStorage()) {
            val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            Formatter.formatFileSize(_context, totalBlocks * blockSize)
        } else "-1"
    }
}