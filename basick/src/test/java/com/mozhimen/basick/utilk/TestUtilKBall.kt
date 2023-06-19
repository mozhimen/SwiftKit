package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.printlog
import com.mozhimen.basick.utilk.math.UtilKBall
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
        UtilKBall.distance(31.250101, 0.0,31.150101, 0.0).printlog()
    }
}