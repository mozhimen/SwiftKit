package com.mozhimen.abilityk.adaptk

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import com.mozhimen.basick.utilk.res.UtilKConfiguration
import me.jessyan.autosize.AutoSizeCompat


/**
 * @ClassName AdaptK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 14:17
 * @Version 1.0
 */
object AdaptK {
    @JvmStatic
    fun genContextResource(context: Context): Context {
        return object : ContextWrapper(context) {
            private var _oldResources: Resources = Resources(super.getResources().assets, super.getResources().displayMetrics, super.getResources().configuration)

            override fun getResources(): Resources {
                if (UtilKConfiguration.getConfiguration(_oldResources).orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    AutoSizeCompat.autoConvertDensityBaseOnWidth(_oldResources, AdaptKMgr.instance.getLength().toFloat())
                } else {
                    AutoSizeCompat.autoConvertDensityBaseOnHeight(_oldResources, AdaptKMgr.instance.getLength().toFloat())
                }
                return _oldResources
            }
        }
    }
}