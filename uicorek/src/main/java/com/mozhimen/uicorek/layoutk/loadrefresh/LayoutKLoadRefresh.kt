package com.mozhimen.uicorek.layoutk.loadrefresh

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.ILoadRefreshListener
import com.mozhimen.uicorek.recyclerk.load.RecyclerKLoad
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.recyclerk.RecyclerKAdapter
import com.mozhimen.uicorek.layoutk.refresh.LayoutKRefresh
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefresh
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.commons.RefreshOverView
import com.mozhimen.uicorek.recyclerk.load.commons.IRecyclerKLoadListener

/**
 * @ClassName LayoutKLoadRefresh
 * @Description 上拉加载,下拉刷新
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/28 9:58
 * @Version 1.0
 */
class LayoutKLoadRefresh @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    LayoutKRefresh(context, attrs, defStyleAttr) {

    private lateinit var _recyclerKLoad: RecyclerKLoad
    private lateinit var _recyclerKAdapter: RecyclerKAdapter

    /**
     * 获取上拉加载RecyclerView
     * @return DataKRecyclerView
     */
    fun getRecycler(): RecyclerKLoad = _recyclerKLoad

    fun initRefresher(
        refreshOverView: RefreshOverView,
        minPullRefreshHeight: Int?,
        minDamp: Float?,
        maxDamp: Float?,
        listener: IRefreshListener
    ) {
        setRefreshOverView(refreshOverView)
        setRefreshParams(minPullRefreshHeight, minDamp, maxDamp)
        setRefreshListener(listener)
    }

    fun initRecycler(
        prefetchSize: Int,
        items: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>,
        listener: IRecyclerKLoadListener
    ) {
        _recyclerKLoad = RecyclerKLoad(context)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _recyclerKLoad.layoutManager = layoutManager
        _recyclerKAdapter = RecyclerKAdapter(context)
        _recyclerKLoad.adapter = _recyclerKAdapter
        _recyclerKLoad.enableLoad(prefetchSize, listener)
        _recyclerKAdapter.addItems(items, true)
        addView(_recyclerKLoad, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    fun refresh(dataItems: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>?, listener: ILoadRefreshListener?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        this.refreshFinished()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _recyclerKAdapter.clearItems()
            _recyclerKAdapter.addItems(dataItems!!, true)
        } else {
            if (_recyclerKAdapter.itemCount <= 0) {
                listener?.onRefreshOrLoad(success)
            }
        }
    }

    fun load(dataItems: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>?, listener: ILoadRefreshListener?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _recyclerKAdapter.addItems(dataItems!!, true)
        } else {
            listener?.onRefreshOrLoad(success)
        }
        _recyclerKLoad.loadFinished()
    }
}