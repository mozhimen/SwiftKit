package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics

/**
 * @ClassName KDisplayUtil
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:27
 * @Version 1.0
 */
object UtilKDisplay {

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun get(activity: Activity): Display =
        UtilKActivity.getDisplay(activity)

    /**
     * 获取旋转
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_30_11_R)
    fun getRotation(activity: Activity): Int =
        get(activity).rotation
}
