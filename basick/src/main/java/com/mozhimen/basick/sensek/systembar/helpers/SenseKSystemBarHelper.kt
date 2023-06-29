package com.mozhimen.basick.sensek.systembar.helpers

import android.app.Activity
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarMultiProperty
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType
import com.mozhimen.basick.utilk.android.view.UtilKContentView
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKSystemBar

/**
 * @ClassName SenseKSystemBarHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 15:34
 * @Version 1.0
 */
object SenseKSystemBarHelper {
    @JvmStatic
    @ASenseKSystemBarMultiProperty(CSystemBarType.LOW_PROFILE)
    fun setLowProfile(activity: Activity) {
        UtilKSystemBar.setStatusBarLowProfile(activity)
    }

    @JvmStatic
    @ASenseKSystemBarMultiProperty(CSystemBarType.IMMERSED_LIGHT)
    fun setImmersedLight(activity: Activity) {
        UtilKSystemBar.setImmersed(activity)
    }

    @JvmStatic
    @ASenseKSystemBarMultiProperty(CSystemBarType.IMMERSED_FORCE)
    fun setImmersedForce(activity: Activity) {
        UtilKSystemBar.setImmersed(activity)
        UtilKDecorView.setSystemUiVisibilityOr(activity, CView.SystemUi.FLAG_IMMERSIVE)
    }

    @JvmStatic
    @ASenseKSystemBarMultiProperty(CSystemBarType.IMMERSED_STICKY)
    fun setImmersedSticky(activity: Activity) {
        UtilKSystemBar.setImmersed(activity)
        UtilKDecorView.setSystemUiVisibilityOr(activity, CView.SystemUi.FLAG_IMMERSIVE_STICKY)
    }
}