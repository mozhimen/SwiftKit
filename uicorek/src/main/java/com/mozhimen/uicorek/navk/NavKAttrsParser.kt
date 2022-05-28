package com.mozhimen.uicorek.navk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R

/**
 * @ClassName NavKAttrsParser
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:41
 * @Version 1.0
 */
internal object NavKAttrsParser {
    internal val TITLE_TEXT = "请填写你的标题"
    internal val TITLE_TEXT_SIZE = 17f.sp2px()
    internal val TITLE_TEXT_COLOR = Color.BLACK
    internal val SUBTITLE_TEXT_SIZE = 14f.sp2px()
    internal val SUBTITLE_TEXT_COLOR = UtilKRes.getColor(R.color.gray_normal)
    internal val SUBTITLE_TEXT_MARGIN_TOP = 1f.dp2px()
    internal val LINE_COLOR = UtilKRes.getColor(R.color.gray_light)
    internal val LINE_WIDTH = 0f.dp2px()

    fun parseNavAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): NavKAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.NavKBar_NavKBarStyle, value, true)

        //xml-->theme.navigationStyle---navigationStyle
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.NavKBarStyle
        val array = context.obtainStyledAttributes(attrs, R.styleable.NavKBar, defStyleAttr, defStyleRes)

        //标题
        val titleStr = array.getString(
            R.styleable.NavKBar_navKBar_title
        )
        val titleTextColor = array.getColor(
            R.styleable.NavKBar_navKBar_title_textColor,
            TITLE_TEXT_COLOR
        )
        val titleTextSize = array.getDimensionPixelSize(
            R.styleable.NavKBar_navKBar_title_textSize, TITLE_TEXT_SIZE
        )

        //副标题
        val subTitleStr = array.getString(
            R.styleable.NavKBar_navKBar_subTitle
        )
        val subTitleTextSize = array.getDimensionPixelSize(
            R.styleable.NavKBar_navKBar_subTitle_textSize, SUBTITLE_TEXT_SIZE
        )
        val subTitleTextColor = array.getColor(
            R.styleable.NavKBar_navKBar_subTitle_textColor, SUBTITLE_TEXT_COLOR
        )
        val subTitleMarginTop = array.getDimensionPixelOffset(
            R.styleable.NavKBar_navKBar_subTitle_marginTop, SUBTITLE_TEXT_MARGIN_TOP
        )

        val lineColor = array.getColor(
            R.styleable.NavKBar_navKBar_lineColor, LINE_COLOR
        )
        val lineWidth = array.getDimensionPixelOffset(
            R.styleable.NavKBar_navKBar_lineWidth, LINE_WIDTH
        )
        array.recycle()

        return NavKAttrs(
            titleStr ?: TITLE_TEXT,
            titleTextColor,
            titleTextSize,
            subTitleStr,
            subTitleTextSize,
            subTitleTextColor,
            subTitleMarginTop,
            lineColor,
            lineWidth
        )
    }

    data class NavKAttrs(
        val titleStr: String,
        val titleTextColor: Int,
        val titleTextSize: Int,
        val subTitleStr: String?,
        val subTitleTextSize: Int,
        val subTitleTextColor: Int,
        val subTitleMarginTop: Int,
        val lineColor: Int,
        val lineWidth: Int
    )
}