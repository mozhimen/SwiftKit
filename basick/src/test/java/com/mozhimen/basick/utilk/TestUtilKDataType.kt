package com.mozhimen.basick.utilk

import org.junit.Assert
import org.junit.Test

/**
 * @ClassName UtilKDataType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/8/30 23:57
 * @Version 1.0
 */
class TestUtilKDataType {
    @Test
    fun isTypeMatch() {
        Assert.assertTrue(UtilKDataType.isTypeMatch("123", String::class.java))
    }

    @Test
    fun getTypeName(){
        println(UtilKDataType.getTypeName(0x000000))
    }
}