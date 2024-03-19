package com.mozhimen.basick.utilk.android

import android.provider.Settings
import android.text.TextUtils
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKEnvironment
import com.mozhimen.basick.utilk.android.provider.UtilKSettings
import com.mozhimen.basick.utilk.android.provider.UtilKSettingsSecure
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.android.util.i
import com.mozhimen.basick.utilk.android.util.v

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:06
 * @Version 1.0
 */
object UtilKPermission : BaseUtilK() {

    @JvmStatic
    fun isSelfGranted(permissions: Array<String>): Boolean =
        isSelfGranted(permissions.toList())

    @JvmStatic
    fun isSelfGranted(permissions: List<String>): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) true
        else {
            for (permission in permissions)
                allGranted = allGranted and isSelfGranted(permission)
            allGranted
        }
    }

    @JvmStatic
    fun isSelfGranted(permission: String): Boolean =
        UtilKContextCompat.isSelfPermissionGranted(_context, permission).also { UtilKLogWrapper.d(TAG, "hasPermission: permission $permission is $it") }

    /////////////////////////////////////////////////////////////////////////

    @OPermission_READ_EXTERNAL_STORAGE
    @OPermission_MANAGE_EXTERNAL_STORAGE
    @RequiresPermission(allOf = [CPermission.MANAGE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    @JvmStatic
    fun hasManageExternalStorage(): Boolean =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKEnvironment.isExternalStorageManager()
        else
            isSelfGranted(arrayOf(CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE))

    //是否有文件管理权限
    @JvmStatic
    @OPermission_READ_EXTERNAL_STORAGE
    @OPermission_MANAGE_EXTERNAL_STORAGE
    @RequiresPermission(allOf = [CPermission.MANAGE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    fun hasReadExternalStorage(): Boolean =
        if (UtilKBuildVersion.isAfterV_30_11_R()) UtilKEnvironment.isExternalStorageManager() else true

    //是否有Overlay的权限
    @JvmStatic
    @OPermission_SYSTEM_ALERT_WINDOW
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun hasSystemAlertWindow(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            UtilKSettings.canDrawOverlays(_context)
        } else true

    //是否有包安装权限
    @JvmStatic
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun hasRequestInstallPackages(): Boolean =
        UtilKPackageManager.canRequestPackageInstalls(_context)

    /**
     * 是否有无障碍权限
     */
    @JvmStatic
    fun hasAccessibility(serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val strService = "${UtilKPackage.getPackageName()}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = UtilKSettingsSecure.getInt(UtilKContentResolver.get(_context), CSettings.Secure.ACCESSIBILITY_ENABLED)
            "hasAccessibility permissionEnable $permissionEnable".d(TAG)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            "hasAccessibility error finding setting, default accessibility to not found ${e.message}".e(TAG)
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            "hasAccessibility accessibility is enabled".d(TAG)
            UtilKSettingsSecure.getString(UtilKContentResolver.get(_context), CSettings.Secure.ENABLED_ACCESSIBILITY_SERVICES)?.let {
                stringColonSplitter.setString(it)
                while (stringColonSplitter.hasNext()) {
                    val accessibilityService = stringColonSplitter.next()
                    "isSettingAccessibilityPermissionEnable accessibilityService $accessibilityService - service $strService".v(TAG)
                    if (accessibilityService.equals(strService, ignoreCase = true)) {
                        "hasAccessibility we've found the correct setting - accessibility is switched on!".i(TAG)
                        return true
                    }
                }
            }
        } else "hasAccessibility accessibility is disabled".e(TAG)
        return false
    }
}