package com.mozhimen.basicsmk.logmk.commons

/**
 * @ClassName ILogMKFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:33
 * @Version 1.0
 */
interface ILogMKFormatter<T> {
    fun format(data: T): String?
}