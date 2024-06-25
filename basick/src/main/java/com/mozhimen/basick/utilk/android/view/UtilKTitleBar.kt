package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.elemk.android.view.cons.CWindow
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKAppCompatActivity
import com.mozhimen.basick.utilk.commons.IUtilK
import kotlin.math.abs

/**
 * @ClassName UtilKTitleBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/23 23:34
 * @Version 1.0
 */
object UtilKTitleBar : IUtilK {
    /**
     * 获取标题栏高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期的结果
     */
    @JvmStatic
    fun getHeight(activity: Activity) =
        abs(UtilKContentViewWrapper.getViewDrawHeight_ofWin(activity) - UtilKStatusBar.getHeight(activity))

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hide(activity: Activity) {
        try {
            if (activity is AppCompatActivity) {
                UtilKAppCompatActivity.supportRequestWindowFeature(activity, CWindow.FEATURE_NO_TITLE)   //继承AppCompatActivity中使用
            } else
                UtilKActivity.requestWindowFeature(activity, CWindow.Feature.NO_TITLE)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "hide: ", e)
        }
    }
}