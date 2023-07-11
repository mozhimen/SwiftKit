package com.mozhimen.basick.cachek.commons

/**
 * @ClassName ICacheK
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/4 13:56
 * @Version 1.0
 */
interface ICacheK<P : ICacheKProvider> {
    /**
     * 携带sp名称
     * @param name String
     * @return T
     */
    fun with(name: String): P
}