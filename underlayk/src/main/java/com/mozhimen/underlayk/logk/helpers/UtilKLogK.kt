package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.underlayk.logk.LogK


/**
 * @ClassName UtilKLogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/18 16:57
 * @Version 1.0
 */
object UtilKLogK {
    @JvmStatic
    fun et_code_msg(tag: String, code: Int, msg: String) {
        LogK.et(tag, "et_code_msg code $code msg $msg")
    }
}