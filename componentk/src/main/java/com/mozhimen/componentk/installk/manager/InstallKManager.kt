package com.mozhimen.componentk.installk.manager

import android.content.Context
import android.content.pm.PackageInfo
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
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
object InstallKManager : BaseUtilK() {

    private val _installedPackageInfos = mutableListOf<PackageInfo>()//用来保存包的信息

    /////////////////////////////////////////////////////////////////////////

    fun init(context: Context) {
        if (_installedPackageInfos.isEmpty()) {
            _installedPackageInfos.addAll(UtilKPackageManager.getInstalledPackages(context, false))
        }
    }

    /////////////////////////////////////////////////////////////////////////

    fun getPackageInfoByPackageName(packageName: String): PackageInfo? {
        getAllInstalledPackage(_context)//再查询当前应用是否安装
        for (packageInfo in _installedPackageInfos) {
            if (packageInfo.packageName == packageName) {
                return packageInfo
            }
        }
        return null
    }

    /////////////////////////////////////////////////////////////////////////
    /**
     * 查询应用是否安装
     */
    fun isInstall(packageName: String): Boolean =
        _installedPackageInfos.containsBy { it.packageName == packageName }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 应用安装的时候调用
     */
    @JvmStatic
    fun onPackageAdded(context: Context, packageName: String) {
        UtilKPackageInfo.get(context, packageName, 0)?.let {
            _installedPackageInfos.add(it)
        }
    }

    /**
     * 应用卸载的时候调用
     */
    @JvmStatic
    fun onPackageRemoved(packageName: String) {
        val iterator = _installedPackageInfos.iterator()
        while (iterator.hasNext()) {
            val packageInfo = iterator.next()
            if (packageInfo.packageName == packageName) {
                iterator.remove()
                break
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 查询手机中安装的所有应用
     */
    private fun getAllInstalledPackage(context: Context) {
        if (_installedPackageInfos.isEmpty()) {
            _installedPackageInfos.addAll(UtilKPackageManager.getInstalledPackages(context, false))
        }
    }

}