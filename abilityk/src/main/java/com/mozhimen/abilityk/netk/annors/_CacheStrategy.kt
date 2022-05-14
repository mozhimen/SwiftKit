package com.mozhimen.abilityk.netk.annors

/**
 * @ClassName CacheStrategyK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 12:01
 * @Version 1.0
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class _CacheStrategy(val cacheKey: String, val value: Int = NET_ONLY) {
    companion object {
        const val CACHE_FIRST = 0//请求接口时候先读取本地缓存,再读取接口,接口成功后更新缓存（页面初始化数据）
        const val NET_ONLY = 1//仅仅只请求接口 （一般是分页和独立非列表页）
        const val NET_CACHE = 2//先接口，接口成功后更新缓存 (一般是下拉刷新)
    }
}
