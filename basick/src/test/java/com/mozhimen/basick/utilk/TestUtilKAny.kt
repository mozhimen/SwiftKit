package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.UtilKAny
import com.mozhimen.basick.utilk.kotlin.isObjPrimitive
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Assert
import org.junit.Test

/**
 * @ClassName UtilKDataType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/8/30 23:57
 * @Version 1.0
 */
class TestUtilKAny {
    @Test
    fun isObjTypeMatch() {
        Assert.assertTrue(UtilKAny.isObjTypeMatch("123", String::class.java))
    }

    @Test
    fun getTypeName() {
        UtilKAny.getObjTypeName(0x000000).printlog()
    }

    @Test
    fun isObjPrimitive() {
        2.isObjPrimitive().printlog()
    }
}