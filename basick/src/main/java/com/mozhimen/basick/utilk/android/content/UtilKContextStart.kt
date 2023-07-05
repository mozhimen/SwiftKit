package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.commons.IExts_Listener
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:35
 * @Version 1.0
 */

fun Context.startContext(intent: Intent) {
    UtilKContextStart.startContext(this, intent)
}

fun Context.startContext(clazz: Class<*>) {
    UtilKContextStart.startContext(this, clazz)
}

inline fun <reified A : Activity> Context.startContext() {
    UtilKContextStart.startContext<A>(this)
}

inline fun <reified A : Activity> Context.startContext(block: IExts_Listener<Intent>) {
    UtilKContextStart.startContext<A>(this, block)
}

object UtilKContextStart : BaseUtilK() {
    /**
     * 原始跳转
     * @param context Context
     * @param intent Intent
     */
    @JvmStatic
    fun startContext(context: Context, intent: Intent) {
        if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    /**
     * 原始跳转
     * @param context Context
     * @param clazz Class<*>
     */
    @JvmStatic
    fun startContext(context: Context, clazz: Class<*>) {
        startContext(context, Intent(context, clazz))
    }

    /**
     * 不带参数的跳转
     * @param context Context
     */
    @JvmStatic
    inline fun <reified T : Activity> startContext(context: Context) {
        startContext(context, Intent(context, T::class.java))
    }

    /**
     * 带参数的跳转
     * @param context Context
     * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
     */
    @JvmStatic
    inline fun <reified T : Activity> startContext(context: Context, block: IExts_Listener<Intent>) {
        startContext(context, Intent(context, T::class.java).apply(block))
    }

    /**
     * startActivityForResult
     * @param activity Activity
     * @param intent Intent
     * @param requestCode Int
     */
    @JvmStatic
    fun startActivityForResult(activity: Activity, requestCode: Int, intent: Intent) {
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * startActivityForResult
     * @param activity Activity
     */
    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int) {
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java))
    }

    /**
     * startActivityForResult
     * @param activity Activity
     * @param requestCode Int
     * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
     */
    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int, block: IExts_Listener<Intent>) {
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java).apply(block))
    }
}