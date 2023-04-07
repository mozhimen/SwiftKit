package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.datatype.UtilKString
import com.mozhimen.basick.utilk.exts.checkUrl
import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test

class TestUtilKString {
    @Test
    fun findFirst() {
        val index = UtilKString.findFirst("5a1fbe000000000000f5", "5a")
        UtilKString.subStr("5a1fbe000000000000f5", index, 20).printlog()
        UtilKString.isNotEmpty(",",".").printlog()

        val str = "http://www.sq.com/construction-sites-images"
        UtilKString.getSplitFirst(str,"/").printlog()
        str.checkUrl().printlog()
    }
}