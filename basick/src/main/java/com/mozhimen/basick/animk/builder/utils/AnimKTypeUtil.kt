package com.mozhimen.basick.animk.builder.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.helpers.AnimatorBuilder
import com.mozhimen.basick.animk.builder.impls.AnimatorAlpha255Type
import com.mozhimen.basick.animk.builder.impls.AnimatorAlphaViewType
import com.mozhimen.basick.animk.builder.impls.AnimatorColorRecyclerType
import com.mozhimen.basick.animk.builder.impls.AnimatorIntType
import com.mozhimen.basick.animk.builder.impls.AnimatorRotationType
import com.mozhimen.basick.elemk.android.animation.BaseLayoutParamsAnimatorListenerAdapter
import com.mozhimen.basick.elemk.android.animation.BaseViewHolderAnimatorListenerAdapter
import com.mozhimen.basick.utilk.android.view.applyMargin
import com.mozhimen.basick.utilk.androidx.core.UtilKViewCompat

/**
 * @ClassName AnimKTypeUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/24 23:40
 * @Version 1.0
 */
object AnimKTypeUtil {
    /**
     * 背景变换
     */
    @JvmStatic
    fun get_ofBackgroundColor(view: View, @ColorInt intColorStart: Int, @ColorInt intColorEnd: Int): AnimatorColorRecyclerType {
        val colorDrawable = ColorDrawable(intColorStart)
        val animatorColorRecyclerType = AnimatorColorRecyclerType().setColorRange(intColorStart, intColorEnd).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    colorDrawable.color = value
                    UtilKViewCompat.applyBackground(view, colorDrawable)
                }
            }
        })
        return animatorColorRecyclerType as AnimatorColorRecyclerType
    }

    /**
     * 背景透明度变换
     */
    @JvmStatic
    fun get_ofBackgroundAlpha(view: View, @FloatRange(from = 0.0, to = 1.0) alphaEnd: Float, @FloatRange(from = 0.0, to = 1.0) alphaStart: Float = 1f): AnimatorAlpha255Type {
        val alphaDrawable: Drawable = view.background
        val animatorAlpha255Type = AnimatorAlpha255Type().setAlpha(alphaStart, alphaEnd).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    alphaDrawable.alpha = value
                    UtilKViewCompat.applyBackground(view, alphaDrawable)
                }
            }
        })
        return animatorAlpha255Type as AnimatorAlpha255Type
    }

    /**
     * 旋转
     */
    @JvmStatic
    fun get_ofRotate(view: View, @FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimatorRotationType =
        AnimatorRotationType().rotate(from, to).setInterpolator(DecelerateInterpolator()).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Float> {
            override fun onChange(value: Float?) {
                value?.let {
                    view.rotation = it
                }
            }
        }) as AnimatorRotationType

    /**
     * 高度
     * //真正实现具体展开动画的方法，使用ValueAnimator.ofInt生成一系列高度值，然后添加上面的监听
     * //监听动画的变化，不断设定view的高度值
     */
    @JvmStatic
    fun get_ofHeight(view: View, startHeight: Int, endHeight: Int): AnimatorIntType =
        AnimatorIntType().setInt(startHeight, endHeight)
            .addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
                override fun onChange(value: Int?) {
                    value?.let {
                        val lp = view.layoutParams
                        lp.height = it
                        view.layoutParams = lp
                    }
                }
            })
            .addAnimatorListener(BaseLayoutParamsAnimatorListenerAdapter(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

    @JvmStatic
    fun get_ofHeight_viewHolder(view: View, viewHolder: ViewHolder, startHeight: Int, endHeight: Int): AnimatorIntType =
        get_ofHeight(view, startHeight, endHeight)
            .addAnimatorListener(BaseViewHolderAnimatorListenerAdapter(viewHolder))//设定该Item在动画开始结束和取消时能否被recycle

    @JvmStatic
    fun get_ofHeight_viewHolder(viewHolder: ViewHolder): AnimatorIntType {
        val parent = viewHolder.itemView.parent as View
        val startHeight = viewHolder.itemView.measuredHeight//测量扩展动画的起始高度和结束高度
        viewHolder.itemView.measure(
            View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val endHeight = viewHolder.itemView.measuredHeight
        return get_ofHeight_viewHolder(viewHolder.itemView, viewHolder, startHeight, endHeight)
    }

    @JvmStatic
    fun get_ofAlphaView(view: View, @FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimatorAlphaViewType =
        AnimatorAlphaViewType().setViewRef(view).setAlpha(fromAlpha, toAlpha)

    @JvmStatic
    fun get_ofMarginVertical(view: View, marginHorizontal: Int, fromMargin: Int, toMargin: Int): AnimatorIntType =
        AnimatorIntType().setInt(fromMargin, toMargin).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    view.applyMargin(it, marginHorizontal)
                }
            }
        })
}