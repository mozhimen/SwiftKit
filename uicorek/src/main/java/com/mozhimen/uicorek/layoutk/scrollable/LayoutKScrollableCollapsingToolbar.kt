package com.mozhimen.uicorek.layoutk.scrollable

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mozhimen.basick.elemk.commons.IA_Listener

/**
 * @ClassName LayoutLScrollableCollapsingToolbar
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/6 10:29
 * @Version 1.0
 */
class LayoutKScrollableCollapsingToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CollapsingToolbarLayout(context, attrs, defStyleAttr) {
    private var _onScrimsShownListener: IA_Listener<Boolean>? = null//public interface OnScrimsShownListener {void onScrimsShown(boolean shown); }
    private var _scrimsAreShown = false

    ////////////////////////////////////////////////////////////////////////////

    override fun setScrimsShown(shown: Boolean, animate: Boolean) {
        super.setScrimsShown(shown, animate)
        _scrimsAreShown = shown
        _onScrimsShownListener?.invoke(shown)
    }

    ////////////////////////////////////////////////////////////////////////////

    fun isScrimsAreShown(): Boolean =
        _scrimsAreShown

    fun setOnScrimsShownListener(onScrimsShownListener: IA_Listener<Boolean>) {
        _onScrimsShownListener = onScrimsShownListener
    }
}