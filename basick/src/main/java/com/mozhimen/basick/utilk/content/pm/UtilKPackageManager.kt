package com.mozhimen.basick.utilk.content.pm

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:50
 * @Version 1.0
 */
@AManifestKRequire(CPermission.REQUEST_INSTALL_PACKAGES)
object UtilKPackageManager {

    /**
     * getPackageManager
     * @return PackageManager
     */
    @JvmStatic
    fun get(context: Context): PackageManager =
        UtilKContext.getPackageManager(context)

    /**
     * 包安装器
     * @return PackageInstaller
     */
    @JvmStatic
    fun getPackageInstaller(context: Context): PackageInstaller =
        get(context).packageInstaller

    /**
     * 得到应用名
     * @param packageManager PackageManager
     * @param applicationInfo ApplicationInfo
     * @return String
     */
    @JvmStatic
    fun getApplicationLabel(packageManager: PackageManager, applicationInfo: ApplicationInfo): String =
        packageManager.getApplicationLabel(applicationInfo).toString()

    /**
     * 得到图标信息
     * @param packageManager PackageManager
     * @param applicationInfo ApplicationInfo
     * @return Drawable
     */
    @JvmStatic
    fun getApplicationIcon(packageManager: PackageManager, applicationInfo: ApplicationInfo): Drawable =
        packageManager.getApplicationIcon(applicationInfo)

    /**
     * 查询所有的符合Intent的Activities
     * @param intent Intent
     * @param flags Int
     * @return List<ResolveInfo>
     */
    @JvmStatic
    fun queryIntentActivities(context: Context, intent: Intent, flags: Int): List<ResolveInfo> =
        get(context).queryIntentActivities(intent, flags)

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun canRequestPackageInstalls(context: Context): Boolean =
        get(context).canRequestPackageInstalls()

    /**
     * 是否有前置
     * @return Boolean
     */
    @JvmStatic
    fun hasFrontCamera(context: Context): Boolean =
        hasSystemFeature(context, PackageManager.FEATURE_CAMERA_FRONT)

    /**
     * 是否有后置
     * @return Boolean
     */
    @JvmStatic
    fun hasBackCamera(context: Context): Boolean =
        hasSystemFeature(context, PackageManager.FEATURE_CAMERA)

    /**
     * 是否有配置
     * @param featureName String
     */
    @JvmStatic
    fun hasSystemFeature(context: Context, featureName: String): Boolean =
        get(context).hasSystemFeature(featureName)
}