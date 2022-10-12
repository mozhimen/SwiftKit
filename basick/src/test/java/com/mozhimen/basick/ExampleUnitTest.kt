package com.mozhimen.basick

import com.mozhimen.basick.extsk.printlog
import com.mozhimen.basick.utilk.UtilKConsole
import com.mozhimen.basick.utilk.UtilKDate
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
    fun testMd5() {
        val deviceSerialNum = "3K8D33K3"
        val timestamp = UtilKDate.getTimeStamp()//"1665559396"
        val signature1 = UtilKEncryptMD5.encrypt16("hsq$deviceSerialNum$timestamp")
        val signature2 = UtilKEncryptMD5.encryptLower32("hsq$deviceSerialNum$timestamp")
        val signature3 = UtilKEncryptMD5.encrypt32("hsq$deviceSerialNum$timestamp")
        deviceSerialNum.printlog()
        timestamp.printlog()
        signature1.printlog()
        signature2.printlog()
        signature3.printlog()
    }
}