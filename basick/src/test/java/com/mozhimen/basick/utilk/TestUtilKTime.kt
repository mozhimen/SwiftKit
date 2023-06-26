package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.printlog
import com.mozhimen.basick.utilk.java.util.isOnTheHourOf
import com.mozhimen.basick.utilk.java.util.isOnTheMinuteOf
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
        "00".isOnTheHourOf("00:11").printlog()
        "10".isOnTheHourOf("00:11").printlog()
        "10".isOnTheHourOf("00:00").printlog()
        "00".isOnTheHourOf("00:00").printlog()

        ("00" to "11").isOnTheMinuteOf("00:11").printlog()
        ("00" to "10").isOnTheMinuteOf("00:11").printlog()
        ("01" to "11").isOnTheMinuteOf("00:11").printlog()
    }
}