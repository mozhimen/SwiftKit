package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.datatype.UtilKString
import com.mozhimen.basick.utilk.exts.isUrlValid
import com.mozhimen.basick.utilk.exts.printlog
import org.junit.Test

class TestUtilKString {
    @Test
    fun findFirst() {
        val index = UtilKString.findFirst("5a1fbe000000000000f5", "5a")
        UtilKString.substring("5a1fbe000000000000f5", index, 20).printlog()
        UtilKString.isNotEmpty(",",".").printlog()

        val str = "http://www.sq.com/construction-sites-images"
        UtilKString.getSplitFirst(str,"/").printlog()
        str.isUrlValid().printlog()
    }
}