package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.PackageInfo
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKPackageArchiveInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/27 1:10
 * @Version 1.0
 */
object UtilKPackageArchiveInfo : BaseUtilK() {
    @JvmStatic
    fun get(context: Context, archiveFilePath: String, flags: Int): PackageInfo? =
        UtilKPackageManager.getPackageArchiveInfo(context, archiveFilePath, flags)

    @JvmStatic
    fun get_ofGET_ACTIVITIES(context: Context, archiveFilePath: String): PackageInfo? =
        get(context, archiveFilePath, CPackageManager.GET_ACTIVITIES)
}