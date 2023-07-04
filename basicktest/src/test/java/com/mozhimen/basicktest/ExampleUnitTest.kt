package com.mozhimen.basicktest

import com.mozhimen.basick.sensek.systembar.cons.CProperty
import com.mozhimen.basick.sensek.systembar.cons.CPropertyOr
import com.mozhimen.basick.utilk.kotlin.getByteStr
import com.mozhimen.basick.utilk.kotlin.printlog
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
    fun test() {
        val jsons = arrayOf("2022-01-01", "2022-01-01")
        jsons.joinToString()
    }

    @Test
    fun test1() {
        CPropertyOr.THEME_CUSTOM.getByteStr(16).printlog()
        CPropertyOr.THEME_CUSTOM.inv().getByteStr(16).printlog()
       0b1.inv().getByteStr(16).forEach {
           it.printlog()
       }
    }
}