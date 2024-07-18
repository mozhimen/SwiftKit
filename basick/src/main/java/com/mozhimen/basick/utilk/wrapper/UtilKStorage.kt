package com.mozhimen.basick.utilk.wrapper

import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKStatFsWrapper
import com.mozhimen.basick.utilk.java.io.getLastAccessTime
import com.mozhimen.basick.utilk.wrapper.UtilKDevice.hasExternalStorage
import java.io.File

/**
 * @ClassName UtilKStorage
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/18
 * @Version 1.0
 */
object UtilKStorage {

    @JvmStatic
    fun getFolderSize(files: List<File>): Long =
        files.sumOf { it.length() }

    /////////////////////////////////////////////////////////////////////

    /**
     * 本地存储可用大小
     */
    @JvmStatic
    fun getStrInternalMemorySize_ofFree(): String =
        UtilKStatFsWrapper.getStrExternalDataMemorySize_ofFree()

    @JvmStatic
    fun getStrInternalMemorySize_ofAvailable(): String =
        UtilKStatFsWrapper.getStrExternalDataMemorySize_ofAvailable()

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getStrInternalMemorySize_ofTotal(): String =
        UtilKStatFsWrapper.getStrExternalDataMemorySize_ofTotal()

    /**
     * 获取手机空闲空间大小
     */
    @JvmStatic
    fun getStrExternalMemorySize_ofFree(): String =
        if (hasExternalStorage()) {
            UtilKStatFsWrapper.getStrExternalStorageMemorySize_ofFree()
        } else "0"

    @JvmStatic
    fun getStrExternalMemorySize_ofAvailable(): String =
        if (hasExternalStorage()) {
            UtilKStatFsWrapper.getStrExternalStorageMemorySize_ofAvailable()
        } else "0"

    /**
     * 获取手机外部空间大小
     */
    @JvmStatic
    fun getStrExternalMemorySize_ofTotal(): String =
        if (hasExternalStorage()) {
            UtilKStatFsWrapper.getStrExternalStorageMemorySize_ofTotal()
        } else "0"

    /////////////////////////////////////////////////////////////////////
    /**
     * 本地存储可用大小
     */
    @JvmStatic
    fun getInternalMemorySize_ofFree(): Long =
        UtilKStatFsWrapper.getExternalDataMemorySize_ofFree()

    @JvmStatic
    fun getInternalMemorySize_ofAvailable(): Long =
        UtilKStatFsWrapper.getExternalDataMemorySize_ofAvailable()

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getInternalMemorySize_ofTotal(): Long =
        UtilKStatFsWrapper.getExternalDataMemorySize_ofTotal()

    /**
     * 获取手机空闲空间大小
     */
    @JvmStatic
    fun getExternalMemorySize_ofFree(): Long =
        if (hasExternalStorage())
            UtilKStatFsWrapper.getExternalStorageMemorySize_ofFree()
        else 0L

    @JvmStatic
    fun getExternalMemorySize_ofAvailable(): Long =
        if (hasExternalStorage())
            UtilKStatFsWrapper.getExternalStorageMemorySize_ofAvailable()
        else 0L

    /**
     * 获取手机外部空间大小
     */
    @JvmStatic
    fun getExternalMemorySize_ofTotal(): Long =
        if (hasExternalStorage())
            UtilKStatFsWrapper.getExternalStorageMemorySize_ofTotal()
        else 0L
}