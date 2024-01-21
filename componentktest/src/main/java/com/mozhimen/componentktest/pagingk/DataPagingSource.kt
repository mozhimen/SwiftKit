package com.mozhimen.componentktest.pagingk

import android.util.Log
import androidx.paging.PagingSource
import com.mozhimen.componentktest.pagingk.restful.Repository
import com.mozhimen.componentktest.pagingk.restful.mos.DataRes
import java.io.IOException
import java.lang.Exception

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc 数据源
 */
class DataPagingSource : PagingSource<Int, DataRes.DataBean.DatasBean>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataRes.DataBean.DatasBean> {
        return try {
            //页码未定义置为1
            val currentPage = params.key ?: 1
            //仓库层请求数据
            Log.d("MainActivity", "请求第${currentPage}页")
            val dataRes = Repository.getDataOnBack(currentPage)
            //当前页码 小于 总页码 页面加1
            val nextPage = if (currentPage < (dataRes.data?.pageCount ?: 0)) {
                currentPage + 1
            } else {
                null//没有更多数据
            }

            LoadResult.Page(
                data = dataRes.data.datas,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            if (e is IOException) {
                Log.d("测试错误数据", "-------连接失败")
            }
            Log.d("测试错误数据", "-------${e.message}")
            LoadResult.Error(throwable = e)
        }
    }
}