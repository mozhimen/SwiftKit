package com.mozhimen.basick

import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.extsk.printlog
import com.mozhimen.basick.utilk.UtilKConsole
import com.mozhimen.basick.utilk.UtilKEncryptMD5
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

    @Test
    fun test_class() {
        BaseKActivityVBVM::class.java.toString().printlog()
        BaseKActivityVBVM::class.java.superclass.toString().printlog()
        BaseKActivityVBVM::class.java.superclass.toString().contains("Activity").printlog()
    }

    @Test
    fun md5() {
        val a = "EIST0110140001"
        val b = "1665565543326"
        UtilKEncryptMD5.encrypt32("hsq$a$b").printlog()
    }
}