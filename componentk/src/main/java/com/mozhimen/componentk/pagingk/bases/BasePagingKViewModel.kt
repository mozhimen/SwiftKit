package com.mozhimen.componentk.pagingk.bases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.componentk.pagingk.commons.IPagingKDataSource
import com.mozhimen.componentk.pagingk.commons.IPagingKDataSourceLoadListener
import com.mozhimen.componentk.pagingk.cons.CPagingKLoadingState
import com.mozhimen.componentk.pagingk.mos.PagingKConfig
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName BasePagingKViewModel
 * @Description
 * RES 返回的数据
 * DES 目标数据
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:22
 * @Version 1.0
 */
abstract class BasePagingKViewModel<RES, DES>(protected val pagingKConfig: PagingKConfig = PagingKConfig()) : BaseViewModel(), IPagingKDataSource<RES, DES> {
    val liveLoadState = MutableLiveData<Int>()
    open val livePagedList: LiveData<PagedList<DES>>

    ////////////////////////////////////////////////////////////////////////////////////

    private val _pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener = object : IPagingKDataSourceLoadListener {

        override fun onFirstLoadStart() {
            liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_START)
        }

        override fun onFirstLoadCompleted(isEmpty: Boolean) {
            if (isEmpty)
                liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_EMPTY)
            else
                liveLoadState.postValue(CPagingKLoadingState.STATE_FIRST_LOAD_COMPLETED)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////

    init {
        livePagedList = generateLivePagedList()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    fun getViewModelScope(): CoroutineScope =
        viewModelScope

    fun generateLivePagedList(): LiveData<PagedList<DES>> {
        Log.d(TAG, "generateLivePagedList: ")
        val dataSourceFactory = object : BasePagingKDataSourceFactory<DES>(viewModelScope, _pagingKDataSourceLoadingListener) {
            override fun createPagingKDataSource(coroutineScope: CoroutineScope, pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener): BasePagingKKeyedDataSource<RES, DES> =
                object : BasePagingKKeyedDataSource<RES, DES>(pagingKConfig, coroutineScope, pagingKDataSourceLoadingListener) {
                    override suspend fun onLoadInitial(): BasePagingKRep<RES> =
                        this@BasePagingKViewModel.onLoadInitial(pagingKConfig.firstPageIndex, pagingKConfig.loadPageSize)

                    override suspend fun onLoadAfter(adjacentPageKey: Int): BasePagingKRep<RES> =
                        this@BasePagingKViewModel.onLoadAfter(adjacentPageKey, pagingKConfig.loadPageSize)

                    override suspend fun onDataAggregation(dataList: List<RES>): MutableList<DES> =
                        this@BasePagingKViewModel.onDataAggregation(dataList)

                    override suspend fun onAddOtherData(isLoadInitial: Boolean, hasMore: Boolean, dataList: MutableList<DES>) {
                        this@BasePagingKViewModel.onAddOtherData(isLoadInitial, hasMore, dataList)
                    }

                    override fun getHeader(): DES? =
                        this@BasePagingKViewModel.getHeader()

                    override fun getFooter(): DES? =
                        this@BasePagingKViewModel.getFooter()
                }
        }
        return LivePagedListBuilder(
            dataSourceFactory,
            PagedList.Config.Builder()
                //设置是否显示占位符
                .setEnablePlaceholders(false)
                //设置每页的大小
                .setPageSize(pagingKConfig.loadPageSize)
                //设置距离底部还有多少条时开始加载下一页
                .setPrefetchDistance(3)
                //设置首次加载的数量，要求是pageSize的整数倍
                .setInitialLoadSizeHint(pagingKConfig.loadPageSize * 3)
                .build()
        ).build()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    open fun onInvalidate() {
        livePagedList.value?.dataSource?.invalidate()
    }

    ////////////////////////////////////////////////////////////////////////////////////

    /**
     * 初次加载
     *
     * @param firstPage 第一页
     * @param pageSize 每页数量
     *
     * @return 请求的数量
     */
    abstract suspend fun onLoadInitial(firstPage: Int, pageSize: Int): BasePagingKRep<RES>

    /**
     * 加载下一页
     *
     * @param adjacentPageKey 当前页数
     * @param pageSize 每页数量
     *
     * @return 请求的数量
     */
    abstract suspend fun onLoadAfter(adjacentPageKey: Int, pageSize: Int): BasePagingKRep<RES>
}