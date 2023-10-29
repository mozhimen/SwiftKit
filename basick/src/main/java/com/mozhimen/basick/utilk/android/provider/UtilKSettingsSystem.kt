package com.mozhimen.basick.utilk.android.provider

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver

/**
 * @ClassName UtilKSettingSystem
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:57
 * @Version 1.0
 */
object UtilKSettingsSystem {
    @JvmStatic
    fun getString(contentResolver: ContentResolver, name: String): String =
        Settings.System.getString(contentResolver, name)

    @JvmStatic
    fun getString(context: Context, name: String): String =
        Settings.System.getString(UtilKContentResolver.get(context), name)

    @JvmStatic
    fun getAndroidId(context: Context): String =
        getString(context, CSettings.Secure.ANDROID_ID)
}