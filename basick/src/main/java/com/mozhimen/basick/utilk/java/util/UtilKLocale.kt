package com.mozhimen.basick.utilk.java.util

import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.util.Locale

/**
 * @ClassName UtilKLocale
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKLocale {
    @JvmStatic
    fun get_ofDefault(): Locale =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            getDefault(Locale.Category.FORMAT)
        else
            getDefault()

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDefault(): Locale =
        Locale.getDefault()

    @JvmStatic
    @RequiresApi(CVersCode.V_24_7_N)
    fun getDefault(category: Locale.Category): Locale =
        Locale.getDefault(category)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun toLanguageTag(locale: Locale): String =
        locale.toLanguageTag()
}