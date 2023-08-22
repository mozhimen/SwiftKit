package com.mozhimen.basick.utilk.android.location

import android.content.Context
import android.location.Geocoder
import java.util.Locale


/**
 * @ClassName UtilKGeocoder
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/22 20:09
 * @Version 1.0
 */
object UtilKGeocoder {
    @JvmStatic
    fun get(context: Context, locale: Locale = Locale.CHINA): Geocoder =
        Geocoder(context, locale)
}