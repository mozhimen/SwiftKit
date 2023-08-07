package com.mozhimen.basick.utilk.java.lang


/**
 * @ClassName UtilKCharacter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 17:54
 * @Version 1.0
 */
object UtilKCharacter {
    @JvmStatic
    fun getNumericValue(codePoint: Int): Int =
        Character.getNumericValue(codePoint)
}