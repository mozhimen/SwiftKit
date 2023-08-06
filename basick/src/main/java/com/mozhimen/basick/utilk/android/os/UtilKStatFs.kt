package com.mozhimen.basick.utilk.android.os

import android.os.StatFs
import com.mozhimen.basick.utilk.android.text.getFormatFileSize
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
        StatFs(UtilKStrPath.Absolute.External.getDataDir())

    @JvmStatic
    fun getStatFsExternalStorage(): StatFs =
        StatFs(UtilKStrPath.Absolute.External.getStorageDir())

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getBlockSizeLong(statFs: StatFs): Long =
        statFs.blockSizeLong//每个block 占字节数

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
    fun getFreeExternalDataMemorySize(): String {
        val statFs = getStatFsExternalData()
        return (getFreeBlocksLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }

    /**
     * 获取手机内部空间大小
     */
    @JvmStatic
    fun getAvailableExternalDataMemorySize(): String {
        val statFs = getStatFsExternalData()//Gets the Android data directory
        return (getAvailableBlocksLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }

    @JvmStatic
    fun getTotalExternalDataMemorySize(): String {
        val statFs = getStatFsExternalData()//Gets the Android data directory
        return (getBlockCountLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }

    @JvmStatic
    fun getFreeExternalStorageMemorySize(): String {
        val statFs = getStatFsExternalStorage()
        return (getFreeBlocksLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }

    @JvmStatic
    fun getAvailableExternalStorageMemorySize(): String {
        val statFs = getStatFsExternalStorage()
        return (getAvailableBlocksLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }

    @JvmStatic
    fun getTotalExternalStorageMemorySize(): String {
        val statFs = getStatFsExternalStorage()
        return (getBlockCountLong(statFs) * getBlockSizeLong(statFs)).getFormatFileSize()
    }
}