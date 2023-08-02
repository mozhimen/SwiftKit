package com.mozhimen.basick.utilk.java.util

import java.util.UUID

/**
 * @ClassName UtilKStrUuid
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:51
 * @Version 1.0
 */
object UtilKUuid {
    /**
     * 生成随机字符串
     * @return String
     */
    @JvmStatic
    fun getStrUuid(): String =
            UUID.randomUUID().toString().replace("-", "")
}