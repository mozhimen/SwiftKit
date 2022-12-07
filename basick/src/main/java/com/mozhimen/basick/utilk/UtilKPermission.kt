package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings


/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKPermission {
    @JvmStatic
    fun openSettingFile(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//当系统在11及以上
            if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
                val intent = Intent()
                intent.apply {
                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    data = Uri.parse("package:${activity.packageName}")
                }
                activity.startActivity(intent)
            }
        }
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun openSetting(activity: Activity) {
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }
}