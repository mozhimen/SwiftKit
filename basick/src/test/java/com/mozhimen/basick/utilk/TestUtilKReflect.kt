package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.lang.UtilKReflect
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKReflect
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/29 0:19
 * @Version 1.0
 */
class TestUtilKReflect {
    @Test
    fun test() {
        val reflectTest = ReflectTest()
        UtilKReflect.getField_ofInt(reflectTest,"field").printlog()
    }

    class ReflectTest {
        val field = 1
    }
}