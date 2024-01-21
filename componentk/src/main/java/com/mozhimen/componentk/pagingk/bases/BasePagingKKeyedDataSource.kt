package com.mozhimen.componentk.pagingk.bases

import androidx.paging.PageKeyedDataSource
import com.mozhimen.componentk.pagingk.commons.IPagingKDataSourceLoadListener
import com.mozhimen.componentk.pagingk.commons.IPagingKKeyedDataSource
import com.mozhimen.componentk.pagingk.mos.PagingKConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName BasePagingKKeyedDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:31
 * @Version 1.0
 */
abstract class BasePagingKKeyedDataSource<RES, DES : Any>(
    private val _pagingKConfig: PagingKConfig,
    private val _coroutineScope: CoroutineScope,
    private val _pagingKDataSourceLoadListener: IPagingKDataSourceLoadListener
) : PageKeyedDataSource<Int, DES>(), IPagingKKeyedDataSource<RES, DES> {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DES>) {
        _coroutineScope.launch(Dispatchers.IO) {
            //回调接口
            _pagingKDataSourceLoadListener.onFirstLoadStart()
            val pagingKRep = onLoadInitial()

            if (pagingKRep.isSuccessful()) {
                val pagingKData = pagingKRep.data
                if (pagingKData != null) {
                    val records = pagingKData.records
                    if (!records.isNullOrEmpty()) {
                        var pages = pagingKData.pages
                        if (pages <= 0) {
                            val total = pagingKData.total
                            //total 总条数 用总条数/每页数量=总页数
                            pages = total / _pagingKConfig.loadPageSize
                            if (total % _pagingKConfig.loadPageSize > 0) {
                                pages += 1
                            }
                        }
                        val adjacentPageKey = if (pagingKData.current >= pages) null else pagingKData.current + 1
                        val dataAggregation = onDataAggregation(records)
                        val haveMore = null != adjacentPageKey
                        onAddOtherData(true, haveMore, dataAggregation)
                        //添加头部
                        getHeader()?.let {
                            dataAggregation.add(0, it)
                        }
                        //添加底部
                        if (adjacentPageKey == null && getFooter() != null) {
                            dataAggregation.add(getFooter()!!)
                        }
                        _pagingKDataSourceLoadListener.onFirstLoadCompleted(false)
                        callback.onResult(dataAggregation, null, adjacentPageKey)
                        return@launch
                    }
                }
            }
            val dataAggregation: MutableList<DES> = mutableListOf()
            onAddOtherData(isLoadInitial = true, hasMore = false, dataList = dataAggregation)
            //添加头部
            getHeader()?.let {
                dataAggregation.add(0, it)
            }
            //添加底部
            getFooter()?.let {
                dataAggregation.add(it)
            }
            _pagingKDataSourceLoadListener.onFirstLoadCompleted(dataAggregation.isEmpty())
            callback.onResult(dataAggregation, null, null)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DES>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DES>) {
        _coroutineScope.launch(Dispatchers.IO) {
            val loadInitial = onLoadAfter(params.key)
            if (loadInitial.isSuccessful()) {
                val pagingKData = loadInitial.data
                if (pagingKData != null) {
                    val records = pagingKData.records
                    if (!records.isNullOrEmpty()) {
                        var pages = pagingKData.pages
                        if (pages <= 0) {
                            val total = pagingKData.total
                            //total 总条数 用总条数/每页数量=总页数
                            pages = total / _pagingKConfig.loadPageSize
                            if (total % _pagingKConfig.loadPageSize > 0) {
                                pages += 1
                            }
                        }
                        val adjacentPageKey = if (pagingKData.current >= pages) null else pagingKData.current + 1
                        val dataAggregation = onDataAggregation(records)
                        val haveMore = null != adjacentPageKey
                        onAddOtherData(false, haveMore, dataAggregation)
                        //添加底部
                        if (!haveMore) {
                            getFooter()?.let {
                                dataAggregation.add(it)
                            }
                        }
                        callback.onResult(dataAggregation, adjacentPageKey)
                        return@launch
                    }
                }
            }
            val dataAggregation: MutableList<DES> = mutableListOf()
            onAddOtherData(isLoadInitial = false, hasMore = false, dataList = dataAggregation)
            //添加底部
            getFooter()?.let {
                dataAggregation.add(it)
            }
            callback.onResult(dataAggregation, null)
        }
    }
}