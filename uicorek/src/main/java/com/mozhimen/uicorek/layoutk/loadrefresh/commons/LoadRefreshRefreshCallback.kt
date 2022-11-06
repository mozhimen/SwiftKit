package com.mozhimen.uicorek.layoutk.loadrefresh.commons

import androidx.annotation.CallSuper
import com.mozhimen.uicorek.recyclerk.load.RecyclerKLoad
import com.mozhimen.uicorek.layoutk.refresh.LayoutKRefresh
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefresh
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener

/**
 * @ClassName LoadKRefreshListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/28 14:44
 * @Version 1.0
 */
open class LoadRefreshRefreshCallback(
    private val _layoutKRefresh: LayoutKRefresh,
    private val _recyclerKLoad: RecyclerKLoad,
) : IRefreshListener {
    @CallSuper
    override fun onRefresh() {
        if (_recyclerKLoad.isLoading()) {
            _layoutKRefresh.post {
                _layoutKRefresh.refreshFinished()
            }
            return
        }
    }

    override fun enableRefresh(): Boolean = true
}