package com.mozhimen.uicorek.sliderk

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.uicorek.R
import com.mozhimen.basick.utilk.UtilKRes

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
    private var BG_COLOR_SELECT = UtilKRes.getColor(android.R.color.holo_blue_light)

    fun parseMenuAttr(context: Context, attrs: AttributeSet?): SliderKMenuItemAttrs {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderKLayout)
            val width =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuWidth, MENU_WIDTH)
            val height =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuHeight, MENU_HEIGHT)
            val textSize =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemTextSize, MENU_TEXT_SIZE)
            val textSizeSelect =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKLayout_sliderKLayout_menuItemTextSizeSelect, MENU_TEXT_SIZE_SELECT)
            val textColor = typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemTextColor, UtilKRes.getColor(R.color.blue_normal))
            val textColorSelect = typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemTextColor, UtilKRes.getColor(R.color.blue_dark))
            val bgColor =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemBackgroundColor, BG_COLOR_NORMAL)
            val bgColorSelect =
                typedArray.getColor(R.styleable.SliderKLayout_sliderKLayout_menuItemBackgroundColorSelect, BG_COLOR_SELECT)
            typedArray.recycle()
            val indicator = typedArray.getDrawable(R.styleable.SliderKLayout_sliderKLayout_menuItemIndicator)
                ?: UtilKRes.getDrawable(R.drawable.sliderk_indicator_menu)

            return SliderKMenuItemAttrs(
                width,
                height,
                textSize,
                textSizeSelect,
                textColor,
                textColorSelect,
                bgColor,
                bgColorSelect,
                indicator
            )
        } ?: return SliderKMenuItemAttrs(
            MENU_WIDTH,
            MENU_HEIGHT,
            MENU_TEXT_SIZE,
            MENU_TEXT_SIZE_SELECT,
            TEXT_COLOR_NORMAL,
            TEXT_COLOR_SELECT,
            BG_COLOR_SELECT,
            BG_COLOR_NORMAL,
            UtilKRes.getDrawable(R.drawable.sliderk_indicator_menu)
        )
    }

    /*private fun generateColorStateList(): ColorStateList {
        val states = Array(2) { intArrayOf(android.R.attr.state_selected) }
        val colors = intArrayOf(TEXT_COLOR_SELECT, TEXT_COLOR_NORMAL)

        return ColorStateList(states, colors)
    }*/

    data class SliderKMenuItemAttrs(
        val width: Int,
        val height: Int,
        val textSize: Int,
        val textSizeSelect: Int,
        val textColor: Int,
        val textColorSelect: Int,
        val bgColor: Int,
        val bgColorSelect: Int,
        val indicator: Drawable?,
    )
}