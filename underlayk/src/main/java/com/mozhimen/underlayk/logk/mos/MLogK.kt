package com.mozhimen.underlayk.logk.mos

import com.mozhimen.basick.elemk.cons.CDateFormat
import com.mozhimen.basick.utilk.os.UtilKDate
import com.mozhimen.basick.elemk.cons.CLogType

/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:46
 * @Version 1.0
 */
class MLogK(private var _timeMillis: Long, var level: Int, var tag: String, var log: String) {

    fun flattenedLog(): String {
        return getFlattened() + "\n" + log
    }

    fun getFlattened(): String {
        return "${UtilKDate.long2Str(_timeMillis, CDateFormat.yyyyMMddHHmmss)} | Level: ${CLogType.getTypeName(level)} | Tag: $tag :"
    }
}