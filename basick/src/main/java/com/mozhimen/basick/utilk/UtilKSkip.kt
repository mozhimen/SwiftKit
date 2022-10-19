package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @ClassName UtilKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:35
 * @Version 1.0
 */
object UtilKSkip {
    /**
     * 带参数的跳转
     * @param context Context
     * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
     */
    @JvmStatic
    inline fun <reified T> start(context: Context, block: Intent.() -> Unit) where T : Activity {
        val intent = Intent(context, T::class.java)
        intent.block()
        context.startActivity(intent)
    }

    /**
     * 不带参数的跳转
     * @param context Context
     */
    @JvmStatic
    inline fun <reified T> start(context: Context) where T : Activity {
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }
}