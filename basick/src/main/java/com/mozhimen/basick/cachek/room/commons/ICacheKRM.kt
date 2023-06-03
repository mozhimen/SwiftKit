package com.mozhimen.basick.cachek.room.commons

/**
 * @ClassName ICacheKRM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/4 0:38
 * @Version 1.0
 */
interface ICacheKRM {
    /**
     * 保存cache
     * @param key String
     * @param body T
     */
    fun <T> saveCache(key: String, body: T)

    /**
     * 获取cache
     * @param key String
     * @return T?
     */
    fun <T> getCache(key: String): T?

    
}