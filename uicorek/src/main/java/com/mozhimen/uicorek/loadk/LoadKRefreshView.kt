package com.mozhimen.uicorek.loadk

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.uicorek.refreshk.RefreshKLayout
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView

/**
 * @ClassName RefreshKLoadView
 * @Description 上拉加载,下拉刷新
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/28 9:58
 * @Version 1.0
 */
class LoadKRefreshView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RefreshKLayout(context, attrs, defStyleAttr) {

    private lateinit var _dataKRecyclerView: DataKRecyclerView
    private lateinit var _adapter: DataKAdapter

    interface ILoadKRefreshListener {
        fun onRefreshOrLoad(isSuccess: Boolean)
    }

    /**
     * 获取下拉加载RecyclerView
     * @return DataKRecyclerView
     */
    fun getRecycler(): DataKRecyclerView = _dataKRecyclerView

    fun initRefresher(
        refreshKOverView: RefreshKOverView,
        minPullRefreshHeight: Int?,
        minDamp: Float?,
        maxDamp: Float?,
        listener: IRefreshK.IRefreshKListener
    ) {
        setRefreshOverView(refreshKOverView)
        setRefreshParams(minPullRefreshHeight, minDamp, maxDamp)
        setRefreshListener(listener)
    }

    fun initRecycler(
        prefetchSize: Int,
        items: List<DataKItem<*, out RecyclerView.ViewHolder>>,
        listener: DataKRecyclerView.IDataKLoadListener
    ) {
        _dataKRecyclerView = DataKRecyclerView(context)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _dataKRecyclerView.layoutManager = layoutManager
        _adapter = DataKAdapter(context)
        _dataKRecyclerView.adapter = _adapter
        _dataKRecyclerView.enableLoadMore(prefetchSize, listener)
        _adapter.addItems(items, true)
        addView(_dataKRecyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    fun refresh(dataItems: List<DataKItem<*, out RecyclerView.ViewHolder>>?, listener: ILoadKRefreshListener?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        this.refreshFinished()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _adapter.clearItems()
            _adapter.addItems(dataItems!!, true)
        } else {
            if (_adapter.itemCount <= 0) {
                listener?.onRefreshOrLoad(success)
            }
        }
    }

    fun load(dataItems: List<DataKItem<*, out RecyclerView.ViewHolder>>?, listener: ILoadKRefreshListener?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _adapter.addItems(dataItems!!, true)
        } else {
            listener?.onRefreshOrLoad(success)
        }
        _dataKRecyclerView.loadFinished()
    }
}