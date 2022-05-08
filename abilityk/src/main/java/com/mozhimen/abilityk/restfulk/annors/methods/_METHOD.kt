package com.mozhimen.abilityk.restfulk.annors.methods

import androidx.annotation.IntDef

/**
 * @ClassName METHODK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 13:20
 * @Version 1.0
 */
@IntDef(value = [_METHOD._GETK, _METHOD._POSTK, _METHOD._PUTK, _METHOD._DELETEK])
annotation class _METHOD {
    companion object {
        const val _GETK = 0
        const val _POSTK = 1
        const val _PUTK = 2
        const val _DELETEK = 3
    }
}
