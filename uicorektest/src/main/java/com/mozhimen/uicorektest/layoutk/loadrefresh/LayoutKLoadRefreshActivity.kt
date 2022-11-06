package com.mozhimen.uicorektest.layoutk.loadrefresh

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.prefabk.handler.PrefabKHandler
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.LoadRefreshLoadCallback
import com.mozhimen.uicorek.layoutk.loadrefresh.commons.LoadRefreshRefreshCallback
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkLoadrefreshBinding
import com.mozhimen.uicorektest.recyclerk.mos.RecyclerKItemLoadMore

class LayoutKLoadRefreshActivity : BaseKActivityVB<ActivityLayoutkLoadrefreshBinding>() {
    private val _dataSets = ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    private var _pageIndex = 0

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)

    }

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
            initRecycler(
                5,
                _dataSets,
                object : LoadRefreshLoadCallback(lottieOverView, vb.loadkContainer) {
                    override fun onLoad() {
                        super.onLoad()
                        _pageIndex++
                        PrefabKHandler(this@LayoutKLoadRefreshActivity).postDelayed(1000) {
                            val dataItems: List<RecyclerKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                                RecyclerKItemLoadMore(_dataSets.size + 1)
                            )
                            _dataSets.addAll(dataItems)
                            vb.loadkContainer.load(dataItems, null)
                        }
                    }
                })
            initRefresher(
                LottieOverView(this@LayoutKLoadRefreshActivity),
                null,
                null,
                null,
                object : LoadRefreshRefreshCallback(vb.loadkContainer, vb.loadkContainer.getRecycler()) {
                    override fun onRefresh() {
                        super.onRefresh()
                        _pageIndex = 1
                        PrefabKHandler(this@LayoutKLoadRefreshActivity).postDelayed(1000) {
                            //模拟获取到了
                            val dataItems: ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                                RecyclerKItemLoadMore(1),
                                RecyclerKItemLoadMore(2),
                                RecyclerKItemLoadMore(3),
                                RecyclerKItemLoadMore(4),
                            )
                            _dataSets.clear()
                            _dataSets.addAll(dataItems)
                            refresh(dataItems, null)
                        }
                    }
                })
        }
    }
}