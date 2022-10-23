package com.mozhimen.basick.utilk

import com.mozhimen.basick.extsk.long2String
import com.mozhimen.basick.extsk.printlog
import org.junit.Test

class TestUtilKDate {
    @Test
    fun getTimeZone() {
        val timeStamp = UtilKDate.getTimeStamp()
        timeStamp.printlog()
        timeStamp.long2String(UtilKDate.FORMAT_yyyyMMddHHmmss).printlog()
    }
}