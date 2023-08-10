package com.mozhimen.underlayk.logk.bases

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.intLogPriority2str
import com.mozhimen.basick.utilk.java.util.UtilKDate

/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:46
 * @Version 1.0
 */
class BaseLogKRecord(private var _timeMillis: Long, var priority: Int, var tag: String, var msg: String) {

    fun flattenedLog(): String =
        getFlattened() + "\n" + msg

    fun getFlattened(): String =
        "${UtilKDate.longDate2strDate(_timeMillis, CDateFormat.yyyyMMddHHmmss)} | Level: ${priority.intLogPriority2str()} | Tag: $tag :"
}