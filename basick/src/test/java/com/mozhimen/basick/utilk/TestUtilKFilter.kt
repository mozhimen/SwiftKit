package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.filterAlphabet
import com.mozhimen.basick.utilk.kotlin.filterChinese
import com.mozhimen.basick.utilk.kotlin.filterNAC
import com.mozhimen.basick.utilk.kotlin.filterNumber
import com.mozhimen.basick.utilk.kotlin.text.isStrDigits
import com.mozhimen.basick.utilk.kotlin.text.isStrDigits2
import com.mozhimen.basick.utilk.kotlin.text.isStrDigitsAndAlphabets
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

        "0123456789".isStrDigits().printlog()
        "12322r".isStrDigits().printlog()
        "ree".isStrDigits().printlog()

        "0123456789".isStrDigits2().printlog()
        "12322r".isStrDigits2().printlog()
        "ree".isStrDigits2().printlog()

        "ree123".isStrDigitsAndAlphabets().printlog()
        "123456".isStrDigitsAndAlphabets().printlog()
        "123".isStrDigitsAndAlphabets().printlog()
        "ree".isStrDigitsAndAlphabets().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".filterNumber().printlog()
        "123我是谁AAA&&".filterAlphabet().printlog()
        "123我是谁AAA&&".filterChinese().printlog()
        "123我是谁AAA&&".filterNAC().printlog()
    }
}