package com.mozhimen.uicorek.sliderk.helpers

import android.content.Context
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.uicorek.R
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.sliderk.mos.SliderKSubAttrs

/**
 * @ClassName SliderKSubAttrsParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:32
 * @Version 1.0
 */
internal object SliderKSubAttrsParser {
    private val MENU_WIDTH = 90f.dp2px()
    private val MENU_HEIGHT = 45f.dp2px()
    private val MENU_ITEM_TEXT_SIZE = 16f.sp2px()
    private val MENU_ITEM_TEXT_SIZE_SELECT = 17f.sp2px()

    private val MENU_ITEM_TEXT_COLOR = UtilKRes.getColor(R.color.blue_normal)
    private val MENU_ITEM_TEXT_COLOR_SELECT = UtilKRes.getColor(R.color.blue_dark)
    private val MENU_ITEM_BG_COLOR = UtilKRes.getColor(android.R.color.white)
    private val MENU_ITEM_BG_COLOR_SELECT = UtilKRes.getColor(R.color.blue_light)
    private val MENU_ITEM_INDICATOR = UtilKRes.getDrawable(R.drawable.sliderk_indicator_menu)

    private val SUB_TEXT_SIZE = 16f.sp2px()
    private val SUB_TEXT_COLOR = UtilKRes.getColor(R.color.blue_normal)
    private val SUB_HEIGHT = 40f.dp2px()
    private val SUB_MARGIN_START = 10f.dp2px()

    private val CONTENT_TEXT_SIZE = 15f.sp2px()
    private val CONTENT_TEXT_COLOR = UtilKRes.getColor(R.color.blue_normal)
    private const val CONTENT_IMG_RATIO = 1f

    fun parseMenuAttr(context: Context, attrs: AttributeSet?): SliderKSubAttrs {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderKSubLayout)
            val menuWidth =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKSubLayout_sliderKSubLayout_menuWidth, MENU_WIDTH)
            val menuHeight =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKSubLayout_sliderKSubLayout_menuHeight, MENU_HEIGHT)
            val menuItemTextSize =
                typedArray.getDimensionPixelSize(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemTextSize, MENU_ITEM_TEXT_SIZE)
            val menuItemTextSizeSelect =
                typedArray.getDimensionPixelSize(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemTextSizeSelect, MENU_ITEM_TEXT_SIZE_SELECT)
            val menuItemTextColor =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemTextColor, MENU_ITEM_TEXT_COLOR)
            val menuItemTextColorSelect =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemTextColor, MENU_ITEM_TEXT_COLOR_SELECT)
            val menuItemBgColor =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemBackgroundColor, MENU_ITEM_BG_COLOR)
            val menuItemBgColorSelect =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemBackgroundColorSelect, MENU_ITEM_BG_COLOR_SELECT)
            val menuItemIndicator =
                typedArray.getDrawable(R.styleable.SliderKSubLayout_sliderKSubLayout_menuItemIndicator) ?: MENU_ITEM_INDICATOR
            val subTextSize =
                typedArray.getDimensionPixelSize(R.styleable.SliderKSubLayout_sliderKSubLayout_subTextSize, SUB_TEXT_SIZE)
            val subTextColor =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_subTextColor, SUB_TEXT_COLOR)
            val subHeight =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKSubLayout_sliderKSubLayout_subHeight, SUB_HEIGHT)
            val subMarginStart =
                typedArray.getDimensionPixelOffset(R.styleable.SliderKSubLayout_sliderKSubLayout_subMarginStart, SUB_MARGIN_START)
            val contentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.SliderKSubLayout_sliderKSubLayout_subTextSize, CONTENT_TEXT_SIZE)
            val contentTextColor =
                typedArray.getColor(R.styleable.SliderKSubLayout_sliderKSubLayout_contentTextColor, CONTENT_TEXT_COLOR)
            val contentImgRatio =
                when (typedArray.getInteger(R.styleable.SliderKSubLayout_sliderKSubLayout_contentImgRatio, 0)) {
                    0 -> 1f
                    1 -> 4f / 3f
                    else -> 16f / 9f
                }
            typedArray.recycle()

            return SliderKSubAttrs(
                menuWidth,
                menuHeight,
                menuItemTextSize,
                menuItemTextSizeSelect,
                menuItemTextColor,
                menuItemTextColorSelect,
                menuItemBgColor,
                menuItemBgColorSelect,
                menuItemIndicator,
                subTextSize,
                subTextColor,
                subHeight,
                subMarginStart,
                contentTextSize,
                contentTextColor,
                contentImgRatio
            )
        } ?: return SliderKSubAttrs(
            MENU_WIDTH,
            MENU_HEIGHT,
            MENU_ITEM_TEXT_SIZE,
            MENU_ITEM_TEXT_SIZE_SELECT,
            MENU_ITEM_TEXT_COLOR,
            MENU_ITEM_TEXT_COLOR_SELECT,
            MENU_ITEM_BG_COLOR_SELECT,
            MENU_ITEM_BG_COLOR,
            MENU_ITEM_INDICATOR,
            SUB_TEXT_SIZE,
            SUB_TEXT_COLOR,
            SUB_HEIGHT,
            SUB_MARGIN_START,
            CONTENT_TEXT_SIZE,
            CONTENT_TEXT_COLOR,
            CONTENT_IMG_RATIO
        )
    }

    /*private fun generateColorStateList(): ColorStateList {
        val states = Array(2) { intArrayOf(android.R.attr.state_selected) }
        val colors = intArrayOf(TEXT_COLOR_SELECT, TEXT_COLOR_NORMAL)

        return ColorStateList(states, colors)
    }*/
}