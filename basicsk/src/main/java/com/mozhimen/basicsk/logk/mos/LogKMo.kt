package com.mozhimen.basicsk.logk.mos

import com.mozhimen.basicsk.utilk.UtilKDate

/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:46
 * @Version 1.0
 */
class LogKMo(private var timeMillis: Long, var level: Int, var tag: String, var log: String) {

    fun flattenedLog(): String =
        getFlattened() + "\n" + log

    fun getFlattened(): String =
        "${
            UtilKDate.long2String(
                timeMillis,
                UtilKDate.FORMAT_yyyyMMddHHmmss
            )
        } | Level: ${LogKType.getTypeName(level)} | Tag: $tag :"
}