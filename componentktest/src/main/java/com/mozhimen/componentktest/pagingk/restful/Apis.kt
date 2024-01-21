package com.mozhimen.componentktest.pagingk.restful

import com.mozhimen.componentktest.pagingk.restful.mos.DataRes
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @ClassName Apis
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 16:36
 * @Version 1.0
 */
interface Apis {
    /**
     * 获取数据
     */
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId: Int): DataRes
}