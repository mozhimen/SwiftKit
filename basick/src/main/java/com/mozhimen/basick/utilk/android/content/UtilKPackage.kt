package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.PackageInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrClazz


/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/21 16:19
 * @Version 1.0
 */
object UtilKPackage : BaseUtilK() {
    @JvmStatic
    fun getPackageInfo(strPackageName: String, flags: Int): PackageInfo? =
        UtilKPackageInfo.get(_context, strPackageName, flags)

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getVersionCode(strPackageName: String, flags: Int): Int =
        UtilKPackageInfo.getVersionCode(_context, strPackageName, flags)

    @JvmStatic
    fun getVersionCode(flags: Int): Int =
        UtilKPackageInfo.getVersionCode(_context, _context.packageName, flags)

    @JvmStatic
    fun getPackageName(): String =
        UtilKContext.getPackageName(_context)

    @JvmStatic
    fun getVersionName(strPackageName: String, flags: Int): String =
        UtilKPackageInfo.getVersionName(_context, strPackageName, flags)

    @JvmStatic
    fun getVersionName(flags: Int): String =
        UtilKPackageInfo.getVersionName(_context, _context.packageName, flags)

    @JvmStatic
    fun getRequestedPermissionsStr(strPackageName: String, flags: Int): String =
        UtilKPackageInfo.getRequestedPermissions(_context, strPackageName, flags).contentToString()

    @JvmStatic
    fun getRequestedPermissionsStr(flags: Int): String =
        UtilKPackageInfo.getRequestedPermissions(_context, _context.packageName, flags).contentToString()

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取所有安装程序包名
     */
    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getInstalledPackages(hasSystemPackages: Boolean): List<PackageInfo> =
        UtilKPackageManagerWrapper.getInstalledPackages(_context, hasSystemPackages)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun hasPackage(strPackageName: String, flags: Int): Boolean =
        hasPackage_ofPackageManager_enabled(strPackageName, flags)
                || hasPackage_ofPackageInfo_enabled(strPackageName, flags)
                || hasPackage_ofPackageManager(strPackageName, flags)
                || hasPackage_ofPackageInfo(strPackageName, flags)
                || hasPackage_ofClazz("${strPackageName}.MainActivity")

    @JvmStatic
    fun hasPackage_ofPackageManager_enabled(strPackageName: String, flags: Int): Boolean =
        UtilKApplicationInfo.enabled_ofPackageManager(_context, strPackageName, flags)

    @JvmStatic
    fun hasPackage_ofPackageInfo_enabled(strPackageName: String, flags: Int): Boolean =
        UtilKApplicationInfo.enabled_ofPackageInfo(_context, strPackageName, flags)

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun hasPackage_ofPackageManager(strPackageName: String, flags: Int): Boolean =
        UtilKPackageManager.queryIntentActivities(_context, UtilKIntentWrapper.getMainLauncher_ofPackage(strPackageName), flags).isNotEmpty()

    @JvmStatic
    fun hasPackage_ofPackageInfo(strPackageName: String, flags: Int): Boolean =
        UtilKPackageInfo.hasPackage(_context, strPackageName, flags)

    @JvmStatic
    fun hasPackage_ofClazz(strPackageNameWithActivity: String): Boolean =
        UtilKStrClazz.isStrClassPackageExists(strPackageNameWithActivity)

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * 系统的下载组件是否可用
     */
    @JvmStatic
    fun isDownloadComponentEnabled(): Boolean =
        UtilKPackageManagerWrapper.isDownloadComponentEnabled(_context)

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否有前置
     */
    @JvmStatic
    fun hasFrontCamera(): Boolean =
        UtilKPackageManagerWrapper.hasFrontCamera(_context)

    /**
     * 是否有后置
     */
    @JvmStatic
    fun hasBackCamera(): Boolean =
        UtilKPackageManagerWrapper.hasBackCamera(_context)
}