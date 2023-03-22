package com.mozhimen.basick.utilk.content

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresFeature
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.app.UtilKAppInstall
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.pm.UtilKPackageManager

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:06
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
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun isOverlayPermissionEnable(context: Context): Boolean {
        return Build.VERSION.SDK_INT < CVersionCode.V_23_6_M || Settings.canDrawOverlays(context)
    }


    /**
     * 是否有文件管理权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @ADescription(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
    fun isExternalStoragePermissionEnable(): Boolean =
        Environment.isExternalStorageManager()

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun isAppInstallsPermissionEnable(context: Context): Boolean =
        UtilKPackageManager.canRequestPackageInstalls(context).also { Log.d(TAG, "isAppInstallsPermissionEnable: $it") }

    /**
     * 是否有无障碍权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isAccessibilityPermissionEnable(context: Context, serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val service = "${UtilKContext.getPackageName(context)}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = Settings.Secure.getInt(UtilKContext.getContentResolver(context), Settings.Secure.ACCESSIBILITY_ENABLED)
            Log.d(TAG, "isSettingAccessibilityPermissionEnable permissionEnable $permissionEnable")
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "isSettingAccessibilityPermissionEnable error finding setting, default accessibility to not found ${e.message}")
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            Log.d(TAG, "isSettingAccessibilityPermissionEnable accessibility is enabled")
            val settingValue = Settings.Secure.getString(UtilKContext.getContentResolver(context), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                stringColonSplitter.setString(settingValue)
                while (stringColonSplitter.hasNext()) {
                    val accessibilityService = stringColonSplitter.next()
                    Log.v(TAG, "isSettingAccessibilityPermissionEnable accessibilityService $accessibilityService - service $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.i(TAG, "isSettingAccessibilityPermissionEnable we've found the correct setting - accessibility is switched on!")
                        return true
                    }
                }
            } else {
            }
        } else {
            Log.e(TAG, "isSettingAccessibilityPermissionEnable accessibility is disabled")
        }
        return false
    }
}