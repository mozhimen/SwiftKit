package com.mozhimen.uicorek.sliderk.mos

import android.graphics.drawable.Drawable

/**
 * @ClassName SliderKAttrs
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/14 16:37
 * @Version 1.0
 */
data class SliderKAttrs(
    val menuWidth: Int,
    val menuHeight: Int,
    val menuItemTextSize: Int,
    val menuItemTextSizeSelect: Int,
    val menuItemTextColor: Int,
    val menuItemTextColorSelect: Int,
    val menuItemBgColor: Int,
    val menuItemBgColorSelect: Int,
    val menuItemIndicator: Drawable?
)
