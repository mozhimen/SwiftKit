package com.mozhimen.underlayk.logk.commons


/**
 * @ClassName ILogKJsonParser
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 13:08
 * @Version 1.0
 */
interface ILogKJsonParser {
    fun toJson(src: Any): String
}