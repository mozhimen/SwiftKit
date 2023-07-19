package com.mozhimen.basick

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mozhimen.basick.utilk.kotlin.text.UtilKGenerateNo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @ClassName TestUtilKGenerateNo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/19 19:25
 * @Version 1.0
 */
@RunWith(AndroidJUnit4::class)
class TestUtilKGenerateNo {
    @Test
    fun test() {
        assertEquals(UtilKGenerateNo.generateSerialNo(), "000001")
        assertEquals(UtilKGenerateNo.generateSerialNo(), "000002")
    }
}