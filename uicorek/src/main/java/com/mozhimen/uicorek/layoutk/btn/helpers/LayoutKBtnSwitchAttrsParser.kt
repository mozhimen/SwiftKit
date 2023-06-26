package com.mozhimen.uicorek.layoutk.btn.helpers

import android.content.Context
import android.util.AttributeSet
import com.mozhimen.basick.utilk.android.view.dp2px
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IAttrsParser
import com.mozhimen.uicorek.layoutk.btn.mos.LayoutKBtnSwitchAttrs

/**
 * @ClassName LayoutKBtnSwitchAttrsParser
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/5/6 20:31
 * @Version 1.0
 */
object LayoutKBtnSwitchAttrsParser : IAttrsParser<LayoutKBtnSwitchAttrs> {
    val BG_COLOR_ON = UtilKRes.getColor(R.color.ui_blue_650)
    val BG_COLOR_OFF = UtilKRes.getColor(R.color.ui_blue_050)
    val BTN_COLOR = UtilKRes.getColor(android.R.color.white)
    val BTN_MARGIN = 3f.dp2px()
    val BORDER_COLOR = UtilKRes.getColor(R.color.ui_blue_050)
    val BORDER_WIDTH = 1f.dp2px().toInt()

    override fun parseAttrs(context: Context, attrs: AttributeSet?): LayoutKBtnSwitchAttrs {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBtnSwitch)
        val defaultStatus =
            typedArray.getBoolean(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_defaultStatus, false)
        val bgColorOn =
            typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_bgColorOn, BG_COLOR_ON)
        val bgColorOff =
            typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_bgColorOff, BG_COLOR_OFF)
        val btnColor =
            typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_btnColor, BTN_COLOR)
        val btnMargin =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_btnMargin, BTN_MARGIN.toInt()).toFloat()
        val borderColor =
            typedArray.getColor(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_borderColor, BORDER_COLOR)
        val borderWidth =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_borderWidth, BORDER_WIDTH)
        val animTime =
            typedArray.getInt(R.styleable.LayoutKBtnSwitch_layoutKBtnSwitch_animTime, 300)
        typedArray.recycle()
        return LayoutKBtnSwitchAttrs(defaultStatus, bgColorOn, bgColorOff, btnColor, btnMargin, borderColor, borderWidth, animTime)
    }
}
