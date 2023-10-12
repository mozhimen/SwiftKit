package com.mozhimen.componentk.pagingk.commons

/**
 * @ClassName IPaginKDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 15:42
 * @Version 1.0
 */
interface IPagingKDataSource<RES, DES> {
    /**
     * 添加其他数据
     * @param isLoadInitial 是否在初始化中调用
     * @param hasMore 是否还有更多
     * @param dataList 数据
     */
    suspend fun onAddOtherData(isLoadInitial: Boolean, hasMore: Boolean, dataList: MutableList<DES>) {}

    /**
     * 数据聚合 将请求回来的原始数据，组装成目标数据
     * @param dataList 请求回来的数据
     */
    suspend fun onDataAggregation(dataList: List<RES>): MutableList<DES>

    /**
     * 获取头部数据
     */
    fun getHeader(): DES? = null

    /**
     * 获取底部数据
     */
    fun getFooter(): DES? = null
}