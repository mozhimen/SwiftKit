package com.mozhimen.uicorek.loadk.commons

import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.refreshk.RefreshKLayout
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView
import com.mozhimen.uicorek.refreshk.mos.RefreshKStatus

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
) :
    DataKRecyclerView.IDataKLoadListener {
    override fun onLoadMore() {
        if (_overView.refreshKStatus == RefreshKStatus.VISIBLE ||
            _overView.refreshKStatus == RefreshKStatus.REFRESHING ||
            _overView.refreshKStatus == RefreshKStatus.OVERFLOW ||
            _overView.refreshKStatus == RefreshKStatus.OVERFLOW_RELEASE
        ) {
            //正处于刷新状态
            _refreshKLayout.refreshFinished()
            return
        }
    }
}