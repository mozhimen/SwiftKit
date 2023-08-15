package com.mozhimen.basick.utilk.android.app

import android.annotation.TargetApi
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKEnvironment
import com.mozhimen.basick.utilk.android.os.isBeforeVersion
import com.mozhimen.basick.utilk.android.provider.UtilKSettings
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.util.it
import com.mozhimen.basick.utilk.android.util.vt

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:06
 * @Version 1.0
 */
object UtilKPermission : BaseUtilK() {

    @JvmStatic
    fun checkPermissions(permissions: Array<String>): Boolean =
        checkPermissions(permissions.toList())

    @JvmStatic
    fun checkPermissions(permissions: List<String>): Boolean {
        var allGranted = true
        return if (permissions.isEmpty()) true
        else {
            for (permission in permissions)
                allGranted = allGranted and checkPermission(permission)
            allGranted
        }
    }

    @JvmStatic
    fun checkPermission(permission: String): Boolean =
        UtilKContextCompat.isSelfPermissionGranted(_context, permission)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasWriteRead(): Boolean =
        if (UtilKBuildVersion.isAfterV_30_11_R())
            UtilKEnvironment.isExternalStorageManager()
        else
            checkPermissions(arrayOf(CPermission.READ_EXTERNAL_STORAGE, CPermission.WRITE_EXTERNAL_STORAGE))

    @JvmStatic
    fun hasOverlay(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M()) hasOverlay2() else true

    /**
     * 是否有Overlay的权限
     * @return Boolean
     */
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun hasOverlay2(): Boolean =
        CVersCode.V_23_6_M.isBeforeVersion() || UtilKSettings.canDrawOverlays(_context)

    /**
     * 是否有文件管理权限
     * @return Boolean
     */
    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    @ADescription(CSettings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
    fun hasExternalStorage(): Boolean =
        UtilKEnvironment.isExternalStorageManager()

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun hasPackageInstalls(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O()) hasPackageInstallsAfter26() else true

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @TargetApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @ADescription(CSettings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun hasPackageInstallsAfter26(): Boolean =
        UtilKPackageManager.canRequestPackageInstalls(_context)
            .also { "isAppInstallsPermissionEnable: $it".dt(TAG) }

    /**
     * 是否有无障碍权限
     * @return Boolean
     */
    @JvmStatic
    fun hasAccessibility(serviceClazz: Class<*>): Boolean {
        var permissionEnable = 0
        val strService = "${UtilKPackage.getPackageName()}/${serviceClazz.canonicalName}"
        try {
            permissionEnable = UtilKSettings.getSecureInt(
                UtilKContentResolver.get(_context),
                CSettings.Secure.ACCESSIBILITY_ENABLED
            )
            "hasAccessibility permissionEnable $permissionEnable".dt(TAG)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            "hasAccessibility error finding setting, default accessibility to not found ${e.message}".et(
                TAG
            )
        }
        val stringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (permissionEnable == 1) {
            "hasAccessibility accessibility is enabled".dt(TAG)
            UtilKSettings.getSecureString(
                UtilKContentResolver.get(_context),
                CSettings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )?.let {
                stringColonSplitter.setString(it)
                while (stringColonSplitter.hasNext()) {
                    val accessibilityService = stringColonSplitter.next()
                    "isSettingAccessibilityPermissionEnable accessibilityService $accessibilityService - service $strService".vt(
                        TAG
                    )
                    if (accessibilityService.equals(strService, ignoreCase = true)) {
                        "hasAccessibility we've found the correct setting - accessibility is switched on!".it(
                            TAG
                        )
                        return true
                    }
                }
            }
        } else "hasAccessibility accessibility is disabled".et(TAG)
        return false
    }
}