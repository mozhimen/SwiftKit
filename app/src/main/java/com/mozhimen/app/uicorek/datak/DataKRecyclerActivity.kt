package com.mozhimen.app.uicorek.datak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityDatakLoadMoreBinding
import com.mozhimen.app.uicorek.datak.mos.DataItemLoadMore
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.extsk.postDelayed
import com.mozhimen.basicsk.utilk.UtilKHandler
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.uicorek.datak.DataKRecyclerView
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.mos.RefreshKStatus
import com.mozhimen.uicorek.refreshk.temps.TextOverView

class DataKRecyclerActivity : AppCompatActivity() {
    private var _pageIndex: Int = 1
    private val TAG = "DataKLoadMoreActivity>>>>>"
    private lateinit var _textOverView: TextOverView
    private val vb by lazy { ActivityDatakLoadMoreBinding.inflate(layoutInflater) }
    private lateinit var _adapter: DataKAdapter
    private val _dataSets = ArrayList<DataKItem<*, out RecyclerView.ViewHolder>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        initRefreshK()
        initRecycler()
    }

    private fun initRefreshK() {
        _textOverView = TextOverView(this)
        vb.datakLoadMoreRefresh.setRefreshOverView(_textOverView)
        vb.datakLoadMoreRefresh.setRefreshParams(90f.dp2px(), 1.6f, null)
        vb.datakLoadMoreRefresh.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                if (vb.datakLoadMoreRecycler.isLoading()) {
                    //正处于分页
                    //复现场景,比较难以复现---》如果下执行上拉分页。。。快速返回  往下拉，松手。会出现一个bug: 转圈圈的停住不动了。
                    //问题的原因在于 立刻调用 refreshFinished 时，refreshHeader的底部bottom值是超过了 它的height的。
                    //refreshLayout#recover（dis） 方法中判定了，如果传递dis 参数 大于 header height ,dis =200,height =100,只能恢复到 刷新的位置。不能恢复到初始位置。
                    //加了延迟之后，他会  等待 松手的动画做完，才去recover 。此时就能恢复最初状态了。
                    vb.datakLoadMoreRefresh.post {
                        vb.datakLoadMoreRefresh.refreshFinished()
                    }
                    return
                }
                _pageIndex = 1
                //模拟刷新
                UtilKHandler(this@DataKRecyclerActivity).postDelayed(1000) {
                    //模拟获取到了
                    val dataItems: ArrayList<DataKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                        DataItemLoadMore(1),
                        DataItemLoadMore(2),
                        DataItemLoadMore(3),
                        DataItemLoadMore(4),
                    )
                    _dataSets.clear()
                    _dataSets.addAll(dataItems)
                    //----------->
                    refreshOrLoad(true, _dataSets)
                }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }

    /**
     * 刷新or加载
     * @param isRefresh Boolean 是否是刷新
     * @param dataItems List<DataKItem<*, out ViewHolder>>?
     */
    fun refreshOrLoad(isRefresh: Boolean, dataItems: List<DataKItem<*, out RecyclerView.ViewHolder>>?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        //光真么判断还是不行的，我们还需要别的措施。。。因为可能会出现 下拉单时候，有执行了删上拉分页
        if (isRefresh) {
            vb.datakLoadMoreRefresh.refreshFinished()
            if (success) {
                //emptyView?.visibility = View.GONE空白布局
                _adapter.clearItems()
                _adapter.addItems(dataItems!!, true)
            } else {
                //此时就需要判断列表上是否已经有数据，如果么有，显示出空页面转态
                if (_adapter.itemCount <= 0) {
                    //emptyView?.visibility = View.VISIBLE空白布局
                }
            }
        } else {
            if (success) {
                _dataSets.addAll(dataItems!!)
                _adapter.addItems(dataItems, true)
            }
            vb.datakLoadMoreRecycler.loadFinished()
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        _adapter = DataKAdapter(this)
        vb.datakLoadMoreRecycler.layoutManager = layoutManager
        vb.datakLoadMoreRecycler.adapter = _adapter
        vb.datakLoadMoreRecycler.setFooterView(R.layout.item_datak_footer_load)
        vb.datakLoadMoreRecycler.enableLoadMore(5, object : DataKRecyclerView.IDataKLoadListener {
            override fun onLoadMore() {
                if (_textOverView.refreshKStatus == RefreshKStatus.VISIBLE ||
                    _textOverView.refreshKStatus == RefreshKStatus.REFRESHING ||
                    _textOverView.refreshKStatus == RefreshKStatus.OVERFLOW ||
                    _textOverView.refreshKStatus == RefreshKStatus.OVERFLOW_RELEASE
                ) {
                    //正处于刷新状态
                    vb.datakLoadMoreRefresh.refreshFinished()
                    return
                }
                _pageIndex++
                //模拟加载
                UtilKHandler(this).postDelayed(1000) {
                    val dataItems: List<DataKItem<*, out RecyclerView.ViewHolder>> = arrayListOf(
                        DataItemLoadMore(_dataSets.size + 1)
                    )
                    refreshOrLoad(false, dataItems)
                }
            }
        })
        _dataSets.apply {
            add(DataItemLoadMore(1))
            add(DataItemLoadMore(2))
            add(DataItemLoadMore(3))
            add(DataItemLoadMore(4))
            add(DataItemLoadMore(5))
        }
        _adapter.addItems(_dataSets, true)
    }
}