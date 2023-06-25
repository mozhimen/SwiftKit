package com.mozhimen.basick.utilk.app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.pm.UtilKApplicationInfo
import com.mozhimen.basick.utilk.content.pm.UtilKPackageInfo
import com.mozhimen.basick.utilk.content.pm.UtilKPackageManager


/**
 * @ClassName UtilKApk
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:06
 * @Version 1.0
 */
object UtilKApk : BaseUtilK() {

    @JvmStatic
    fun getPackageArchiveInfo(apkPathWithName: String): PackageInfo? =
        UtilKPackageManager.getPackageArchiveInfo(_context, apkPathWithName, PackageManager.GET_ACTIVITIES)

    @JvmStatic
    fun getApplicationInfo(apkPathWithName: String): ApplicationInfo? =
        getPackageArchiveInfo(apkPathWithName)?.let { UtilKApplicationInfo.get(it) }

    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 得到应用名
     * @param apkPathWithName String
     * @return String?
     */
    @JvmStatic
    fun getApplicationLabel(apkPathWithName: String): String? =
        getApplicationInfo(apkPathWithName)?.let { UtilKPackageManager.getApplicationLabel(_context, it) }

    /**
     * 得到包名
     * @param apkPathWithName String
     * @return String?
     */
    @JvmStatic
    fun getPackageName(apkPathWithName: String): String? =
        getApplicationInfo(apkPathWithName)?.let { UtilKApplicationInfo.getPackageName(it) }

    /**
     * 得到版本信息
     * @param apkPathWithName String
     * @return String?
     */
    @JvmStatic
    fun getVersionName(apkPathWithName: String): String? =
        getPackageArchiveInfo(apkPathWithName)?.let { UtilKPackageInfo.getVersionName(it) }

    /**
     * 得到版本号
     * @param apkPathWithName String
     * @return Int?
     */
    @JvmStatic
    fun getVersionCode(apkPathWithName: String): Int? =
        getPackageArchiveInfo(apkPathWithName)?.let { UtilKPackageInfo.getVersionCode(it) }

    /**
     * 得到图标信息
     * @param apkPathWithName String
     * @return Drawable?
     */
    @JvmStatic
    fun getApplicationIcon(apkPathWithName: String): Drawable? =
        getApplicationInfo(apkPathWithName)?.let {
            it.apply {
                sourceDir = apkPathWithName
                publicSourceDir = apkPathWithName/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            }
            UtilKPackageManager.getApplicationIcon(_context, it)
        }

    /**
     * 得到图标信息2
     * @param apkPathWithName String
     * @return Drawable?
     */
    @JvmStatic
    fun getApplicationIcon2(apkPathWithName: String): Drawable? =
        getApplicationInfo(apkPathWithName)?.let {
            it.apply {
                sourceDir = apkPathWithName
                publicSourceDir = apkPathWithName/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            }
            UtilKApplicationInfo.loadIcon(it, UtilKPackageManager.get(_context))
        }

    /**
     * 打印apk包的信息：版本号，名称，图标等
     * @param apkPathWithName apk包的绝对路径
     */
    @JvmStatic
    fun printApkInfo(apkPathWithName: String) {
        // 得到应用名
        getApplicationLabel(apkPathWithName)?.let {
            Log.d(TAG, "printApkInfo: getApplicationLabel $it")
        }
        getPackageName(apkPathWithName)?.let {
            Log.d(TAG, "printApkInfo: getPackageName $it")
        }
        getVersionName(apkPathWithName)?.let {
            Log.d(TAG, "printApkInfo: getVersionName $it")
        }
        getVersionCode(apkPathWithName)?.let {
            Log.d(TAG, "printApkInfo: getVersionCode $it")
        }
    }
}