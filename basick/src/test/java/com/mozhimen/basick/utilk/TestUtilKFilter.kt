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
        "192.168.33.102".checkIP().printlog()
        "192.168".checkIP().printlog()
        "1.1.1.1".checkIP().printlog()
        "8080".checkPort().printlog()
        "80".checkPort().printlog()

        "0123456789".isNumberic().printlog()
        "12322r".isNumberic().printlog()
        "ree".isNumberic().printlog()
        "ree123".hasNumberAndAlphabet().printlog()
        "123456".hasNumberAndAlphabet().printlog()
        "123".hasNumberAndAlphabet().printlog()
        "ree".hasNumberAndAlphabet().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".filterNumber().printlog()
        "123我是谁AAA&&".filterAlphabet().printlog()
        "123我是谁AAA&&".filterChinese().printlog()
        "123我是谁AAA&&".filter().printlog()
    }
}