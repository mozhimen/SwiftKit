package com.mozhimen.uicorek.sliderk

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.uicorek.R
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.sliderk.mos.SliderKAttrs

/**
 * @ClassName SliderKAttrsParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:32
 * @Version 1.0
 */
internal object SliderKAttrsParser {
    private val MENU_WIDTH = 100f.dp2px()
    private val MENU_HEIGHT = 45f.dp2px()
    private val MENU_ITEM_TEXT_SIZE = 14f.sp2px()
    private val MENU_ITEM_TEXT_SIZE_SELECT = 16f.sp2px()

    private val MENU_ITEM_TEXT_COLOR = UtilKRes.getColor(R.color.blue_normal)
    private val MENU_ITEM_TEXT_COLOR_SELECT = UtilKRes.getColor(R.color.blue_dark)
    private val MENU_ITEM_BG_COLOR = UtilKRes.getColor(android.R.color.white)
    private val MENU_ITEM_BG_COLOR_SELECT = UtilKRes.getColor(android.R.color.holo_blue_light)
    private val MENU_ITEM_INDICATOR = UtilKRes.getDrawable(R.drawable.sliderk_indicator_menu)

    fun parseMenuAttr(context: Context, attrs: AttributeSet?): SliderKAttrs {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderKLayout)
            val menuWidth =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuWidth, MENU_WIDTH)
            val menuHeight =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuHeight, MENU_HEIGHT)
            val menuItemTextSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemTextSize, MENU_ITEM_TEXT_SIZE)
            val menuItemTextSizeSelect =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemTextSizeSelect, MENU_ITEM_TEXT_SIZE_SELECT)
            val menuItemTextColor =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemTextColor, UtilKRes.getColor(R.color.blue_normal))
            val menuItemTextColorSelect =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemTextColor, UtilKRes.getColor(R.color.blue_dark))
            val menuItemBgColor =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemBackgroundColor, MENU_ITEM_BG_COLOR)
            val menuItemBgColorSelect =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemBackgroundColorSelect, MENU_ITEM_BG_COLOR_SELECT)
            val menuItemIndicator =
                typedArray.getDrawable(R.styleable.SliderKLayout_sliderKLayout_menuItemIndicator) ?: MENU_ITEM_INDICATOR
            typedArray.recycle()

            return SliderKAttrs(
                menuWidth,
                menuHeight,
                menuItemTextSize,
                menuItemTextSizeSelect,
                menuItemTextColor,
                menuItemTextColorSelect,
                menuItemBgColor,
                menuItemBgColorSelect,
                menuItemIndicator
            )
        } ?: return SliderKAttrs(
            MENU_WIDTH,
            MENU_HEIGHT,
            MENU_ITEM_TEXT_SIZE,
            MENU_ITEM_TEXT_SIZE_SELECT,
            MENU_ITEM_TEXT_COLOR,
            MENU_ITEM_TEXT_COLOR_SELECT,
            MENU_ITEM_BG_COLOR_SELECT,
            MENU_ITEM_BG_COLOR,
            MENU_ITEM_INDICATOR
        )
    }

    /*private fun generateColorStateList(): ColorStateList {
        val states = Array(2) { intArrayOf(android.R.attr.state_selected) }
        val colors = intArrayOf(TEXT_COLOR_SELECT, TEXT_COLOR_NORMAL)

        return ColorStateList(states, colors)
    }*/
}