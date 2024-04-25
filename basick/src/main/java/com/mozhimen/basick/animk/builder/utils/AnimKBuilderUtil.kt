package com.mozhimen.basick.animk.builder.utils

import android.animation.Animator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.helpers.AnimatorBuilder
import com.mozhimen.basick.animk.builder.impls.AnimatorAlpha255Type
import com.mozhimen.basick.animk.builder.impls.AnimatorColorRecyclerType
import com.mozhimen.basick.animk.builder.impls.AnimatorIntType
import com.mozhimen.basick.animk.builder.impls.AnimatorRotationType
import com.mozhimen.basick.utilk.androidx.core.UtilKViewCompat

/**
 * @ClassName AnimKBuilderUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/24
 * @Version 1.0
 */
object AnimKBuilderUtil {
    /**
     * 背景变换
     */
    @JvmStatic
    fun get_ofBackgroundColor(view: View, @ColorInt intColorStart: Int, @ColorInt intColorEnd: Int): AnimatorBuilder {
        val colorDrawable = ColorDrawable(intColorStart)
        val animatorBuilder = AnimKBuilder.asAnimator().add(
            AnimatorColorRecyclerType().setColorRange(intColorStart, intColorEnd).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
                override fun onChange(value: Int?) {
                    value?.let {
                        colorDrawable.color = value
                        UtilKViewCompat.applyBackground(view, colorDrawable)
                    }
                }
            })
        )
        return animatorBuilder
    }

    /**
     * 背景透明度变换
     */
    @JvmStatic
    fun get_ofBackgroundAlpha(view: View, @FloatRange(from = 0.0, to = 1.0) alphaEnd: Float, @FloatRange(from = 0.0, to = 1.0) alphaStart: Float = 1f): AnimatorBuilder {
        val alphaDrawable: Drawable = view.background
        val animatorBuilder = AnimKBuilder.asAnimator().add(AnimatorAlpha255Type().addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    alphaDrawable.alpha = value
                    UtilKViewCompat.applyBackground(view, alphaDrawable)
                }
            }
        }).setAlpha(alphaStart, alphaEnd))
        return animatorBuilder
    }

    /**
     * 旋转
     */
    @JvmStatic
    fun get_ofRotate(view: View, @FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): AnimatorBuilder =
        AnimKBuilder.asAnimator().add(AnimatorRotationType().rotate(from, to).setInterpolator(DecelerateInterpolator()).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Float> {
            override fun onChange(value: Float?) {
                value?.let {
                    view.rotation = it
                }
            }
        }))

    /**
     * 高度
     * //真正实现具体展开动画的方法，使用ValueAnimator.ofInt生成一系列高度值，然后添加上面的监听
     * //监听动画的变化，不断设定view的高度值
     */
    @JvmStatic
    fun get_ofHeight(view: View, start: Int, end: Int): AnimatorBuilder =
        AnimKBuilder.asAnimator().add(AnimKTypeUtil.get_ofHeight(view, start, end))
}