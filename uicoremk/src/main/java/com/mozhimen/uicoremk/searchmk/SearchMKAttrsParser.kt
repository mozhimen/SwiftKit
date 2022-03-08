package com.mozhimen.uicoremk.searchmk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basicsmk.utilmk.UtilMKRes
import com.mozhimen.basicsmk.utilmk.sp2px
import com.mozhimen.uicoremk.R

internal object SearchMKAttrsParser {
    fun parseSearchViewAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): SearchMKAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.SearchMKLayout_SearchMKLayoutStyle, value, true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.SearchMKLayoutStyle

        val array = context.obtainStyledAttributes(attrs, R.styleable.SearchMKLayout, defStyleAttr, defStyleRes)

        //search icon
        val searchIcon = array.getString(
            R.styleable.SearchMKLayout_searchMKLayout_search_icon
        )
        val searchIconSize = array.getDimensionPixelSize(
            R.styleable.SearchMKLayout_searchMKLayout_search_iconSize, 16f.sp2px()
        )
        val searchIconPadding = array.getDimensionPixelOffset(
            R.styleable.SearchMKLayout_searchMKLayout_search_iconPadding, 4f.sp2px()
        )
        val searchBackground = array.getDrawable(
            R.styleable.SearchMKLayout_searchMKLayout_search_background
        ) ?: UtilMKRes.getDrawable(R.drawable.searchmk_search_background)
        val searchTextSize = array.getDimensionPixelSize(
            R.styleable.SearchMKLayout_searchMKLayout_search_textSize, 16f.sp2px()
        )
        val searchTextColor = array.getColor(
            R.styleable.SearchMKLayout_searchMKLayout_search_textColor, UtilMKRes.getColor(android.R.color.black)
        )

        //clear icon
        val clearIcon = array.getString(
            R.styleable.SearchMKLayout_searchMKLayout_clear_icon
        )
        val clearIconSize = array.getDimensionPixelSize(
            R.styleable.SearchMKLayout_searchMKLayout_clear_iconSize, 16f.sp2px()
        )

        //hint
        val hintText = array.getString(
            R.styleable.SearchMKLayout_searchMKLayout_hint_text
        )
        val hintTextSize = array.getDimensionPixelSize(
            R.styleable.SearchMKLayout_searchMKLayout_hint_textSize, 16f.sp2px()
        )
        val hintTextColor = array.getColor(
            R.styleable.SearchMKLayout_searchMKLayout_hint_textColor, UtilMKRes.getColor(android.R.color.black)
        )
        val hintGravity = array.getInteger(
            R.styleable.SearchMKLayout_searchMKLayout_hint_gravity, 1
        )

        //keyword
        val keywordIcon = array.getString(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_icon
        )
        val keywordIconSize = array.getDimensionPixelSize(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_iconSize, 13f.sp2px()
        )
        val keywordIconColor = array.getColor(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_iconColor, Color.WHITE
        )
        val keywordBackground = array.getDrawable(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_background
        )
        val keywordMaxLength = array.getInteger(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_maxLength, 10
        )
        val keywordPadding = array.getDimensionPixelOffset(
            R.styleable.SearchMKLayout_searchMKLayout_keyword_padding, 12f.sp2px()
        )

        array.recycle()

        return SearchMKAttrs(
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