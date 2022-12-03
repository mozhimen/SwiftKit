package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.exts.printlog
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
        UtilKConsole.printlog(UtilKNumber.normalize(-1f, 0, 10).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(1f, 0, 10).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(11f, 0, 10).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(0f, 0, 0).toString())
    }

    @Test
    fun angleSin() {
        UtilKConsole.printlog(UtilKNumber.angleSin(1f, 2f).toString())
        UtilKConsole.printlog(UtilKNumber.angleCos(1f, 2f).toString())
    }

    @Test
    fun percent() {
        UtilKConsole.printlog(UtilKNumber.percent(0f, 0, 100).toString())
        UtilKConsole.printlog(UtilKNumber.percent(-1f, 0, 100).toString())
        UtilKConsole.printlog(UtilKNumber.percent(101f, 0, 100).toString())
        UtilKConsole.printlog(UtilKNumber.percent(50f, 0, 100).toString())
        UtilKConsole.printlog(UtilKNumber.percent(33f, 0, 100).toString())
        UtilKConsole.printlog(UtilKNumber.percent(23f, 0, 99).toString())
        UtilKConsole.printlog(UtilKNumber.percent(23f, 23, 99).toString())
        UtilKConsole.printlog(UtilKNumber.percent(23f, 0, 0).toString())
    }

    @Test
    fun keepTwo() {
        UtilKNumber.keepTwoDigits(2.33333f).printlog()
    }
}