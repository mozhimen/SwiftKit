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
import com.mozhimen.basick.animk.builder.impls.AnimatorAlpha255Type
import com.mozhimen.basick.animk.builder.impls.AnimatorColorRecyclerType
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
    fun get_ofBackgroundColor(view: View, @ColorInt intColorStart: Int, @ColorInt intColorEnd: Int): Animator {
        val colorDrawable = ColorDrawable(intColorStart)
        val colorAnimator = AnimKBuilder.asAnimator().add(
            AnimatorColorRecyclerType().setColorRange(intColorStart, intColorEnd).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
                override fun onChange(value: Int?) {
                    value?.let {
                        colorDrawable.color = value
                        UtilKViewCompat.applyBackground(view, colorDrawable)
                    }
                }
            })
        ).build()
        return colorAnimator
    }

    /**
     * 背景透明度变换
     */
    @JvmStatic
    fun get_ofBackgroundAlpha(view: View, @FloatRange(from = 0.0, to = 1.0) alphaEnd: Float, @FloatRange(from = 0.0, to = 1.0) alphaStart: Float = 1f): Animator {
        val alphaDrawable: Drawable = view.background
        val alphaAnimator = AnimKBuilder.asAnimator().add(AnimatorAlpha255Type().addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    alphaDrawable.alpha = value
                    UtilKViewCompat.applyBackground(view, alphaDrawable)
                }
            }
        }).setAlpha(alphaStart, alphaEnd)).build()
        return alphaAnimator
    }

    @JvmStatic
    fun get_ofRotate(view: View, @FloatRange(from = 0.0, to = 360.0) from: Float, @FloatRange(from = 0.0, to = 360.0) to: Float): Animator {
        val rotateAnimator =
            AnimKBuilder.asAnimator().add(AnimatorRotationType().rotate(from, to).setInterpolator(DecelerateInterpolator()).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Float> {
                override fun onChange(value: Float?) {
                    value?.let {
                        view.rotation = it
                    }
                }
            })).build()
        return rotateAnimator
    }
}