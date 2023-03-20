package com.mozhimen.basick.utilk.app

import android.content.Context
import android.content.pm.ApplicationInfo
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 23:07
 * @Version 1.0
 */
object UtilKApplicationInfo {
    @JvmStatic
    fun get(context: Context): ApplicationInfo =
        UtilKContext.getApplicationInfo(context)

    @JvmStatic
    fun getTargetSdkVersion(context: Context): Int =
        get(context).targetSdkVersion
}