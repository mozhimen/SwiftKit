package com.mozhimen.basick.utilk.context

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
object UtilKActivitySkip {
    /**
     * 不带参数的跳转
     * @param activity Activity
     */
    @JvmStatic
    inline fun <reified T> start(activity: Activity) where T : Activity {
        val intent = Intent(activity, T::class.java)
        activity.startActivity(intent)
    }

    /**
     * 原始跳转
     * @param activity Activity
     * @param intent Intent
     */
    @JvmStatic
    fun start(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
    }

    /**
     * 带参数的跳转
     * @param activity Activity
     * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
     */
    @JvmStatic
    inline fun <reified T> start(activity: Activity, block: Intent.() -> Unit) where T : Activity {
        val intent = Intent(activity, T::class.java)
        intent.block()
        activity.startActivity(intent)
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

    /**
     * 原始跳转
     * @param context Context
     * @param intent Intent
     */
    @JvmStatic
    fun start(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

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
}