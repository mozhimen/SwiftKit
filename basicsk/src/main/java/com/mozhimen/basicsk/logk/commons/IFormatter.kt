package com.mozhimen.basicsk.logk.commons

/**
 * @ClassName ILogKFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:33
 * @Version 1.0
 */
interface IFormatter<T> {
    fun format(data: T): String?
}