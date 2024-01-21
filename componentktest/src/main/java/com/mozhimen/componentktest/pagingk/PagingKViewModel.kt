package com.mozhimen.componentktest.pagingk

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel

/**
 * @ClassName PagingKViewModel
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 23:17
 * @Version 1.0
 */
class PagingKViewModel : BaseViewModel() {
    fun getData() = Pager(PagingConfig(pageSize = 1)) {
        DataPagingSource()
    }.flow
}