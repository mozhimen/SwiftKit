package com.mozhimen.basick

import com.mozhimen.basick.utilk.UtilKConsole
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun fun_test() {
        val variate: String? = "fun"
        UtilKConsole.printlog("start")
        variate ?: return//测试此语句的是否真的导致退出
        UtilKConsole.printlog("end")
    }
}