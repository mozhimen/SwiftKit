package com.mozhimen.basick.utilk.android.app

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKIntentWrapper
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.kotlin.UtilKString

/**
 * @ClassName UtilKActivityInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 23:21
 * @Version 1.0
 */
object UtilKActivityInfo {

    //获取启动Activity
    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getMainLauncher(context: Context, strPackageName: String): ActivityInfo? {
        if (UtilKString.hasSpace(strPackageName) || strPackageName.isEmpty()) return null
        val resolveInfos = UtilKPackageManager.queryIntentActivities(context, UtilKIntentWrapper.getMainLauncher_ofPackage(strPackageName, null), 0)
        return if (resolveInfos.isEmpty()) null else resolveInfos[0].activityInfo
    }

    /////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getMainLauncherName(context: Context, strPackageName: String): String =
        getMainLauncher(context, strPackageName)?.name ?: ""
}