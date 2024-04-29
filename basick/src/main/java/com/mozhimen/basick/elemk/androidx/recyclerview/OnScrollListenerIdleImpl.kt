package com.mozhimen.basick.elemk.androidx.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_Set
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.lang.ref.WeakReference


/**
 * @ClassName OnScollListenerImpl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/29
 * @Version 1.0
 */
class OnScrollListenerIdleImpl(private val _recyclerViewRef: WeakReference<RecyclerView>, private val _block: I_Listener) : RecyclerView.OnScrollListener(), IUtilK {
    private var _isScrolling by VarProperty_Set(true) { _, value ->
        if (!value) {
            generateBlock()
        }
        true
    }

    //////////////////////////////////////////////////

    init {
        if (_recyclerViewRef.get()?.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            _isScrolling = false
        }
    }

    //////////////////////////////////////////////////

    private fun generateBlock() {
        if (_recyclerViewRef.get()?.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            _block.invoke()
            _recyclerViewRef.get()?.removeOnScrollListener(this).also { UtilKLogWrapper.d(TAG, "generateBlock removeOnScrollListener") }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            _isScrolling = false
        }
    }
}
