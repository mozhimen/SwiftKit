package com.mozhimen.abilityk.restfulk.annors.methods

import androidx.annotation.IntDef

/**
 * @ClassName METHODK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 13:20
 * @Version 1.0
 */
@IntDef(value = [METHOD.GETK, METHOD.POSTK, METHOD.PUTK, METHOD.DELETEK])
annotation class METHOD {
    companion object {
        const val GETK = 0
        const val POSTK = 1
        const val PUTK = 2
        const val DELETEK = 3
    }
}
