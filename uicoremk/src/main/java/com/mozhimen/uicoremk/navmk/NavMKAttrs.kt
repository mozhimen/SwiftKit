package com.mozhimen.uicoremk.navmk

import android.content.res.ColorStateList

/**
 * @ClassName NavMKAttrs
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:50
 * @Version 1.0
 */
data class NavMKAttrs(
    val iconStr: String?,
    val iconColor: Int,
    val iconSize: Float,
    val titleStr: String?,
    val titleTextColor: Int,
    val titleTextSize: Float,
    val titleTextSizeWithSubTitle: Float,
    val subTitleStr: String?,
    val subTitleTextSize: Float,
    val subTitleTextColor: Int,
    val textBtnTextColor: ColorStateList?,
    val textBtnTextSize: Float,
    val lineColor: Int,
    val lineWidth: Int,
    val paddingHorizontal: Int
)
