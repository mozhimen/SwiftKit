package com.mozhimen.basick.utilk.android.os

import android.os.Process
import com.mozhimen.basick.utilk.bases.BaseUtilK


/**
 * @ClassName UtilKProcess
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/14 17:32
 * @Version 1.0
 */
object UtilKProcess : BaseUtilK() {
    @JvmStatic
    fun getMyPid(): Int =
        Process.myPid()

    @JvmStatic
    fun killProcess(intPid: Int) {
        Process.killProcess(intPid)
    }

    @JvmStatic
    fun killProcess_ofMyPid() {
        killProcess(getMyPid())
    }
}