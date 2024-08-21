package com.mozhimen.basick.utilk.wrapper

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.android.content.UtilKApplicationInfo
import com.mozhimen.basick.utilk.android.content.UtilKPackageArchiveInfo
import com.mozhimen.basick.utilk.android.content.UtilKPackageInfo
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKApk
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:06
 * @Version 1.0
 */
object UtilKApk : BaseUtilK() {

    //得到版本信息
    @JvmStatic
    fun getVersionName(strPathNameApk: String): String? =
        UtilKPackageArchiveInfo.get_ofGET_ACTIVITIES(_context, strPathNameApk)?.let { UtilKPackageInfo.getVersionName(it) }

    //得到版本号
    @JvmStatic
    fun getVersionCode(strPathNameApk: String): Int? =
        UtilKPackageArchiveInfo.get_ofGET_ACTIVITIES(_context, strPathNameApk)?.let { UtilKPackageInfo.getVersionCode(it) }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getApplicationInfo(strPathNameApk: String): ApplicationInfo? =
        UtilKPackageArchiveInfo.get_ofGET_ACTIVITIES(_context, strPathNameApk)?.let { UtilKPackageInfo.getApplicationInfo(it) }

    //得到应用名
    @JvmStatic
    fun getApplicationLabel(strPathNameApk: String): String? =
        getApplicationInfo(strPathNameApk)?.let { UtilKPackageManager.getApplicationLabel(_context, it) }

    //得到包名
    @JvmStatic
    fun getPackageName(strPathNameApk: String): String? =
        getApplicationInfo(strPathNameApk)?.let { UtilKApplicationInfo.getPackageName(it) }

    //得到图标信息
    @JvmStatic
    fun getApplicationIcon(strPathNameApk: String): Drawable? =
        getApplicationInfo(strPathNameApk)?.let {
            it.apply {
                sourceDir = strPathNameApk
                publicSourceDir = strPathNameApk/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            }
            UtilKPackageManager.getApplicationIcon(_context, it)
        }

    //得到图标信息2
    @JvmStatic
    fun getApplicationIcon_ofLoadIcon(strPathNameApk: String): Drawable? =
        getApplicationInfo(strPathNameApk)?.let {
            it.apply {
                sourceDir = strPathNameApk
                publicSourceDir = strPathNameApk/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            }
            UtilKApplicationInfo.loadIcon(it, UtilKPackageManager.get(_context))
        }

    /**
     * 打印apk包的信息：版本号，名称，图标等
     * @param strPathNameApk apk包的绝对路径
     */
    @JvmStatic
    fun printApkInfo(strPathNameApk: String) {
        // 得到应用名
        getApplicationLabel(strPathNameApk)?.let {
            "printApkInfo: getApplicationLabel $it".d(TAG)
        }
        getPackageName(strPathNameApk)?.let {
            "printApkInfo: getPackageName $it".d(TAG)
        }
        getVersionName(strPathNameApk)?.let {
            "printApkInfo: getVersionName $it".d(TAG)
        }
        getVersionCode(strPathNameApk)?.let {
            "printApkInfo: getVersionCode $it".d(TAG)
        }
    }
}