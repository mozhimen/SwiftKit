package com.mozhimen.uicorek.searchk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R

internal object SearchKAttrsParser {
    fun parseSearchViewAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): SearchKAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.SearchKLayout_SearchKLayoutStyle, value, true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.SearchKLayoutStyle

        val array = context.obtainStyledAttributes(attrs, R.styleable.SearchKLayout, defStyleAttr, defStyleRes)

        //search icon
        val searchIcon = array.getString(
            R.styleable.SearchKLayout_searchKLayout_search_icon
        )
        val searchIconSize = array.getDimensionPixelSize(
            R.styleable.SearchKLayout_searchKLayout_search_iconSize, 16f.sp2px()
        )
        val searchIconPadding = array.getDimensionPixelOffset(
            R.styleable.SearchKLayout_searchKLayout_search_iconPadding, 4f.sp2px()
        )
        val searchBackground = array.getDrawable(
            R.styleable.SearchKLayout_searchKLayout_search_background
        ) ?: UtilKRes.getDrawable(R.drawable.searchk_search_background)
        val searchTextSize = array.getDimensionPixelSize(
            R.styleable.SearchKLayout_searchKLayout_search_textSize, 16f.sp2px()
        )
        val searchTextColor = array.getColor(
            R.styleable.SearchKLayout_searchKLayout_search_textColor, UtilKRes.getColor(android.R.color.black)
        )

        //clear icon
        val clearIcon = array.getString(
            R.styleable.SearchKLayout_searchKLayout_clear_icon
        )
        val clearIconSize = array.getDimensionPixelSize(
            R.styleable.SearchKLayout_searchKLayout_clear_iconSize, 16f.sp2px()
        )

        //hint
        val hintText = array.getString(
            R.styleable.SearchKLayout_searchKLayout_hint_text
        )
        val hintTextSize = array.getDimensionPixelSize(
            R.styleable.SearchKLayout_searchKLayout_hint_textSize, 16f.sp2px()
        )
        val hintTextColor = array.getColor(
            R.styleable.SearchKLayout_searchKLayout_hint_textColor, UtilKRes.getColor(android.R.color.black)
        )
        val hintGravity = array.getInteger(
            R.styleable.SearchKLayout_searchKLayout_hint_gravity, 1
        )

        //keyword
        val keywordIcon = array.getString(
            R.styleable.SearchKLayout_searchKLayout_keyword_icon
        )
        val keywordIconSize = array.getDimensionPixelSize(
            R.styleable.SearchKLayout_searchKLayout_keyword_iconSize, 13f.sp2px()
        )
        val keywordIconColor = array.getColor(
            R.styleable.SearchKLayout_searchKLayout_keyword_iconColor, Color.WHITE
        )
        val keywordBackground = array.getDrawable(
            R.styleable.SearchKLayout_searchKLayout_keyword_background
        )
        val keywordMaxLength = array.getInteger(
            R.styleable.SearchKLayout_searchKLayout_keyword_maxLength, 10
        )
        val keywordPadding = array.getDimensionPixelOffset(
            R.styleable.SearchKLayout_searchKLayout_keyword_padding, 12f.sp2px()
        )

        array.recycle()

        return SearchKAttrs(
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