package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.java.util.toHump
import com.mozhimen.basick.utilk.java.util.toUnderline
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test


/**
 * @ClassName TestUtilKPattern
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/19 19:36
 * @Version 1.0
 */
class TestUtilKPattern {
    @Test
    fun test() {
        "TestUtilKPattern".toHump().printlog()
        "TestUtilKPattern".toUnderline().printlog()
    }
}