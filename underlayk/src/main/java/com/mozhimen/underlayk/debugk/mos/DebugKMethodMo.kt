package com.mozhimen.underlayk.debugk.mos

import android.content.Context
import java.lang.reflect.Method

/**
 * @ClassName DebugKMethodMo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 18:06
 * @Version 1.0
 */
data class DebugKMethodMo(
    val name: String,
    val desc: String,
    val method: Method,
    val enable: Boolean,
    val target: Any
) {
    fun invoke(context: Context) {
        method.invoke(target, context)
    }
}
