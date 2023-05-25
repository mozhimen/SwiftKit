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
//        "192.168.33.102".checkIP().printlog()
//        "192.168".checkIP().printlog()
//        "1.1.1.1".checkIP().printlog()
//        "8080".checkPort().printlog()
//        "80".checkPort().printlog()

        "0123456789".checkAllDigits().printlog()
        "12322r".checkAllDigits().printlog()
        "ree".checkAllDigits().printlog()

        "0123456789".checkAllDigits2().printlog()
        "12322r".checkAllDigits2().printlog()
        "ree".checkAllDigits2().printlog()

        "ree123".checkAllDigitsAndAlphabets().printlog()
        "123456".checkAllDigitsAndAlphabets().printlog()
        "123".checkAllDigitsAndAlphabets().printlog()
        "ree".checkAllDigitsAndAlphabets().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".outNumber().printlog()
        "123我是谁AAA&&".outAlphabet().printlog()
        "123我是谁AAA&&".outChinese().printlog()
        "123我是谁AAA&&".outNAC().printlog()
    }
}