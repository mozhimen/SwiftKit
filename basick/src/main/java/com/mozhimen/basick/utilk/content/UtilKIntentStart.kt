package com.mozhimen.basick.utilk.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode


/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKIntentStart {
    private const val TAG = "UtilKPermission>>>>>"

    /**
     * 设置申请权限 当系统在23及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_23_6_M)
    fun startSettingOverlay(context: Context) {
        UtilKActivityStart.start(
            context, Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
        )
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param activity Activity
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_30_11_R)
    fun startSettingAll(activity: Activity) {
        if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            UtilKActivityStart.start(
                activity,
                Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:${activity.packageName}"))
            )
        }
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun startSettingSelf(activity: Activity) {
        UtilKActivityStart.start(
            activity, Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null))
        )
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun startSettingAccessibility(activity: Activity) {
        UtilKActivityStart.start(
            activity, Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        )
    }

    /**
     * 设置申请权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        UtilKActivityStart.start(
            context, Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        )
    }
}