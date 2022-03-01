package com.mozhimen.abilitymk.restfulmk.annors.methods

import androidx.annotation.IntDef

/**
 * @ClassName METHODMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 13:20
 * @Version 1.0
 */
@IntDef(value = [METHODMK.GETMK, METHODMK.POSTMK, METHODMK.PUTMK, METHODMK.DELETEMK])
annotation class METHODMK {
    companion object {
        const val GETMK = 0
        const val POSTMK = 1
        const val PUTMK = 2
        const val DELETEMK = 3
    }
}
