package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.exts.*
import org.junit.Test


/**
 * @ClassName TestUtilKVerifyUrl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/9 18:02
 * @Version 1.0
 */
class TestUtilKFilter {
    @Test
    fun verify() {
        "192.168.33.102".isIPValid().printlog()
        "192.168".isIPValid().printlog()
        "1.1.1.1".isIPValid().printlog()
        "8080".isPortValid().printlog()
        "80".isPortValid().printlog()

        "0123456789".isNumberic().printlog()
        "12322r".isNumberic().printlog()
        "ree".isNumberic().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".filterNumber().printlog()
        "123我是谁AAA&&".filterAlphabet().printlog()
        "123我是谁AAA&&".filterChinese().printlog()
        "123我是谁AAA&&".filter().printlog()
    }
}