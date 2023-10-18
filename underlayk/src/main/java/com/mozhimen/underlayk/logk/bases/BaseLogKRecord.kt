package com.mozhimen.underlayk.logk.bases

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basick.utilk.kotlin.intLogPriority2strLogSimple

/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:46
 * @Version 1.0
 */
open class BaseLogKRecord(var timeMillis: Long, var priority: Int, var tag: String, var msg: String) {

    open fun flattenedLog(): String =
        getFlattened() + msg

    open fun getFlattened(): String =
        "${UtilKDate.longDate2strDate(timeMillis, CDateFormat.yyyy_MM_dd_HH_mm_ss)} | Level: ${priority.intLogPriority2strLogSimple()} | Tag: $tag : "
}