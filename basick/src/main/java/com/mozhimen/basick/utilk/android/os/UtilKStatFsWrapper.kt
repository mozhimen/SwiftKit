package com.mozhimen.basick.utilk.android.os

import com.mozhimen.basick.utilk.android.text.formatFileSize

/**
 * @ClassName UtilKStatsFsWrapper
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/18
 * @Version 1.0
 */
object UtilKStatFsWrapper {
    /**
     * 本地存储可用大小
     */
    @JvmStatic
    fun getExternalDataMemorySize_ofFree(): Long {
        val statFs = UtilKStatFs.get_ofExternalData()
        return UtilKStatFs.getFreeBlocksLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getExternalDataMemorySize_ofAvailable(): Long {
        val statFs = UtilKStatFs.get_ofExternalData()//Gets the Android data directory
        return UtilKStatFs.getAvailableBlocksLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }


    @JvmStatic
    fun getExternalDataMemorySize_ofTotal(): Long {
        val statFs = UtilKStatFs.get_ofExternalData()//Gets the Android data directory
        return UtilKStatFs.getBlockCountLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getExternalStorageMemorySize_ofFree(): Long {
        val statFs = UtilKStatFs.get_ofExternalStorage()
        return UtilKStatFs.getFreeBlocksLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getExternalStorageMemorySize_ofAvailable(): Long {
        val statFs = UtilKStatFs.get_ofExternalStorage()
        return UtilKStatFs.getAvailableBlocksLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getExternalStorageMemorySize_ofTotal(): Long {
        val statFs = UtilKStatFs.get_ofExternalStorage()
        return UtilKStatFs.getBlockCountLong(statFs) * UtilKStatFs.getBlockSizeLong(statFs)
    }

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrExternalDataMemorySize_ofFree(): String =
        getExternalDataMemorySize_ofFree().formatFileSize()

    @JvmStatic
    fun getStrExternalDataMemorySize_ofAvailable(): String =
        getExternalDataMemorySize_ofAvailable().formatFileSize()

    @JvmStatic
    fun getStrExternalDataMemorySize_ofTotal(): String =
        getExternalDataMemorySize_ofTotal().formatFileSize()

    @JvmStatic
    fun getStrExternalStorageMemorySize_ofFree(): String =
        getExternalStorageMemorySize_ofFree().formatFileSize()

    @JvmStatic
    fun getStrExternalStorageMemorySize_ofAvailable(): String =
        getExternalStorageMemorySize_ofAvailable().formatFileSize()

    @JvmStatic
    fun getStrExternalStorageMemorySize_ofTotal(): String =
        getExternalStorageMemorySize_ofTotal().formatFileSize()

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isEnough_ofFileSize(fileSize: Long): Boolean {
        return getExternalStorageMemorySize_ofAvailable() >= fileSize
    }
}