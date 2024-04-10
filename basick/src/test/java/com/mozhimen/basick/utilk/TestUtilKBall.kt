package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.kotlin.math.UtilKMathBall
import org.junit.Test

/**
 * @ClassName TestUtilKBall
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/23 21:06
 * @Version 1.0
 */
class TestUtilKBall {
    @Test
    fun distance() {
        UtilKMathBall.getDistance(31.250101, 0.0,31.150101, 0.0).printlog()
    }
}