package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.getByteStr
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKInt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 18:30
 * @Version 1.0
 */
class TestUtilKInt {
    @Test
    fun test() {
        (0b11 or 0b100).getByteStr(8).printlog()
        (0b11 or 0b100).getByteStr().printlog()
    }
}