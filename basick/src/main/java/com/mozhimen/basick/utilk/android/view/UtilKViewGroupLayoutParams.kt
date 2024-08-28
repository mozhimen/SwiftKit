package com.mozhimen.basick.utilk.android.view

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Px

/**
 * @ClassName UtilKViewGroupLayoutParams
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/4 15:48
 * @Version 1.0
 */
fun View.applyMargin(@Px verticalMargin: Int, @Px horizontalMargin: Int) {
    UtilKViewGroupLayoutParams.applyMargin(this, verticalMargin, horizontalMargin)
}

fun View.applyMarginVertical(@Px margin: Int) {
    UtilKViewGroupLayoutParams.applyMarginVertical(this, margin)
}

fun View.applyMarginEnd(@Px rightMargin: Int) {
    UtilKViewGroupLayoutParams.applyMarginEnd(this, rightMargin)
}


///////////////////////////////////////////////////////////////////////////

object UtilKViewGroupLayoutParams {

    @JvmStatic
    fun get_ofMargin(view: View): ViewGroup.MarginLayoutParams? =
        when (view.layoutParams) {
            is ViewGroup.MarginLayoutParams -> view.layoutParams as ViewGroup.MarginLayoutParams
            else -> null
        }

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyMargin(view: View, @Px verticalMargin: Int, @Px horizontalMargin: Int) {
        applyMargin(view, horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
    }

    @JvmStatic
    fun applyMargin(view: View, @Px leftMargin: Int, @Px topMargin: Int, @Px rightMargin: Int, @Px bottomMargin: Int) {
        when (view.layoutParams) {
            is ViewGroup.MarginLayoutParams -> {
                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
                view.layoutParams = layoutParams
            }
        }
    }

    @JvmStatic
    fun applyMarginEnd(view: View, @Px rightMargin: Int) {
        when (view.layoutParams) {
            is ViewGroup.MarginLayoutParams -> {
                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                val leftMargin = layoutParams.leftMargin
                val topMargin = layoutParams.topMargin
                val bottomMargin = layoutParams.bottomMargin
                layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
                view.layoutParams = layoutParams
            }
        }
    }

    @JvmStatic
    fun applyMarginVertical(view: View, @Px margin: Int) {
        applyMarginVertical(view, margin, margin)
    }

    @JvmStatic
    fun applyMarginVertical(view: View, @Px topMargin: Int, @Px bottomMargin: Int) {
        when (view.layoutParams) {
            is ViewGroup.MarginLayoutParams -> {
                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, bottomMargin)
                view.layoutParams = layoutParams
            }
        }
    }
}