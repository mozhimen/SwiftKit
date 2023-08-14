package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.commons.IExtension_Listener
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

inline fun <reified A : Context> Context.startContext() {
    UtilKContextStart.startContext<A>(this)
}

inline fun <reified A : Context> Context.startContext(block: IExtension_Listener<Intent>) {
    UtilKContextStart.startContext<A>(this, block)
}

inline fun <reified A : Activity> Activity.startActivityAndFinish() {
    UtilKContextStart.startActivityAndFinish<A>(this)
}

object UtilKContextStart : BaseUtilK() {
    @JvmStatic
    fun startContext(context: Context, intent: Intent) {
        if (context !is Activity) intent.addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmStatic
    fun startContext(context: Context, clazz: Class<*>) {
        startContext(context, context.createIntent(clazz))
    }

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context) {
        startContext(context, context.createIntent<T>())
    }

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context, block: IExtension_Listener<Intent>) {
        startContext(context, context.createIntent<T>(block))
    }

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    inline fun <reified T : Activity> startActivityAndFinish(activity: Activity) {
        startContext(activity, Intent(activity, T::class.java))
        activity.finish()
    }

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun startActivityForResult(activity: Activity, requestCode: Int, intent: Intent) {
        activity.startActivityForResult(intent, requestCode)
    }

    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int) {
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java))
    }

    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int, block: IExtension_Listener<Intent>) {
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java).apply(block))
    }
}