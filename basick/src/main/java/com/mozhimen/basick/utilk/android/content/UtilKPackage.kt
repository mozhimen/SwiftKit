package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrClazz
import java.util.Arrays


/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/21 16:19
 * @Version 1.0
 */
object UtilKPackage : BaseUtilK() {

    @JvmStatic
    fun getVersionCode(): Int =
        UtilKPackageInfo.getVersionCode(_context)

    @JvmStatic
    fun getPackageName(): String =
        UtilKContext.getPackageName(_context)

    @JvmStatic
    fun getVersionName(): String =
        UtilKPackageInfo.getVersionName(_context)

    @JvmStatic
    fun getRequestedPermissionsStr(): String =
        Arrays.toString(UtilKPackageInfo.getRequestedPermissions(_context))

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    @JvmStatic
    fun isPackageInstalled(context: Context, strPackageName: String): Boolean =
        UtilKPackageManager.isPackageInstalled(context, strPackageName)

    @JvmStatic
    fun hasPackageOfQuery(context: Context, strPackageName: String): Boolean =
        UtilKPackageManager.hasPackageOfQuery(context, strPackageName)

    @JvmStatic
    fun hasPackage(context: Context, strPackageName: String): Boolean =
        UtilKPackageManager.hasPackage(context, strPackageName)

    @JvmStatic
    fun hasPackageOfClazz(strPackageNameWithActivity: String): Boolean =
        UtilKStrClazz.isStrClassPackageExists(strPackageNameWithActivity)
}