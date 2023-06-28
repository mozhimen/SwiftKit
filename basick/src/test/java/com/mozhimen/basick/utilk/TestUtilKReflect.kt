package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.lang.UtilKReflect
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
        UtilKReflect.getFieldValue(reflectTest,"com.mozhimen.basick.utilk.TestUtilKReflect.ReflectTest")
    }

    class ReflectTest {
        val field = 1
    }
}