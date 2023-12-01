package com.mozhimen.componentk.installk.manager

import android.content.Context
import android.content.pm.PackageInfo
import android.util.Log
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackageInfo
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.collections.containsBy

/**
 * @ClassName AppInstallManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/9 11:10
 * @Version 1.0
 */
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.REQUEST_INSTALL_PACKAGES)
object InstallKManager : BaseUtilK() {

    private val _installedPackageInfos = mutableListOf<PackageInfo>()//用来保存包的信息

    /////////////////////////////////////////////////////////////////////////

    fun init(context: Context) {
        if (_installedPackageInfos.isEmpty()) {
            _installedPackageInfos.addAll(UtilKPackageManager.getInstalledPackages(context, false).also {
                Log.d(TAG, "init: _installedPackageInfos packages ${it.map { packageInfo -> packageInfo.packageName }}")
            })
        }
    }

    /////////////////////////////////////////////////////////////////////////

    fun getPackageInfoByPackageName(packageName: String): PackageInfo? {
        return _installedPackageInfos.find { it.packageName == packageName }
    }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 查询应用是否安装
     */
    @JvmStatic
    fun hasPackageName(packageName: String): Boolean =
        _installedPackageInfos.containsBy { exist -> packageName == exist.packageName }

    /**
     * 查询应用是否安装
     */
    @JvmStatic
    fun hasPackageNameAndVersion(packageName: String, versionCode: Int): Boolean =
        _installedPackageInfos.containsBy { exist -> packageName == exist.packageName && versionCode <= UtilKPackageInfo.getVersionCode(exist) }

    @JvmStatic
    fun getByPackageName(packageName: String): PackageInfo? =
        _installedPackageInfos.find { it.packageName == packageName }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 应用安装的时候调用
     */
    @JvmStatic
    fun onPackageAdded(packageName: String) {
        Log.d(TAG, "onPackageAdded: packageName $packageName")
        if (hasPackageName(packageName)) {
            Log.d(TAG, "onPackageAdded: already has package")
            return
        }
        UtilKPackageInfo.get(_context, packageName, 0)?.let {
            Log.d(TAG, "onPackageAdded: add packageName $packageName")
            _installedPackageInfos.add(it)
        }
    }

    /**
     * 应用卸载的时候调用
     */
    @JvmStatic
    fun onPackageRemoved(packageName: String) {
        Log.d(TAG, "onPackageRemoved: packageName $packageName")
        if (!hasPackageName(packageName)) {
            Log.d(TAG, "onPackageRemoved: already remove package")
        }
        val iterator = _installedPackageInfos.iterator()
        while (iterator.hasNext()) {
            val packageInfo = iterator.next()
            if (packageInfo.packageName == packageName) {
                Log.d(TAG, "onPackageRemoved: remove packageName $packageName")
                iterator.remove()
                break
            }
        }
    }
}