package com.mozhimen.debugk.mos

import android.app.Activity
import java.io.Serializable
import java.lang.reflect.Method

/**
 * @ClassName DebugKMethodMo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 18:06
 * @Version 1.0
 */
data class MDebugKMethod(
    val title: String,
    val desc: String,
    val method: Method,
    val enable: Boolean,
    val target: Any
) : Serializable {
    fun invoke(activity: Activity) {
        method.invoke(target, activity)
    }
}
