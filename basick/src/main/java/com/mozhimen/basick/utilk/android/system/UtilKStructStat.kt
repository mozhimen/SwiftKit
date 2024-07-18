package com.mozhimen.basick.utilk.android.system

import android.system.StructStat
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKStructStat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/18
 * @Version 1.0
 */
object UtilKStructStat {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get_ofLstat(strPathName: String): StructStat =
        UtilKOs.lstat(strPathName)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getSt_atime(strPathName: String): Long =
        get_ofLstat(strPathName).st_atime
}