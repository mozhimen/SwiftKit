package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.getStrByte
import com.mozhimen.basick.utilk.kotlin.intByte2strByte
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
        (0b11 or 0b100).getStrByte(8).printlog()
        (0b11 or 0b100).intByte2strByte().printlog()
        (0b1 shl 7).getStrByte(8).printlog()

        (0b11 shl 8).getStrByte(10).printlog()
    }
}