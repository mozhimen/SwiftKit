package com.mozhimen.uicorek.layoutk.roll

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.AnimationSet
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.annotation.Px
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.temps.AnimatorFloatType
import com.mozhimen.basick.lintk.annors.ADigitPlace
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import com.mozhimen.uicorek.layoutk.roll.annors.AAnimatorMode

/**
 * @ClassName LayoutKRollText
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/12 12:48
 * @Version 1.0
 */

class LayoutKRollDigit @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutKRollText(context, attrs, defStyleAttr) {

    companion object {
        private const val DELAY_DURATION = 200L
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置下一位数字的值
     * @param digitPlace String 个，十，百位
     * @param value Int
     * @param mode Int
     */
    fun setCurrentValue(value: Int, @ADigitPlace digitPlace: Int = ADigitPlace.PLACE_UNIT, @AAnimatorMode mode: Int = AAnimatorMode.DOWN) {
        //判断数字是增加还是减少，进而确定不同的动画效果
        setCurrentValue(value.toString(), getAnimatorDelay(digitPlace), mode)
    }

    /**
     * 计算对应位置的延迟时间
     */
    private fun getAnimatorDelay(@ADigitPlace digitPlace: Int): Long {
        return when (digitPlace) {
            ADigitPlace.PLACE_UNIT -> DELAY_DURATION
            ADigitPlace.PLACE_HUNDRED -> DELAY_DURATION + 100
            ADigitPlace.PLACE_THOUSAND -> DELAY_DURATION * 2
            ADigitPlace.PLACE_TEN_THOUSAND -> DELAY_DURATION * 2 + 100
            ADigitPlace.PLACE_HUNDRED_THOUSAND -> DELAY_DURATION * 3
            else -> DELAY_DURATION * 3 + 100
        }
    }
}