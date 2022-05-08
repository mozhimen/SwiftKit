package com.mozhimen.basicsk.utilk

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * @ClassName UtilKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:35
 * @Version 1.0
 */
object UtilKSkip {
    /**
     * 不带参数的跳转
     * @param context Context
     * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
     */
    inline fun <reified T> start(context: Context, block: Intent.() -> Unit) {
        val intent = Intent(context, T::class.java)
        intent.block()
        context.startActivity(intent)
    }

    /**
     * 带参数的跳转
     * @param context Context
     */
    inline fun <reified T> start(context: Context) {
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }
}