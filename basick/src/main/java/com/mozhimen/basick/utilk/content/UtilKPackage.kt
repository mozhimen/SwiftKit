package com.mozhimen.basick.utilk.content

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode


/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/11 17:21
 * @Version 1.0
 */
object UtilKPackage {
    private const val TAG = "UtilKPackage>>>>>"

    private val _context = UtilKApplication.instance.get()

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getVersionName(): String {
        return try {
            getPackageInfo().versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getPkgVersionName: NameNotFoundException ${e.message}")
            ""
        }
    }

    /**
     * 获取程序版本号
     * @return Int
     */
    @JvmStatic
    fun getVersionCode(): Int {
        return try {
            val packageInfo = getPackageInfo()
            if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getPkgVersionCode: NameNotFoundException ${e.message}")
            1
        }
    }

    /**
     * getPackageInfo
     * @return PackageInfo
     */
    @JvmStatic
    fun getPackageInfo(): PackageInfo =
        _context.packageManager.getPackageInfo(_context.packageName, PackageInfo.INSTALL_LOCATION_AUTO/*0*/)
}