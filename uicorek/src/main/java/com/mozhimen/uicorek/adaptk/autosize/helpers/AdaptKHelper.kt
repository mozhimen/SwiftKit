package com.mozhimen.uicorek.adaptk.autosize.helpers

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeclare_InManifest
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.utilk.android.content.UtilKConfiguration
import com.mozhimen.uicorek.adaptk.autosize.AdaptKAutoSize
import me.jessyan.autosize.AutoSizeCompat


/**
 * @ClassName AdaptK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 14:17
 * @Version 1.0
 */
object AdaptKHelper {
    @OptIn(ALintKOptIn_ApiInit_InApplication::class, ALintKOptIn_ApiDeclare_InManifest::class)
    @JvmStatic
    fun genContextResource(context: Context): Context {
        return object : ContextWrapper(context) {
            private var _oldResources: Resources = Resources(super.getResources().assets, super.getResources().displayMetrics, super.getResources().configuration)

            override fun getResources(): Resources {
                if (UtilKConfiguration.isOrientationLandscape(_oldResources)) {
                    AutoSizeCompat.autoConvertDensityBaseOnWidth(_oldResources, AdaptKAutoSize.instance.getLength().toFloat())
                } else {
                    AutoSizeCompat.autoConvertDensityBaseOnHeight(_oldResources, AdaptKAutoSize.instance.getLength().toFloat())
                }
                return _oldResources
            }
        }
    }
}