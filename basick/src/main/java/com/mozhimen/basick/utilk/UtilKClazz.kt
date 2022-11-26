package com.mozhimen.basick.utilk

/**
 * @ClassName UtilKClazz
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 23:54
 * @Version 1.0
 */
object UtilKClazz {
    fun wrapLocation(cla: Class<*>, lineNumber: Int): String {
        return ".(" + cla.simpleName + ".java:" + lineNumber + ")"
    }
}