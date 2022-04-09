package com.mozhimen.uicorek.sliderk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.uicorek.R
import com.mozhimen.basicsk.utilk.UtilKRes
import com.mozhimen.basicsk.utilk.dp2px
import com.mozhimen.basicsk.utilk.sp2px

/**
 * @ClassName SliderKParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:32
 * @Version 1.0
 */
internal object SliderKParser {
    private var MENU_WIDTH = 100f.dp2px()
    private var MENU_HEIGHT = 45f.dp2px()
    private var MENU_TEXT_SIZE = 14f.sp2px()
    private var MENU_TEXT_SIZE_SELECT = 16f.sp2px()

    private var TEXT_COLOR_NORMAL = UtilKRes.getColor(R.color.blue_normal)
    private var TEXT_COLOR_SELECT = UtilKRes.getColor(R.color.blue_dark)
    private var BG_COLOR_NORMAL = UtilKRes.getColor(android.R.color.white)
    private var BG_COLOR_SELECT = UtilKRes.getColor(R.color.blue_light)

    fun parseMenuAttr(context: Context, attrs: AttributeSet?): MenuItemAttrs {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderKLayout)
            val itemWidth =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemWidth, MENU_WIDTH)
            val itemHeight =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemHeight, MENU_HEIGHT)
            val itemTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemTextSize, MENU_TEXT_SIZE)
            val itemSelectTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemSelectTextSize, MENU_TEXT_SIZE_SELECT)
            val textColors = typedArray.getColorStateList(R.styleable.SliderKLayout_sliderKLayout_menuItemTextColor)
                ?: generateColorStateList()
            val indicator = typedArray.getDrawable(R.styleable.SliderKLayout_sliderKLayout_menuItemIndicator)
                ?: UtilKRes.getDrawable(R.drawable.sliderk_indicator)
            val bgColorNormal =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemBackgroundColor, BG_COLOR_NORMAL)
            val bgColorSelect =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemSelectBackgroundColor, BG_COLOR_SELECT)
            typedArray.recycle()

            return MenuItemAttrs(
                itemWidth,
                itemHeight,
                textColors,
                bgColorSelect,
                bgColorNormal,
                itemTextSize,
                itemSelectTextSize,
                indicator
            )
        } ?: return MenuItemAttrs(
            MENU_WIDTH,
            MENU_HEIGHT,
            generateColorStateList(),
            BG_COLOR_SELECT,
            BG_COLOR_NORMAL,
            MENU_TEXT_SIZE,
            MENU_TEXT_SIZE_SELECT,
            UtilKRes.getDrawable(R.drawable.sliderk_indicator)
        )
    }

    private fun generateColorStateList(): ColorStateList {
        val states = Array(2) { intArrayOf(android.R.attr.state_selected) }
        val colors = intArrayOf(TEXT_COLOR_SELECT, TEXT_COLOR_NORMAL)

        return ColorStateList(states, colors)
    }

    data class MenuItemAttrs(
        val width: Int, val height: Int, val textColor: ColorStateList, val selectBackgroundColor: Int, val normalBackgroundColor: Int,
        val textSize: Int, val selectTextSize: Int, val indicator: Drawable?
    )
}