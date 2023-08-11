package com.mozhimen.underlayk.logk.bases

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.intLogPriority2strSimple
import com.mozhimen.basick.utilk.java.util.UtilKDate

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
        "${UtilKDate.longDate2strDate(timeMillis, CDateFormat.yyyyMMddHHmmss)} | Level: ${priority.intLogPriority2strSimple()} | Tag: $tag : "
}