package com.mozhimen.uicorek.popwink

import android.app.Activity
import android.view.View
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.textk.bubble.TextKBubble
import com.mozhimen.uicorek.textk.bubble.TextKBubblePopWin
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble.ArrowDirection

/**
 * @ClassName PopwinKBubbleText
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/7 11:50
 * @Version 1.0
 */
class PopwinKBubbleText(contentView: View, textKBubble: TextKBubble) : TextKBubblePopWin(contentView, textKBubble) {

    class Builder(private val _context: Activity) {

        var layoutId = R.layout.textk_bubble

        private var _tip: String? = null
        private var _delayMillis: Long = DEFAULT_DISMISS_DELAY

        fun setTip(tip: String): Builder {
            _tip = tip
            return this
        }

        fun setDismissDelay(delayMillis: Long): Builder {
            _delayMillis = delayMillis
            return this
        }

        fun create(anchorView: View): TextKBubblePopWin {
            val rootView = View.inflate(_context, layoutId, null) as TextKBubble
            rootView.text = _tip ?: DEFAULT_TEXT

            val textKBubblePopWin = TextKBubblePopWin(rootView, rootView)
            textKBubblePopWin.setCancelOnTouch(true)
            textKBubblePopWin.setCancelOnTouchOutside(true)
            textKBubblePopWin.setCancelOnLater(_delayMillis)
            textKBubblePopWin.showArrowTo(anchorView, ArrowDirection.Up)
            return textKBubblePopWin
        }
    }

    private companion object {
        const val DEFAULT_TEXT = "请设置你的信息"
        const val DEFAULT_DISMISS_DELAY = 4000L
    }
}