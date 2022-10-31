package com.mozhimen.uicorek.searchk.mos

import android.graphics.drawable.Drawable

/**
 * @ClassName SearchKAttrs
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/7 12:11
 * @Version 1.0
 */
data class MSearchKAttrs(
    val searchIcon: String?,
    val searchIconSize: Float,
    val searchIconPadding: Int,
    val searchBackground: Drawable?,
    val searchTextSize: Float,
    val searchTextColor: Int,
    val clearIcon: String?,
    val clearIconSize: Float,
    val hintText: String?,
    val hintTextSize: Float,
    val hintTextColor: Int,
    val hintGravity: Int,
    val keywordIcon: String?,
    val keywordIconSize: Float,
    val keywordIconColor: Int,
    val keywordBackground: Drawable?,
    val keywordMaxLength: Int,
    val keywordPadding: Float
)
