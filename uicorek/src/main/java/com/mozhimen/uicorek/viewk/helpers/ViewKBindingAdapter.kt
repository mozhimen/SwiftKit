package com.mozhimen.uicorek.viewk.helpers

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
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

    @JvmStatic
    @BindingAdapter(value = ["loadBackgroundColorWhen", "loadBackgroundColorWhen_statusTrue", "loadBackgroundColorWhen_statusFalse"], requireAll = true)
    fun loadBackgroundColorWhen(view: View, boolean: Boolean, @ColorInt loadBackgroundColorWhen_statusTrue: Int, @ColorInt loadBackgroundColorWhen_statusFalse: Int) {
        if (boolean) {
            view.setBackgroundColor(loadBackgroundColorWhen_statusTrue)
        } else {
            view.setBackgroundColor(loadBackgroundColorWhen_statusFalse)
        }
    }
}