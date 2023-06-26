package com.mozhimen.uicorektest.layoutk.loadrefresh

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.handler.WakeBefPauseLifecycleHandler
import com.mozhimen.basick.utilk.android.os.applyPostDelayed
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.LoadRefreshLoadCallback
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.LoadRefreshRefreshCallback
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkLoadrefreshBinding
import com.mozhimen.uicorektest.recyclerk.mos.RecyclerKItemLoadMore

class LayoutKLoadRefreshActivity : BaseActivityVB<ActivityLayoutkLoadrefreshBinding>() {
    private val _dataSets = ArrayList<BaseRecyclerKItem<out RecyclerView.ViewHolder>>()
    private var _pageIndex = 0

    override fun initView(savedInstanceState: Bundle?) {
        _dataSets.apply {
            add(RecyclerKItemLoadMore(1))
            add(RecyclerKItemLoadMore(2))
            add(RecyclerKItemLoadMore(3))
            add(RecyclerKItemLoadMore(4))
            add(RecyclerKItemLoadMore(5))
        }
        val lottieOverView = LottieOverView(this)
        vb.loadkContainer.apply {
            initLoadParams(
                5,
                _dataSets,
                object : LoadRefreshLoadCallback(lottieOverView, vb.loadkContainer) {
                    override fun onLoading() {
                        super.onLoading()
                        _pageIndex++
                        WakeBefPauseLifecycleHandler(this@LayoutKLoadRefreshActivity).applyPostDelayed(1000) {
                            val items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>> = arrayListOf(RecyclerKItemLoadMore(_dataSets.size + 1))
                            _dataSets.addAll(items)
                            vb.loadkContainer.startLoad(items, null)
                        }
                    }
                })
            initRefreshParams(
                LottieOverView(this@LayoutKLoadRefreshActivity),
                null,
                null,
                null,
                object : LoadRefreshRefreshCallback(vb.loadkContainer, vb.loadkContainer.getRecyclerKLoad()) {
                    override fun onRefreshing() {
                        super.onRefreshing()
                        _pageIndex = 1
                        WakeBefPauseLifecycleHandler(this@LayoutKLoadRefreshActivity).applyPostDelayed(1000) {
                            //模拟获取到了
                            val items: ArrayList<BaseRecyclerKItem<out RecyclerView.ViewHolder>> = arrayListOf(
                                RecyclerKItemLoadMore(1),
                                RecyclerKItemLoadMore(2),
                                RecyclerKItemLoadMore(3),
                                RecyclerKItemLoadMore(4),
                            )
                            _dataSets.clear()
                            _dataSets.addAll(items)
                            startRefresh(items, null)
                        }
                    }
                })
        }
    }
}