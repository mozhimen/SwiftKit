package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.util.hour2millis
import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.java.util.isTimeAtHourOf
import com.mozhimen.basick.utilk.java.util.isTimeAtMinuteOf
import com.mozhimen.basick.utilk.java.util.minute2millis
import com.mozhimen.basick.utilk.java.util.second2millis
import org.junit.Test

/**
 * @ClassName TestUtilKTime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/31 18:41
 * @Version 1.0
 */
class TestUtilKTime {
    @Test
    fun test() {
        "00".isTimeAtHourOf("00:11").printlog()
        "10".isTimeAtHourOf("00:11").printlog()
        "10".isTimeAtHourOf("00:00").printlog()
        "00".isTimeAtHourOf("00:00").printlog()

        ("00" to "11").isTimeAtMinuteOf("00:11").printlog()
        ("00" to "10").isTimeAtMinuteOf("00:11").printlog()
        ("01" to "11").isTimeAtMinuteOf("00:11").printlog()

        1L.second2millis().printlog()
        1L.minute2millis().printlog()
        1L.hour2millis().printlog()
    }
}