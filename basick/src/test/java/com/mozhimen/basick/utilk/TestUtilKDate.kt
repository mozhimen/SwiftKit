package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.device.UtilKDate
import com.mozhimen.basick.utilk.exts.long2Str
import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test

class TestUtilKDate {
    @Test
    fun getTimeZone() {
        val nowDate = UtilKDate.getNowDate()
        val nowTime = UtilKDate.getNowLong()
        val nowString = UtilKDate.getNowStr()
        UtilKDate.apply {
            nowDate.printlog()
            long2Date(nowTime).printlog()
            str2Date(nowString, FORMAT_yyyyMMddHHmmssS).printlog()
            nowTime.printlog()
            date2Long(nowDate).printlog()
            str2Long(nowString, FORMAT_yyyyMMddHHmmssS).printlog()
        }

        val todayString = UtilKDate.getTodayStr()
        todayString.printlog()
        UtilKDate.getNowLong().printlog()
        UtilKDate.getTodayLong().printlog()
        UtilKDate.getTodayLong().long2Str(UtilKDate.FORMAT_yyyyMMdd).printlog()
    }
}