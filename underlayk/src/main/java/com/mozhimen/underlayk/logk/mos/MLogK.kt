package com.mozhimen.underlayk.logk.mos

import com.mozhimen.basick.elemk.cons.CDateFormat
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basick.elemk.cons.CLogType
import com.mozhimen.underlayk.logk.helpers.LogKHelper

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
        return "${UtilKDate.long2Str(_timeMillis, CDateFormat.yyyyMMddHHmmss)} | Level: ${LogKHelper.getTypeName(level)} | Tag: $tag :"
    }
}