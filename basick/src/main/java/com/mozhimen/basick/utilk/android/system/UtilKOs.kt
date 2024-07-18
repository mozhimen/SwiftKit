package com.mozhimen.basick.utilk.android.system

import android.system.Os
import android.system.StructStat
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKOs
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/18
 * @Version 1.0
 */
object UtilKOs {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun lstat(path: String): StructStat =
        Os.lstat(path)
}