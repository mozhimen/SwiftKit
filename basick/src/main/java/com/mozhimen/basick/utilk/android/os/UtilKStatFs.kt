package com.mozhimen.basick.utilk.android.os

import android.os.StatFs
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
    fun get_ofExternalData(): StatFs =
        StatFs(UtilKStrPath.Absolute.External.getEnvData())

    @JvmStatic
    fun get_ofExternalStorage(): StatFs =
        StatFs(UtilKStrPath.Absolute.External.getEnvStorage())

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
}