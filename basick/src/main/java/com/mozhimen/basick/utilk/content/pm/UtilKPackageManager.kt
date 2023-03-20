package com.mozhimen.basick.utilk.content.pm

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:50
 * @Version 1.0
 */
object UtilKPackageManager {

    private val _context = UtilKApplication.instance.get()

    /**
     * getPackageManager
     * @return PackageManager
     */
    @JvmStatic
    fun get(): PackageManager =
        UtilKContext.getPackageManager(_context)

    /**
     * 包安装器
     * @return PackageInstaller
     */
    @JvmStatic
    fun getPackageInstaller(): PackageInstaller =
        get().packageInstaller

    /**
     * 查询所有的符合Intent的Activities
     * @param intent Intent
     * @param flags Int
     * @return List<ResolveInfo>
     */
    @JvmStatic
    fun queryIntentActivities(intent: Intent, flags: Int): List<ResolveInfo> =
        get().queryIntentActivities(intent, flags)

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun canRequestPackageInstalls(): Boolean =
        get().canRequestPackageInstalls()

    /**
     * 是否有前置
     * @return Boolean
     */
    @JvmStatic
    fun hasFrontCamera(): Boolean =
        hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)

    /**
     * 是否有后置
     * @return Boolean
     */
    @JvmStatic
    fun hasBackCamera(): Boolean =
        hasSystemFeature(PackageManager.FEATURE_CAMERA)

    /**
     * 是否有配置
     * @param featureName String
     */
    @JvmStatic
    fun hasSystemFeature(featureName: String): Boolean =
        get().hasSystemFeature(featureName)
}