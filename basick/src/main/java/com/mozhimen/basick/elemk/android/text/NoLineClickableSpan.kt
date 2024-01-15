package com.mozhimen.basick.elemk.android.text

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.mozhimen.basick.elemk.commons.IA_Listener

/**
 * @ClassName NoLineClickableSpan
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/12
 * @Version 1.0
 */
class NoLineClickableSpan(private val _onClick: IA_Listener<View>) : ClickableSpan() {
    override fun onClick(widget: View) {
        _onClick.invoke(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
    }
}