package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.longFileSize2strFileSize
import com.mozhimen.basick.utilk.kotlin.printlog
import org.junit.Test

/**
 * @ClassName TestUtilKLongFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/27 16:51
 * @Version 1.0
 */
class TestUtilKLongFormat {
    @Test
    fun longFileSize2strFileSize() {
        1073000000L.longFileSize2strFileSize().printlog()
    }
}