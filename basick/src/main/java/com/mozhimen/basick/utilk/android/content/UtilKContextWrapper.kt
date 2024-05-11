package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Configuration
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivityWrapper
import com.mozhimen.basick.utilk.android.app.UtilKRunningAppProcessInfo
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics
import com.mozhimen.basick.utilk.kotlin.UtilKStrProcess
import java.util.Locale

/**
 * @ClassName UtilKContextWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/19
 * @Version 1.0
 */

fun Context.isMainProcess(): Boolean =
    UtilKContextWrapper.isMainProcess(this)

///////////////////////////////////////////////////////////////////////

object UtilKContextWrapper {
    /**
     * 是否在主线程
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean =
        UtilKStrProcess.isMainProcess(context.packageName)

    @JvmStatic
    @OApiUse_BaseApplication
    fun isFinishingOrDestroyed(context: Context): Boolean =
        UtilKActivityWrapper.isFinishingOrDestroyed(context)

    /**
     * 这个方法虽然更新了资源但是只能以后的界面生效，之前没有finish的页面还是保留原来的语言
     */
    @JvmStatic
    fun updateLocale(context: Context, locale: Locale) {
        val configuration: Configuration = UtilKConfiguration.get_ofApp(context)
        UtilKConfiguration.setLocale(configuration, locale)
        UtilKResources.updateConfiguration_ofApp(context, configuration, UtilKDisplayMetrics.get_ofApp(context))
    }
}