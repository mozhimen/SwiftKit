package com.mozhimen.underlayk.logk.commons


/**
 * @ClassName BaseMLogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 18:00
 * @Version 1.0
 */
interface ILogKRecord {
    fun flattenedLog(): String
    fun getFlattened(): String
}