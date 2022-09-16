package com.mozhimen.basick.utilk

import org.junit.Test

/**
 * @ClassName TestUtilKNumber
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/14 17:55
 * @Version 1.0
 */
class TestUtilKNumber {
    @Test
    fun normalize() {
        UtilKConsole.printlog(UtilKNumber.normalize(-1, 0..10).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(1, 0..10).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(11, 0..10).toString())
    }

    @Test
    fun angleSin() {
        UtilKConsole.printlog(UtilKNumber.angleSin(1f, 2f).toString())
        UtilKConsole.printlog(UtilKNumber.angleCos(1f, 2f).toString())
    }
}