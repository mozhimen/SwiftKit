package com.mozhimen.uicorek.navk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.navk.mos.NavKAttrs

/**
 * @ClassName NavKAttrsParser
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:41
 * @Version 1.0
 */
internal object NavKAttrsParser {
    private const val TITLE_TEXT = "请填写你的标题"
    private val TITLE_TEXT_SIZE = 17f.sp2px()
    private const val TITLE_TEXT_COLOR = Color.BLACK
    private val SUBTITLE_TEXT_SIZE = 14f.sp2px()
    private val SUBTITLE_TEXT_COLOR = UtilKRes.getColor(R.color.gray_normal)
    private val SUBTITLE_TEXT_MARGIN_TOP = 1f.dp2px()
    private val LINE_COLOR = UtilKRes.getColor(R.color.gray_light)
    private val LINE_WIDTH = 0f.dp2px()

    fun parseNavAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): NavKAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.NavKBar_NavKBarStyle, value, true)

        //xml-->theme.navigationStyle---navigationStyle
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.NavKBarStyle
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavKBar, defStyleAttr, defStyleRes)

        //标题
        val titleStr =
            typedArray.getString(R.styleable.NavKBar_navKBar_title) ?: TITLE_TEXT
        val titleTextColor =
            typedArray.getColor(R.styleable.NavKBar_navKBar_title_textColor, TITLE_TEXT_COLOR)
        val titleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.NavKBar_navKBar_title_textSize, TITLE_TEXT_SIZE)

        //副标题
        val subTitleStr =
            typedArray.getString(R.styleable.NavKBar_navKBar_subTitle)
        val subTitleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.NavKBar_navKBar_subTitle_textSize, SUBTITLE_TEXT_SIZE)
        val subTitleTextColor =
            typedArray.getColor(R.styleable.NavKBar_navKBar_subTitle_textColor, SUBTITLE_TEXT_COLOR)
        val subTitleMarginTop =
            typedArray.getDimensionPixelOffset(R.styleable.NavKBar_navKBar_subTitle_marginTop, SUBTITLE_TEXT_MARGIN_TOP)
        val lineColor =
            typedArray.getColor(R.styleable.NavKBar_navKBar_lineColor, LINE_COLOR)
        val lineWidth =
            typedArray.getDimensionPixelOffset(R.styleable.NavKBar_navKBar_lineWidth, LINE_WIDTH)
        typedArray.recycle()

        return NavKAttrs(
            titleStr,
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
}