package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import com.mozhimen.basick.elemk.android.view.cons.CWindow
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import kotlin.math.abs

/**
 * @ClassName UtilKTitleBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:34
 * @Version 1.0
 */
object UtilKTitleBar {
    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getHeight(activity: Activity) =
        abs(UtilKView.getViewDrawHeight(activity) - UtilKStatusBar.getHeight(activity))

    @JvmStatic
    fun hide(activity: Activity) {
        UtilKActivity.requestWindowFeature(activity, CWindow.Feature.NO_TITLE)
    }
}