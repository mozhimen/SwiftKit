package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.mozhimen.basick.utilk.context.UtilKActivitySkip
import com.mozhimen.basick.utilk.context.UtilKApplication


/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKPermission {
    /**
     * 设置申请权限 当系统在11及以上
     * @param activity Activity
     */
    @JvmStatic
    @RequiresApi(30)
    fun openSettingAll(activity: Activity) {
        if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            val intent = Intent()
            intent.apply {
                action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                data = Uri.parse("package:${activity.packageName}")
            }
            UtilKActivitySkip.start(activity, intent)
        }
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun openSettingSelf(activity: Activity) {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity.packageName, null)
        }
        UtilKActivitySkip.start(activity, intent)
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun openSettingAccessibility(activity: Activity) {
        UtilKActivitySkip.start(activity, Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}