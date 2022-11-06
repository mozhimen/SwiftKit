package com.mozhimen.uicorek.viewk

import android.view.View
import androidx.databinding.BindingAdapter
import com.mozhimen.basick.utilk.view.UtilKView

/**
 * @ClassName ViewKBindingAdapter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:16
 * @Version 1.0
 */
object ViewKBindingAdapter {
    /**
     * 根据View的高度和宽高比, 设置高度
     * @param view View
     * @param viewRatio Float
     */
    @JvmStatic
    @BindingAdapter("viewRatio")
    fun setViewRatio(view: View, viewRatio: Float) {
        UtilKView.setViewRatio(view, viewRatio)
    }
}