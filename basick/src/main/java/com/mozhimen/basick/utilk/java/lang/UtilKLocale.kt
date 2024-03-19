package com.mozhimen.basick.utilk.java.lang

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics
import java.util.Locale


/**
 * @ClassName UtilKLocale
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/13 14:15
 * @Version 1.0
 */
fun Locale.applyLanguage(context: Context) {
    UtilKLocale.applyLanguage(this, context)
}

object UtilKLocale {
    /**
     * 这个方法虽然更新了资源但是只能以后的界面生效，之前没有finish的页面还是保留原来的语言
     */
    @JvmStatic
    fun applyLanguage(locale: Locale, context: Context) {
        val configuration: Configuration = UtilKConfiguration.get_ofApp(context)
        val displayMetrics: DisplayMetrics = UtilKDisplayMetrics.getApp(context)
        if (UtilKBuildVersion.isAfterV_17_42_J1()) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        UtilKResources.updateConfiguration_ofApp(context, configuration, displayMetrics)
    }
}