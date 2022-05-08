package com.mozhimen.uicorek.loadk.commons

import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.refreshk.RefreshKLayout
import com.mozhimen.uicorek.refreshk.commons.IRefreshK

/**
 * @ClassName LoadKRefreshListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/28 14:44
 * @Version 1.0
 */
open class LoadKRefreshCallback(
    private val _refreshKLayout: RefreshKLayout,
    private val _recyclerView: DataKRecyclerView,
) : IRefreshK.IRefreshKListener {
    override fun onRefresh() {
        if (_recyclerView.isLoading()) {
            _refreshKLayout.post {
                _refreshKLayout.refreshFinished()
            }
            return
        }
    }

    override fun enableRefresh(): Boolean = true
}