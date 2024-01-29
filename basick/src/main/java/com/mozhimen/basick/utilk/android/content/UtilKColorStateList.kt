package com.mozhimen.basick.utilk.android.content

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

/**
 * @ClassName UtilKColorStateList
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/26 14:30
 * @Version 1.0
 */
object UtilKColorStateList {
    @JvmStatic
    fun get(@ColorInt intColorNormal: Int, @ColorInt intColorPressed: Int): ColorStateList {
        val states = arrayOfNulls<IntArray>(2)//状态
        states[0] = intArrayOf(android.R.attr.state_pressed)//按下
        states[1] = intArrayOf()//默认
        val colors = intArrayOf(intColorPressed, intColorNormal)//状态对应颜色值（按下，默认）
        return ColorStateList(states, colors)
    }
}