package com.mozhimen.basick.utilk.android.provider

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKSettingsSecure
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:53
 * @Version 1.0
 */
object UtilKSettingsSecure {
    @JvmStatic
    @Throws(Settings.SettingNotFoundException::class)
    fun getInt(contentResolver: ContentResolver, name: String): Int =
        Settings.Secure.getInt(contentResolver, name)

    @JvmStatic
    @Throws(Settings.SettingNotFoundException::class)
    fun getInt(context: Context, name: String): Int =
        Settings.Secure.getInt(UtilKContentResolver.get(context), name)

    @JvmStatic
    fun getString(contentResolver: ContentResolver, name: String): String =
        Settings.Secure.getString(contentResolver, name)

    @JvmStatic
    fun getString(context: Context, name: String): String =
        Settings.Secure.getString(UtilKContentResolver.get(context), name)

    //////////////////////////////////////////////////////////////////////////

    /**
     * 判断定位服务是否开启
     */
    fun isLocationEnabled(context: Context): Boolean {
        return if (UtilKBuildVersion.isAfterV_19_44_K()) {
            val locationMode = try {
                getInt(context, CSettings.Secure.LOCATION_MODE)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            locationMode != CSettings.Secure.LOCATION_MODE_OFF
        } else {
            getString(context, CSettings.Secure.LOCATION_PROVIDERS_ALLOWED).isNotEmpty()
        }
    }
}