package com.mozhimen.uicoremk.navmk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basicsmk.utilmk.UtilMKRes
import com.mozhimen.basicsmk.utilmk.sp2px
import com.mozhimen.uicoremk.R

/**
 * @ClassName NavMKAttrsParser
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:41
 * @Version 1.0
 */
object NavMKAttrsParser {
    fun parseNavAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): NavMKAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.NavMKBar_NavMKBarStyle, value, true)

        //xml-->theme.navigationStyle---navigationStyle
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.NavMKBarStyle
        val array = context.obtainStyledAttributes(attrs, R.styleable.NavMKBar, defStyleAttr, defStyleRes)

        //标题
        val titleStr = array.getString(
            R.styleable.NavMKBar_navMKBar_title
        )
        val titleTextColor = array.getColor(
            R.styleable.NavMKBar_navMKBar_title_textColor,
            UtilMKRes.getColor(android.R.color.black)
        )
        val titleTextSize = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_title_textSize, 17f.sp2px()
        )
        val titleTextSizeWithSubtitle = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_title_textSize_with_subTitle, 16f.sp2px()
        )

        //副标题
        val subTitleStr = array.getString(
            R.styleable.NavMKBar_navMKBar_subTitle
        )
        val subTitleTextSize = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_subTitle_textSize, 14f.sp2px()
        )
        val subTitleTextColor = array.getColor(
            R.styleable.NavMKBar_navMKBar_subTitle_textColor, UtilMKRes.getColor(android.R.color.black)
        )

        //按钮
        val textBtnTextSize = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_textBtn_textSize, 16f.sp2px()
        )
        val textBtnTextColor =
            array.getColorStateList(R.styleable.NavMKBar_navMKBar_textBtn_textColor)

        //图标
        val iconStr = array.getString(
            R.styleable.NavMKBar_navMKBar_icon
        )
        val iconColor = array.getColor(
            R.styleable.NavMKBar_navMKBar_icon_color, Color.BLACK
        )
        val iconSize = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_icon_size, 18f.sp2px()
        )

        //左右边距
        val paddingHorizontal = array.getDimensionPixelSize(
            R.styleable.NavMKBar_navMKBar_paddingHorizontal, 0
        )

        val lineColor = array.getColor(
            R.styleable.NavMKBar_navMKBar_lineColor, Color.parseColor("#eeeeee")
        )
        val lineHeight = array.getDimensionPixelOffset(
            R.styleable.NavMKBar_navMKBar_lineWidth, 0
        )
        array.recycle()

        return NavMKAttrs(
            iconStr,
            iconColor,
            iconSize.toFloat(),
            titleStr,
            titleTextColor,
            titleTextSize.toFloat(),
            titleTextSizeWithSubtitle.toFloat(),
            subTitleStr,
            subTitleTextSize.toFloat(),
            subTitleTextColor,
            textBtnTextColor,
            textBtnTextSize.toFloat(),
            lineColor,
            lineHeight,
            paddingHorizontal
        )
    }
}