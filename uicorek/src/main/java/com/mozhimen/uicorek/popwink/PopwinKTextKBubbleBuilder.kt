package com.mozhimen.uicorek.popwink

import android.app.Activity
import android.view.View
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.textk.bubble.TextKBubble

/**
 * @ClassName PopwinKBubbleText
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/7 11:50
 * @Version 1.0
 */
class PopwinKTextKBubbleBuilder(contentView: View, textKBubble: TextKBubble) :
    TextKBubblePopWin(contentView, textKBubble) {

    class Builder(private val _context: Activity) {

        var layoutId = R.layout.textk_bubble

        private var _xOffset = 0
        private var _yOffset = 0
        private var _tip: String? = null
        private var _delayMillis: Long = 4000L
        private var _arrowDirection: EArrowDirection = EArrowDirection.Up
        private var _isCancelOnTouch: Boolean = true
        private var _isCancelOnTouchOutside: Boolean = true

        fun setTip(tip: String): Builder {
            _tip = tip
            return this
        }

        fun setXOffset(xOffset: Int): Builder {
            _xOffset = xOffset
            return this
        }

        fun setYOffset(yOffset: Int): Builder {
            _yOffset = yOffset
            return this
        }

        fun setDismissDelay(delayMillis: Long): Builder {
            _delayMillis = delayMillis
            return this
        }

        fun setArrowDirection(arrowDirection: EArrowDirection): Builder {
            _arrowDirection = arrowDirection
            return this
        }

        fun setCancelOnTouch(cancel: Boolean): Builder {
            _isCancelOnTouch = cancel
            return this
        }

        fun setCancelOnTouchOutside(cancel: Boolean): Builder {
            _isCancelOnTouchOutside = cancel
            return this
        }

        fun create(anchorView: View): TextKBubblePopWin {
            val rootView = View.inflate(_context, layoutId, null) as TextKBubble
            rootView.text = _tip ?: "请设置你的信息"

            val textKBubblePopWin = TextKBubblePopWin(rootView, rootView)
            textKBubblePopWin.xOffset = _xOffset
            textKBubblePopWin.yOffset = _yOffset
            textKBubblePopWin.setCancelOnTouch(_isCancelOnTouch)
            textKBubblePopWin.setCancelOnTouchOutside(true)
            textKBubblePopWin.setCancelOnLater(_delayMillis)
            textKBubblePopWin.showArrowTo(anchorView, _arrowDirection)
            return textKBubblePopWin
        }
    }

}