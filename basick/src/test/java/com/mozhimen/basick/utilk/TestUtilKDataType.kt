package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.datatype.UtilKDataType
import com.mozhimen.basick.utilk.java.printlog
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
    fun getTypeName() {
        UtilKDataType.getTypeName(0x000000).printlog()
    }

}