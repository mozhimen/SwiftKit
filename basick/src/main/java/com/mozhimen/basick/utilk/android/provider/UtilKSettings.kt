package com.mozhimen.basick.utilk.android.provider

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.IUtilK


/**
 * @ClassName UtilKSettings
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 13:48
 * @Version 1.0
 */
object UtilKSettings : IUtilK {
    @JvmStatic
    @Throws(Settings.SettingNotFoundException::class)
    fun getSecureInt(contentResolver: ContentResolver, name: String): Int =
        Settings.Secure.getInt(contentResolver, name)

    @JvmStatic
    @Throws(Settings.SettingNotFoundException::class)
    fun getSecureInt(context: Context, name: String): Int =
        Settings.Secure.getInt(UtilKContentResolver.get(context), name)

    @JvmStatic
    fun getSecureString(contentResolver: ContentResolver, name: String): String =
        Settings.Secure.getString(contentResolver, name)

    @JvmStatic
    fun getSecureString(context: Context, name: String): String =
        Settings.Secure.getString(UtilKContentResolver.get(context), name)

    //////////////////////////////////////////////////////////////////////////

    /**
     * 判断定位服务是否开启
     */
    fun isLocationEnabled(context: Context): Boolean {
        return if (UtilKBuildVersion.isAfterV_19_44_K()) {
            val locationMode = try {
                getSecureInt(context, CSettings.Secure.LOCATION_MODE)
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            locationMode != CSettings.Secure.LOCATION_MODE_OFF
        } else {
            getSecureString(context, CSettings.Secure.LOCATION_PROVIDERS_ALLOWED).isNotEmpty()
        }
    }

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun canDrawOverlays(context: Context): Boolean =
        Settings.canDrawOverlays(context)
}