package com.mozhimen.basick.utilk.android.os

import android.os.StatFs
import com.mozhimen.basick.utilk.android.text.formatFileSize
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath

/**
 * @ClassName UtilKStatFs
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/6 23:59
 * @Version 1.0
 */
object UtilKStatFs : BaseUtilK() {

    @JvmStatic
    fun getStatFsExternalData(): StatFs =
        StatFs(UtilKStrPath.Absolute.External.getEnvDataDir())

    @JvmStatic
    fun getStatFsExternalStorage(): StatFs =
        StatFs(UtilKStrPath.Absolute.External.getEnvStorageDir())

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getBlockSizeLong(statFs: StatFs): Long =
        statFs.blockSizeLong//每个block 占字节数

    @JvmStatic
    fun getAvailableBlocks(statFs: StatFs): Int =
        statFs.availableBlocks//block总数

    @JvmStatic
    fun getAvailableBlocksLong(statFs: StatFs): Long =
        statFs.availableBlocksLong//block总数

    @JvmStatic
    fun getFreeBlocksLong(statFs: StatFs): Long =
        statFs.freeBlocksLong//block总数

    @JvmStatic
    fun getBlockCountLong(statFs: StatFs): Long =
        statFs.blockCountLong

    /////////////////////////////////////////////////////////////////////

    /**
     * 本地存储可用大小
     */
    @JvmStatic
    fun getFreeExternalDataMemorySize(): Long {
        val statFs = getStatFsExternalData()
        return getFreeBlocksLong(statFs) * getBlockSizeLong(statFs)
    }


    @JvmStatic
    fun getFreeExternalDataMemorySizeStr(): String =
        getFreeExternalDataMemorySize().formatFileSize()

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getAvailableExternalDataMemorySize(): Long {
        val statFs = getStatFsExternalData()//Gets the Android data directory
        return getAvailableBlocksLong(statFs) * getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getAvailableExternalDataMemorySizeStr(): String =
        getAvailableExternalDataMemorySize().formatFileSize()

    /**
     *
     */
    @JvmStatic
    fun getTotalExternalDataMemorySize(): Long {
        val statFs = getStatFsExternalData()//Gets the Android data directory
        return (getBlockCountLong(statFs) * getBlockSizeLong(statFs))
    }

    @JvmStatic
    fun getTotalExternalDataMemorySizeStr(): String =
        getTotalExternalDataMemorySize().formatFileSize()

    /////////////////////////////////////////////////////////////////////

    /**
     *
     */
    @JvmStatic
    fun getFreeExternalStorageMemorySize(): Long {
        val statFs = getStatFsExternalStorage()
        return (getFreeBlocksLong(statFs) * getBlockSizeLong(statFs))
    }

    @JvmStatic
    fun getFreeExternalStorageMemorySizeStr(): String =
        getFreeExternalStorageMemorySize().formatFileSize()

    /**
     *
     */
    @JvmStatic
    fun getAvailableExternalStorageMemorySize(): Long {
        val statFs = getStatFsExternalStorage()
        return getAvailableBlocksLong(statFs) * getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getAvailableExternalStorageMemorySizeStr(): String =
        getAvailableExternalStorageMemorySize().formatFileSize()

    /**
     *
     */
    @JvmStatic
    fun getTotalExternalStorageMemorySize(): Long {
        val statFs = getStatFsExternalStorage()
        return getBlockCountLong(statFs) * getBlockSizeLong(statFs)
    }

    @JvmStatic
    fun getTotalExternalStorageMemorySizeStr(): String =
        getTotalExternalStorageMemorySize().formatFileSize()

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isEnoughOfFileSize(fileSize: Long): Boolean {
        return getAvailableExternalStorageMemorySize() >= fileSize
    }
}