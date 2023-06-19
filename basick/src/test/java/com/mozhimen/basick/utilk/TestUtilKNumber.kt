package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.datatype.UtilKNumber
import com.mozhimen.basick.utilk.java.printlog
import com.mozhimen.basick.utilk.java.UtilKConsole
import com.mozhimen.basick.utilk.math.UtilKTriangle
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
        UtilKConsole.printlog(UtilKNumber.normalize(-1f, 0f, 10f).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(1f, 0f, 10f).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(11f, 0f, 10f).toString())
        UtilKConsole.printlog(UtilKNumber.normalize(0f, 0f, 0f).toString())
    }

    @Test
    fun angleSin() {
        UtilKConsole.printlog(UtilKTriangle.angleSin(1f, 2f).toString())
        UtilKConsole.printlog(UtilKTriangle.angleCos(1f, 2f).toString())
    }

    @Test
    fun percent() {
//        UtilKConsole.printlog(UtilKNumber.percent(0f, 0, 100).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(-1f, 0, 100).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(101f, 0, 100).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(50f, 0, 100).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(33f, 0, 100).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(23f, 0, 99).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(23f, 23, 99).toString())
//        UtilKConsole.printlog(UtilKNumber.percent(23f, 0, 0).toString())
        UtilKNumber.percent(23f, 33f, 44f).printlog()
        UtilKNumber.percent(-23f, 33f, 44f).printlog()
    }

    @Test
    fun keepTwo() {
        UtilKNumber.keepTwoDigits(2.33333f).printlog()
    }

    @Test
    fun getNewRotate() {
        val origin = 45f
        val last = 5f
        val current = 315f
        val temp = current - origin + when {
            (current - last) <= -180f -> 360f
            (current - last) >= 180f -> -360f
            else -> 0f
        }
        var real = 0f
        real += temp
        real.printlog()
    }
}