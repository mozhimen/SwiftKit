package com.mozhimen.uicorek.loadk.commons

import androidx.annotation.CallSuper
import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.refreshk.RefreshKLayout
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView
import com.mozhimen.uicorek.refreshk.cons.ERefreshKStatus

/**
 * @ClassName DataKLoadListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/28 13:39
 * @Version 1.0
 */
open class LoadKLoadCallback(
    private val _overView: RefreshKOverView,
    private val _refreshKLayout: RefreshKLayout,
) : DataKRecyclerView.IDataKLoadListener {
    @CallSuper
    override fun onLoadMore() {
        if (_overView.refreshKStatus == ERefreshKStatus.VISIBLE ||
            _overView.refreshKStatus == ERefreshKStatus.REFRESHING ||
            _overView.refreshKStatus == ERefreshKStatus.OVERFLOW ||
            _overView.refreshKStatus == ERefreshKStatus.OVERFLOW_RELEASE
        ) {
            //正处于刷新状态
            _refreshKLayout.refreshFinished()
            return
        }
    }
}