package com.mozhimen.componentktest.pagingk.restful

import androidx.annotation.WorkerThread
import com.mozhimen.componentktest.pagingk.restful.mos.DataRes

/**
 * @ClassName Repository
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 16:37
 * @Version 1.0
 */
object Repository {
    /**
     * 查询护理数据
     */
    @WorkerThread
    suspend fun getDataOnBack(pageId: Int): DataRes =
        ApiFactory.apis.getData(pageId)
}