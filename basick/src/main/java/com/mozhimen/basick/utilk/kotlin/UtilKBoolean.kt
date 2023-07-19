package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.I_Listener


/**
 * @ClassName UtilKBoolean
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/17 10:36
 * @Version 1.0
 */
fun Boolean.ifOrElse(onTrue: I_Listener, onFalse: I_Listener) {
    UtilKBoolean.ifOrElse(this, onTrue, onFalse)
}

object UtilKBoolean {
    @JvmStatic
    fun ifOrElse(boolean: Boolean, onTrue: I_Listener, onFalse: I_Listener) {
        if (boolean) onTrue.invoke() else onFalse.invoke()
    }
}