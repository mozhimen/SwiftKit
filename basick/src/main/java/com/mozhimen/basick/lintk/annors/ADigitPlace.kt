package com.mozhimen.basick.lintk.annors

import androidx.annotation.IntDef

/**
 * @ClassName ADigitPlace
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
@IntDef(
    ADigitPlace.PLACE_UNIT,
    ADigitPlace.PLACE_TEN,
    ADigitPlace.PLACE_HUNDRED,
    ADigitPlace.PLACE_THOUSAND,
    ADigitPlace.PLACE_TEN_THOUSAND,
    ADigitPlace.PLACE_HUNDRED_THOUSAND,
    ADigitPlace.PLACE_MILLION
)
annotation class ADigitPlace {
    companion object {
        const val PLACE_UNIT = 1
        const val PLACE_TEN = 2
        const val PLACE_HUNDRED = 3
        const val PLACE_THOUSAND = 4
        const val PLACE_TEN_THOUSAND = 5
        const val PLACE_HUNDRED_THOUSAND = 6
        const val PLACE_MILLION = 7
    }
}
