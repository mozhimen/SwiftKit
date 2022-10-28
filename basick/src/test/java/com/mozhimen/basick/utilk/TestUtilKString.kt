package com.mozhimen.basick.utilk

import com.mozhimen.basick.extsk.printlog
import org.junit.Test

class TestUtilKString {
    @Test
    fun findFirst() {
        val index = UtilKString.findFirst("5a1fbe000000000000f5", "5a")
        UtilKString.substring("5a1fbe000000000000f5", index, 20).printlog()
    }
}