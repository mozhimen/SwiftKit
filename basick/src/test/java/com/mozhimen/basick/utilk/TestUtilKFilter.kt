package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.text.isAllDigits
import com.mozhimen.basick.utilk.kotlin.text.isAllDigits2
import com.mozhimen.basick.utilk.kotlin.text.isAllDigitsAndAlphabets
import com.mozhimen.basick.utilk.kotlin.text.outAlphabet
import com.mozhimen.basick.utilk.kotlin.text.outChinese
import com.mozhimen.basick.utilk.kotlin.text.outNAC
import com.mozhimen.basick.utilk.kotlin.text.outNumber
import com.mozhimen.basick.utilk.kotlin.printlog
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

        "0123456789".isAllDigits().printlog()
        "12322r".isAllDigits().printlog()
        "ree".isAllDigits().printlog()

        "0123456789".isAllDigits2().printlog()
        "12322r".isAllDigits2().printlog()
        "ree".isAllDigits2().printlog()

        "ree123".isAllDigitsAndAlphabets().printlog()
        "123456".isAllDigitsAndAlphabets().printlog()
        "123".isAllDigitsAndAlphabets().printlog()
        "ree".isAllDigitsAndAlphabets().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".outNumber().printlog()
        "123我是谁AAA&&".outAlphabet().printlog()
        "123我是谁AAA&&".outChinese().printlog()
        "123我是谁AAA&&".outNAC().printlog()
    }
}