package com.mozhimen.uicoremk.searchmk

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.uicoremk.R

internal object SearchMKAttrsParser {
    fun parseSearchViewAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.SearchMKViewStyle, value, true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.SearchMKViewStyle

        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.SearchMKView,
            defStyleAttr,
            defStyleRes
        )

        //搜索背景
        val searchBackground = array.getDrawable(R.styleable.HiSearchView_search_background)
            ?: HiRes.getDrawable(R.drawable.shape_search_view)
        //搜索图标
        val searchIcon = array.getString(R.styleable.HiSearchView_search_icon)
        val searchIconSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_search_icon_size,
            HiDisplayUtil.sp2px(16f)
        )
        val iconPadding = array.getDimensionPixelOffset(
            R.styleable.HiSearchView_icon_padding,
            HiDisplayUtil.sp2px(4f)
        )

        //清除按钮
        val clearIcon = array.getString(R.styleable.HiSearchView_clear_icon)
        val clearIconSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_clear_icon_size,
            HiDisplayUtil.sp2px(16f)
        )

        //提示语
        val hintText = array.getString(R.styleable.HiSearchView_hint_text)
        val hintTextSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_hint_text_size,
            HiDisplayUtil.sp2px(16f)
        )
        val hintTextColor = array.getColor(
            R.styleable.HiSearchView_hint_text_color,
            HiRes.getColor(R.color.hi_tabtop_normal_color)
        )
        //相对位置
        val gravity = array.getInteger(R.styleable.HiSearchView_hint_gravity, 1)

        //输入文本
        val searchTextSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_search_text_size,
            HiDisplayUtil.sp2px(16f)
        )
        val searchTextColor = array.getColor(
            R.styleable.HiSearchView_search_text_color,
            HiRes.getColor(R.color.hi_tabtop_normal_color)
        )

        //keyword关键词
        val keywordSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_key_word_size,
            HiDisplayUtil.sp2px(13f)
        )
        val keywordColor = array.getColor(R.styleable.HiSearchView_key_word_color, Color.WHITE)
        val keywordMaxLen = array.getInteger(R.styleable.HiSearchView_key_word_max_length, 10)
        val keywordBackground = array.getDrawable(R.styleable.HiSearchView_key_word_background)

        //关键词清除图标
        val keywordClearIcon = array.getString(R.styleable.HiSearchView_clear_icon)
        val keywordClearIconSize = array.getDimensionPixelSize(
            R.styleable.HiSearchView_clear_icon_size,
            HiDisplayUtil.sp2px(12f)
        )


        array.recycle()

        return Attrs(
            searchBackground,
            searchIcon,
            searchIconSize.toFloat(),
            iconPadding,
            clearIcon,
            clearIconSize.toFloat(),
            hintText,
            hintTextSize.toFloat(),
            hintTextColor,
            gravity,
            searchTextSize.toFloat(),
            searchTextColor,
            keywordSize.toFloat(),
            keywordColor,
            keywordMaxLen,
            keywordBackground,
            keywordClearIcon,
            keywordClearIconSize.toFloat()

        )
    }


    data class Attrs(
        /*search view background*/
        val searchBackground: Drawable?,
        /*search icon 🔍*/
        val searchIcon: String?,
        val searchIconSize: Float,
        val iconPadding: Int,
        /*clearIcon*/
        val clearIcon: String?,
        val clearIconSize: Float,
        /*hint*/
        val hintText: String?,
        val hintTextSize: Float,
        val hintTextColor: Int,
        val gravity: Int,
        /*search text*/
        val searchTextSize: Float,
        val searchTextColor: Int,
        /*keyword*/
        val keywordSize: Float,
        val keywordColor: Int,
        val keywordMaxLen: Int,
        val keywordBackground: Drawable?,
        val keywordClearIcon: String?,
        val keywordClearIconSize: Float
    )
}