package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.View
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKActivityOptions
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/25
 * @Version 1.0
 */
object UtilKActivityOptions {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun makeSceneTransitionAnimation(activity: Activity, vararg sharedElements: Pair<View, String>): ActivityOptions =
        ActivityOptions.makeSceneTransitionAnimation(activity, *sharedElements)
}