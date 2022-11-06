package com.mozhimen.uicorek.layoutk.search.helpers

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.search.mos.MSearchAttrs

internal object AttrsParser {
    fun parseSearchViewAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): MSearchAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.LayoutKSearch_LayoutKSearchStyle, value, true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.LayoutKSearchStyle

        val array = context.obtainStyledAttributes(attrs, R.styleable.LayoutKSearch, defStyleAttr, defStyleRes)

        //search icon
        val searchIcon = array.getString(
            R.styleable.LayoutKSearch_layoutKSearch_search_icon
        )
        val searchIconSize = array.getDimensionPixelSize(
            R.styleable.LayoutKSearch_layoutKSearch_search_iconSize, 15f.sp2px()
        )
        val searchIconPadding = array.getDimensionPixelOffset(
            R.styleable.LayoutKSearch_layoutKSearch_search_iconPadding, 4f.sp2px()
        )
        val searchBackground = array.getDrawable(
            R.styleable.LayoutKSearch_layoutKSearch_search_background
        ) ?: UtilKRes.getDrawable(R.drawable.layoutk_search_background)
        val searchTextSize = array.getDimensionPixelSize(
            R.styleable.LayoutKSearch_layoutKSearch_search_textSize, 15f.sp2px()
        )
        val searchTextColor = array.getColor(
            R.styleable.LayoutKSearch_layoutKSearch_search_textColor, UtilKRes.getColor(android.R.color.black)
        )

        //clear icon
        val clearIcon = array.getString(
            R.styleable.LayoutKSearch_layoutKSearch_clear_icon
        )
        val clearIconSize = array.getDimensionPixelSize(
            R.styleable.LayoutKSearch_layoutKSearch_clear_iconSize, 15f.sp2px()
        )

        //hint
        val hintText = array.getString(
            R.styleable.LayoutKSearch_layoutKSearch_hint_text
        )
        val hintTextSize = array.getDimensionPixelSize(
            R.styleable.LayoutKSearch_layoutKSearch_hint_textSize, 15f.sp2px()
        )
        val hintTextColor = array.getColor(
            R.styleable.LayoutKSearch_layoutKSearch_hint_textColor, UtilKRes.getColor(android.R.color.black)
        )
        val hintGravity = array.getInteger(
            R.styleable.LayoutKSearch_layoutKSearch_hint_gravity, 1
        )

        //keyword
        val keywordIcon = array.getString(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_icon
        )
        val keywordIconSize = array.getDimensionPixelSize(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_iconSize, 13f.sp2px()
        )
        val keywordIconColor = array.getColor(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_iconColor, Color.WHITE
        )
        val keywordBackground = array.getDrawable(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_background
        )
        val keywordMaxLength = array.getInteger(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_maxLength, 10
        )
        val keywordPadding = array.getDimensionPixelOffset(
            R.styleable.LayoutKSearch_layoutKSearch_keyword_padding, 12f.sp2px()
        )

        array.recycle()

        return MSearchAttrs(
            searchIcon,
            searchIconSize.toFloat(),
            searchIconPadding,
            searchBackground,
            searchTextSize.toFloat(),
            searchTextColor,
            clearIcon,
            clearIconSize.toFloat(),
            hintText,
            hintTextSize.toFloat(),
            hintTextColor,
            hintGravity,
            keywordIcon,
            keywordIconSize.toFloat(),
            keywordIconColor,
            keywordBackground,
            keywordMaxLength,
            keywordPadding.toFloat(),
        )
    }
}