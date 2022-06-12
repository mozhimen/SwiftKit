package com.mozhimen.app.uicorek.loadk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.databinding.ActivityLoadkBinding
import com.mozhimen.app.uicorek.datak.mos.DataItemLoadMore
import com.mozhimen.basick.eventk.EventKHandler
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.loadk.commons.LoadKLoadCallback
import com.mozhimen.uicorek.loadk.commons.LoadKRefreshCallback
import com.mozhimen.uicorek.refreshk.customs.LottieOverView

class LoadKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityLoadkBinding.inflate(layoutInflater) }
    private val _dataSets = ArrayList<DataKItem<*, out RecyclerView.ViewHolder>>()
    private var _pageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        _dataSets.apply {
            add(DataItemLoadMore(1))
            add(DataItemLoadMore(2))
            add(DataItemLoadMore(3))
            add(DataItemLoadMore(4))
            add(DataItemLoadMore(5))
        }
        val lottieOverView = LottieOverView(this)
        vb.loadkContainer.apply {
            initRecycler(
                5,
                _dataSets,
                object : LoadKLoadCallback(lottieOverView, vb.loadkContainer) {
                    override fun onLoadMore() {
                        super.onLoadMore()
                        _pageIndex++
                        EventKHandler(this).postDelayed(1000) {
                            val dataItems: List<DataKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                                DataItemLoadMore(_dataSets.size + 1)
                            )
                            _dataSets.addAll(dataItems)
                            vb.loadkContainer.load(dataItems, null)
                        }
                    }
                })
            initRefresher(
                LottieOverView(this@LoadKActivity),
                null,
                null,
                null,
                object : LoadKRefreshCallback(vb.loadkContainer, vb.loadkContainer.getRecycler()) {
                    override fun onRefresh() {
                        super.onRefresh()
                        _pageIndex = 1
                        EventKHandler(this@LoadKActivity).postDelayed(1000) {
                            //模拟获取到了
                            val dataItems: ArrayList<DataKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                                DataItemLoadMore(1),
                                DataItemLoadMore(2),
                                DataItemLoadMore(3),
                                DataItemLoadMore(4),
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