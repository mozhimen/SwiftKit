package com.mozhimen.componentk.pagingk.bases

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mozhimen.componentk.pagingk.commons.IPagingKDataSourceLoadListener
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName BasePagingKDataSourceFactory
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:27
 * @Version 1.0
 */
abstract class BasePagingKDataSourceFactory<T>(
    private val _coroutineScope: CoroutineScope,
    private val _pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener
) : DataSource.Factory<Int, T>() {

    private val _liveDataSource = MutableLiveData<DataSource<Int, T>>()

    ///////////////////////////////////////////////////////////////////////////

    override fun create(): DataSource<Int, T> {
        val dataSource = createPagingKDataSource(_coroutineScope, _pagingKDataSourceLoadingListener)
        _liveDataSource.postValue(dataSource)
        return dataSource
    }

    ///////////////////////////////////////////////////////////////////////////

    abstract fun createPagingKDataSource(coroutineScope: CoroutineScope, pagingKDataSourceLoadingListener: IPagingKDataSourceLoadListener): DataSource<Int, T>
}