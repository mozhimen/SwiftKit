package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.VersionCode
import com.mozhimen.basick.utilk.context.UtilKActivitySkip


/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKPermission {
    private const val TAG = "UtilKPermission>>>>>"

    /**
     * 是否有Overlay的权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isSettingOverlayPermissionEnable(context: Context): Boolean {
        return Build.VERSION.SDK_INT < VersionCode.V_23_6_M || Settings.canDrawOverlays(context)
    }

    /**
     * 设置申请权限 当系统在23及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresApi(VersionCode.V_23_6_M)
    fun openSettingOverlay(context: Context) {
        UtilKActivitySkip.start(
            context,
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
        )
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param activity Activity
     */
    @JvmStatic
    @RequiresApi(VersionCode.V_30_11_R)
    fun openSettingAll(activity: Activity) {
        if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
            UtilKActivitySkip.start(
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
    fun openSettingSelf(activity: Activity) {
        UtilKActivitySkip.start(
            activity,
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null))
        )
    }

    /**
     * 设置申请权限
     * @param activity Activity
     */
    @JvmStatic
    fun openSettingAccessibility(activity: Activity) {
        UtilKActivitySkip.start(
            activity,
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        )
    }

    /**
     * 设置申请权限
     * @param context Context
     */
    @JvmStatic
    fun openSettingAccessibility(context: Context) {
        UtilKActivitySkip.start(
            context,
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        )
    }

    /**
     * 是否有无障碍权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isSettingAccessibilityPermissionEnable(context: Context, serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val service = "${context.packageName}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = Settings.Secure.getInt(context.applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
            Log.d(TAG, "isSettingAccessibilityPermissionEnable permissionEnable $permissionEnable")
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "isSettingAccessibilityPermissionEnable error finding setting, default accessibility to not found ${e.message}")
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            Log.d(TAG, "isSettingAccessibilityPermissionEnable accessibility is enabled")
            val settingValue = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                stringColonSplitter.setString(settingValue)
                while (stringColonSplitter.hasNext()) {
                    val accessibilityService = stringColonSplitter.next()
                    Log.v(TAG, "isSettingAccessibilityPermissionEnable accessibilityService $accessibilityService - service $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.w(TAG, "isSettingAccessibilityPermissionEnable we've found the correct setting - accessibility is switched on!")
                        return true
                    }
                }
            }
        } else {
            Log.e(TAG, "isSettingAccessibilityPermissionEnable accessibility is disabled")
        }
        return false
    }
}