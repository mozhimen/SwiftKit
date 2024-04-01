package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.util.strUnderline2strHump
import com.mozhimen.basick.utilk.java.util.strHump2strUnderline
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test


/**
 * @ClassName TestUtilKPattern
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/19 19:36
 * @Version 1.0
 */
class TestUtilKMatcherWrapper {
    @Test
    fun test() {
        "TestUtilKPattern".strUnderline2strHump().printlog()
        "TestUtilKPattern".strHump2strUnderline().printlog()
    }
}