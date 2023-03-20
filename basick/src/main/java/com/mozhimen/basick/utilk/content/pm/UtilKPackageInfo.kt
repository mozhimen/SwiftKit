package com.mozhimen.basick.utilk.content.pm

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKPackageInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:53
 * @Version 1.0
 */
object UtilKPackageInfo : BaseUtilK() {
    private val _context = UtilKApplication.instance.get()

    /**
     * getPackageInfo
     * @return PackageInfo
     */
    @JvmStatic
    fun get(): PackageInfo =
        UtilKPackageManager.get().getPackageInfo(UtilKContext.getPackageName(_context), PackageInfo.INSTALL_LOCATION_AUTO/*0*/)

    /**
     * 获取ApplicationInfo
     * @return ApplicationInfo
     */
    @JvmStatic
    fun getApplicationInfo(): ApplicationInfo =
        get().applicationInfo

    /**
     * 获取flags
     * @return Int
     */
    @JvmStatic
    fun getFlags(): Int =
        get().applicationInfo.flags

    /**
     * isSystemApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemApp(): Boolean =
        (getFlags() and ApplicationInfo.FLAG_SYSTEM) != 0

    /**
     * isSystemUpdateApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemUpdateApp(): Boolean =
        (getFlags() and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

    /**
     * isUserApp
     * @return Boolean
     */
    @JvmStatic
    fun isUserApp(): Boolean =
        !isSystemApp() && !isSystemUpdateApp()

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getVersionName(): String {
        return try {
            get().versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionName: NameNotFoundException ${e.message}")
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
            if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
                get().longVersionCode.toInt()
            } else {
                get().versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionCode: NameNotFoundException ${e.message}")
            0
        }
    }
}