package com.mozhimen.basick.utilk

import com.mozhimen.basick.extsk.isIPValid
import com.mozhimen.basick.extsk.isPortValid
import com.mozhimen.basick.extsk.printlog
import com.mozhimen.basick.utilk.verify.UtilKVerifyUrl
import org.junit.Test


/**
 * @ClassName TestUtilKVerifyUrl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/9 18:02
 * @Version 1.0
 */
class TestUtilKVerifyUrl {
    @Test
    fun verify() {
        "192.168.33.102".isIPValid().printlog()
        "192.168".isIPValid().printlog()
        "1.1.1.1".isIPValid().printlog()
        "8080".isPortValid().printlog()
        "80".isPortValid().printlog()
    }
}