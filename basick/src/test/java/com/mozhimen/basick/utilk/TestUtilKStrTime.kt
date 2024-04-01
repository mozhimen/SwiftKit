package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.util.longHour2longMillis
import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.kotlin.isStrTime_ofHH
import com.mozhimen.basick.utilk.kotlin.isStrTime_ofHHmm
import com.mozhimen.basick.utilk.java.util.longMinute2longMillis
import com.mozhimen.basick.utilk.java.util.longSecond2longMillis
import org.junit.Test

/**
 * @ClassName TestUtilKTime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/31 18:41
 * @Version 1.0
 */
class TestUtilKStrTime {
    @Test
    fun test() {
        "00".isStrTime_ofHH("00:11").printlog()
        "10".isStrTime_ofHH("00:11").printlog()
        "10".isStrTime_ofHH("00:00").printlog()
        "00".isStrTime_ofHH("00:00").printlog()

        ("00" to "11").isStrTime_ofHHmm("00:11").printlog()
        ("00" to "10").isStrTime_ofHHmm("00:11").printlog()
        ("01" to "11").isStrTime_ofHHmm("00:11").printlog()

        1L.longSecond2longMillis().printlog()
        1L.longMinute2longMillis().printlog()
        1L.longHour2longMillis().printlog()
    }
}