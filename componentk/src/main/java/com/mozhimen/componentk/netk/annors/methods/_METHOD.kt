package com.mozhimen.componentk.netk.annors.methods

import androidx.annotation.IntDef

/**
 * @ClassName _METHOD
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 13:20
 * @Version 1.0
 */
@IntDef(value = [_METHOD._GET, _METHOD._POST, _METHOD._PUT, _METHOD._DELETE])
annotation class _METHOD {
    companion object {
        const val _GET = 0
        const val _POST = 1
        const val _PUT = 2
        const val _DELETE = 3
    }
}
