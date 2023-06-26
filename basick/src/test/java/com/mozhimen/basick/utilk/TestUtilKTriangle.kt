package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.kotlin.math.UtilKTriangle
import org.junit.Test


/**
 * @ClassName TestUtilKTriangle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/10 16:59
 * @Version 1.0
 */
class TestUtilKTriangle {
    @Test
    fun calculate(){
        UtilKTriangle.getOppositeLength(6.0,30.0).printlog()
        UtilKTriangle.getAdjacentLength(6.0,60.0).printlog()
    }
}