package com.mozhimen.basick.utilk.android.content

import com.mozhimen.basick.utilk.bases.BaseUtilK
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
}