package com.mozhimen.uicorek.layoutk.navbar.helpers

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.sp2px
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.basick.utilk.res.UtilKTheme
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IAttrsParser2
import com.mozhimen.uicorek.layoutk.navbar.mos.MNavBarAttrs

/**
 * @ClassName AttrsParser
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/2 14:41
 * @Version 1.0
 */
internal object AttrsParser : IAttrsParser2<MNavBarAttrs> {
    private const val TITLE_TEXT = "请填写你的标题"
    private val TITLE_TEXT_SIZE = 17f.sp2px().toInt()
    private const val TITLE_TEXT_COLOR = Color.BLACK
    private val SUBTITLE_TEXT_SIZE = 14f.sp2px().toInt()
    private val SUBTITLE_TEXT_COLOR = UtilKRes.getColor(R.color.ui_gray_350)
    private val SUBTITLE_TEXT_MARGIN_TOP = 1f.dp2px().toInt()
    private val LINE_COLOR = UtilKRes.getColor(R.color.ui_gray_200)
    private val LINE_WIDTH = 0f.dp2px().toInt()

    override fun parseAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): MNavBarAttrs {
        val typedValue = TypedValue()
        UtilKTheme.resolveAttribute(context, R.attr.LayoutKNavBar_LayoutKNavBar_Style, typedValue, true)
        val defStyleRes = if (typedValue.resourceId != 0) typedValue.resourceId else R.style.LayoutKNavBar_Style//xml-->theme.navigationStyle---navigationStyle

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKNavBar, defStyleAttr, defStyleRes)
        val titleStr =
            typedArray.getString(R.styleable.LayoutKNavBar_layoutKNavBar_title) ?: TITLE_TEXT//标题
        val titleTextColor =
            typedArray.getColor(R.styleable.LayoutKNavBar_layoutKNavBar_title_textColor, TITLE_TEXT_COLOR)
        val titleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKNavBar_layoutKNavBar_title_textSize, TITLE_TEXT_SIZE)
        val subTitleStr =
            typedArray.getString(R.styleable.LayoutKNavBar_layoutKNavBar_subTitle)//副标题
        val subTitleTextSize =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKNavBar_layoutKNavBar_subTitle_textSize, SUBTITLE_TEXT_SIZE)
        val subTitleTextColor =
            typedArray.getColor(R.styleable.LayoutKNavBar_layoutKNavBar_subTitle_textColor, SUBTITLE_TEXT_COLOR)
        val subTitleMarginTop =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKNavBar_layoutKNavBar_subTitle_marginTop, SUBTITLE_TEXT_MARGIN_TOP)
        val lineColor =
            typedArray.getColor(R.styleable.LayoutKNavBar_layoutKNavBar_lineColor, LINE_COLOR)
        val lineWidth =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKNavBar_layoutKNavBar_lineWidth, LINE_WIDTH)
        typedArray.recycle()

        return MNavBarAttrs(
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