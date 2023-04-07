package com.mozhimen.uicorek.layoutk.loadrefresh

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.ILoadRefreshListener
import com.mozhimen.uicorek.recyclerk.load.RecyclerKLoad
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffed
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.ILoadRefresh
import com.mozhimen.uicorek.layoutk.refresh.LayoutKRefresh
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
class LayoutKLoadRefresh @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutKRefresh(context, attrs, defStyleAttr), ILoadRefresh {

    private lateinit var _recyclerKLoad: RecyclerKLoad
    private val _adapterKRecyclerStuffed by lazy { AdapterKRecyclerStuffed() }


    override fun getRecyclerKLoad(): RecyclerKLoad =
        _recyclerKLoad

    override fun initRefreshParams(
        refreshOverView: RefreshOverView,
        minPullRefreshHeight: Int?,
        minDamp: Float?,
        maxDamp: Float?,
        listener: IRefreshListener?
    ) {
        setRefreshOverView(refreshOverView)
        setRefreshParams(minPullRefreshHeight, minDamp, maxDamp)
        setRefreshListener(listener)
    }

    override fun initLoadParams(
        prefetchSize: Int,
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>,
        listener: IRecyclerKLoadListener?
    ) {
        _recyclerKLoad = RecyclerKLoad(context)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _recyclerKLoad.layoutManager = layoutManager
        _recyclerKLoad.adapter = _adapterKRecyclerStuffed
        _recyclerKLoad.enableLoad(prefetchSize, listener)
        _adapterKRecyclerStuffed.addItems(items, true)
        addView(_recyclerKLoad, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    override fun startRefresh(
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>?,
        listener: ILoadRefreshListener?
    ) {
        val success = items != null && items.isNotEmpty()
        this.finishRefresh()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _adapterKRecyclerStuffed.removeItemsAll(true)
            _adapterKRecyclerStuffed.addItems(items!!, true)
        } else {
            if (_adapterKRecyclerStuffed.itemCount <= 0) {
                listener?.onRefreshOrLoad(success)
            }
        }
    }

    override fun startLoad(
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>?,
        listener: ILoadRefreshListener?
    ) {
        val success = items != null && items.isNotEmpty()
        if (success) {
            listener?.onRefreshOrLoad(success)
            _adapterKRecyclerStuffed.addItems(items!!, true)
        } else {
            listener?.onRefreshOrLoad(success)
        }
        _recyclerKLoad.finishLoad()
    }
}