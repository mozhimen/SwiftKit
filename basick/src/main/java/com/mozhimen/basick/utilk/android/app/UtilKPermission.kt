package com.mozhimen.basick.utilk.android.app

import android.annotation.TargetApi
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
import com.mozhimen.basick.utilk.android.os.isBeforeVersion

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:06
 * @Version 1.0
 */
object UtilKPermission : BaseUtilK() {

    @JvmStatic
    fun hasOverlay(): Boolean =
        if (UtilKBuildVers.isAfterV_23_6_M()) {
            hasOverlay2()
        } else true

    /**
     * 是否有Overlay的权限
     * @return Boolean
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun hasOverlay2(): Boolean {
        return CVersCode.V_23_6_M.isBeforeVersion() || Settings.canDrawOverlays(_context)
    }


    /**
     * 是否有文件管理权限
     * @return Boolean
     */
    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @ADescription(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
    fun hasExternalStorage(): Boolean =
        Environment.isExternalStorageManager()

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun hasPackageInstalls(): Boolean =
        if (UtilKBuildVers.isAfterV_26_8_O()) {
            hasPackageInstallsAfterO()
        } else true

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @TargetApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun hasPackageInstallsAfterO(): Boolean =
        UtilKPackageManager.canRequestPackageInstalls(_context).also { Log.d(TAG, "isAppInstallsPermissionEnable: $it") }

    /**
     * 是否有无障碍权限
     * @return Boolean
     */
    @JvmStatic
    fun hasAccessibility(serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val service = "${UtilKPackage.getPackageName()}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = Settings.Secure.getInt(UtilKContext.getContentResolver(_context), Settings.Secure.ACCESSIBILITY_ENABLED)
            Log.d(TAG, "isSettingAccessibilityPermissionEnable permissionEnable $permissionEnable")
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "isSettingAccessibilityPermissionEnable error finding setting, default accessibility to not found ${e.message}")
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            Log.d(TAG, "isSettingAccessibilityPermissionEnable accessibility is enabled")
            val settingValue = Settings.Secure.getString(UtilKContext.getContentResolver(_context), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
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
            }
        } else {
            Log.e(TAG, "isSettingAccessibilityPermissionEnable accessibility is disabled")
        }
        return false
    }
}