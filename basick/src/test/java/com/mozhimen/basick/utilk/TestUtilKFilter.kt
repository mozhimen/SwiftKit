package com.mozhimen.basick.utilk

import com.mozhimen.basick.utilk.kotlin.filterAlphabet
import com.mozhimen.basick.utilk.kotlin.filterChinese
import com.mozhimen.basick.utilk.kotlin.filterNAC
import com.mozhimen.basick.utilk.kotlin.filterNumber
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrDigits
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrDigits2
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrDigitsAndAlphabets
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

        "0123456789".matches_ofStrDigits().printlog()
        "12322r".matches_ofStrDigits().printlog()
        "ree".matches_ofStrDigits().printlog()

        "0123456789".matches_ofStrDigits2().printlog()
        "12322r".matches_ofStrDigits2().printlog()
        "ree".matches_ofStrDigits2().printlog()

        "ree123".matches_ofStrDigitsAndAlphabets().printlog()
        "123456".matches_ofStrDigitsAndAlphabets().printlog()
        "123".matches_ofStrDigitsAndAlphabets().printlog()
        "ree".matches_ofStrDigitsAndAlphabets().printlog()
    }

    @Test
    fun filter() {
        "123我是谁AAA&&".filterNumber().printlog()
        "123我是谁AAA&&".filterAlphabet().printlog()
        "123我是谁AAA&&".filterChinese().printlog()
        "123我是谁AAA&&".filterNAC().printlog()
    }
}