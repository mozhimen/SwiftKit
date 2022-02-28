package com.mozhimen.uicoremk.slidermk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.uicoremk.R
import com.mozhimen.basicsmk.utilmk.UtilMKRes
import com.mozhimen.uicoremk.utilmk.dp2px
import com.mozhimen.uicoremk.utilmk.sp2px

/**
 * @ClassName SliderMKParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:32
 * @Version 1.0
 */
internal object SliderMKParser {
    private var MENU_WIDTH = 100f.dp2px()
    private var MENU_HEIGHT = 45f.dp2px()
    private var MENU_TEXT_SIZE = 14f.sp2px()
    private var MENU_TEXT_SIZE_SELECT = 16f.sp2px()

    private var TEXT_COLOR_NORMAL = UtilMKRes.getColor(R.color.blue_normal)
    private var TEXT_COLOR_SELECT = UtilMKRes.getColor(R.color.blue_dark)
    private var BG_COLOR_NORMAL = UtilMKRes.getColor(android.R.color.white)
    private var BG_COLOR_SELECT = UtilMKRes.getColor(R.color.blue_light)

    fun parseMenuAttr(context: Context, attrs: AttributeSet?): MenuItemAttrs {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderMKLayout)
            val itemWidth =
                typedArray.getDimensionPixelOffset(R.styleable.SliderMKLayout_sliderMKLayout_menuItemWidth, MENU_WIDTH)
            val itemHeight =
                typedArray.getDimensionPixelOffset(R.styleable.SliderMKLayout_sliderMKLayout_menuItemHeight, MENU_HEIGHT)
            val itemTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderMKLayout_sliderMKLayout_menuItemTextSize, MENU_TEXT_SIZE)
            val itemSelectTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderMKLayout_sliderMKLayout_menuItemSelectTextSize, MENU_TEXT_SIZE_SELECT)
            val textColors = typedArray.getColorStateList(R.styleable.SliderMKLayout_sliderMKLayout_menuItemTextColor)
                ?: generateColorStateList()
            val indicator = typedArray.getDrawable(R.styleable.SliderMKLayout_sliderMKLayout_menuItemIndicator)
                ?: UtilMKRes.getDrawable(R.drawable.slidermk_indicator)
            val bgColorNormal =
                typedArray.getColor(R.styleable.SliderMKLayout_sliderMKLayout_menuItemBackgroundColor, BG_COLOR_NORMAL)
            val bgColorSelect =
                typedArray.getColor(R.styleable.SliderMKLayout_sliderMKLayout_menuItemSelectBackgroundColor, BG_COLOR_SELECT)
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
            UtilMKRes.getDrawable(R.drawable.slidermk_indicator)
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