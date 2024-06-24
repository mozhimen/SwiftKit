package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.app.ActivityOptions
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.commons.IExt_Listener
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.bases.BaseUtilK
import kotlin.jvm.Throws

/**
 * @ClassName UtilKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:35
 * @Version 1.0
 */
@Throws(ActivityNotFoundException::class)
fun Context.startContext(intent: Intent) =
    UtilKContextStart.startContext(this, intent)

@Throws(ActivityNotFoundException::class)
fun Context.startContext(clazz: Class<*>) {
    UtilKContextStart.startContext(this, clazz)
}

inline fun <reified A : Context> Context.startContext() {
    UtilKContextStart.startContext<A>(this)
}

inline fun <reified A : Context> Context.startContext(block: IExt_Listener<Intent>) {
    UtilKContextStart.startContext<A>(this, block)
}

/////////////////////////////////////////////////////////////////////////////////

inline fun <reified A : Activity> Activity.startActivityAndFinish() {
    UtilKContextStart.startActivityAndFinish<A>(this)
}

inline fun <reified A : Activity> Activity.startActivityAndFinish(block: IExt_Listener<Intent>) {
    UtilKContextStart.startActivityAndFinish<A>(this, block)
}

/////////////////////////////////////////////////////////////////////////////////

inline fun <reified A : Activity> Activity.startActivityAndFinishAnimation_ofActivityOptions() {
    UtilKContextStart.startActivityAndFinishAnimation_ofActivityOptions<A>(this)
}

inline fun <reified A : Activity> Activity.startActivityAndFinishAnimation_ofActivityOptions(block: IExt_Listener<Intent>) {
    UtilKContextStart.startActivityAndFinishAnimation_ofActivityOptions<A>(this, block)
}

/////////////////////////////////////////////////////////////////////////////////

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int) {
    UtilKContextStart.startActivityForResult<T>(this, requestCode)
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, block: IExt_Listener<Intent>) {
    UtilKContextStart.startActivityForResult<T>(this, requestCode, block)
}

/////////////////////////////////////////////////////////////////////////////////

object UtilKContextStart : BaseUtilK() {
    @JvmStatic
    fun startContext(context: Context, intent: Intent): Boolean {
        if (context !is Activity)
            intent.addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "startContext: ", e)
            return false
        }
    }

    @JvmStatic
    fun startContext(context: Context, intent: Intent, options: Bundle): Boolean {
        if (context !is Activity)
            intent.addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent, options)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "startContext: ", e)
            return false
        }
    }

    @JvmStatic
    fun startContext(context: Context, clazz: Class<*>): Boolean =
        startContext(context, UtilKIntent.get(context, clazz))

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context): Boolean =
        startContext(context, UtilKIntent.get<T>(context))

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context, block: IExt_Listener<Intent>): Boolean =
        startContext(context, UtilKIntent.get<T>(context, block))

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    inline fun <reified T : Activity> startActivityAndFinish(activity: Activity) {
        startContext(activity, Intent(activity, T::class.java)).also { activity.finish() }
    }

    @JvmStatic
    inline fun <reified T : Activity> startActivityAndFinish(activity: Activity, block: IExt_Listener<Intent>): Boolean =
        startContext(activity, Intent(activity, T::class.java).apply(block)).also { activity.finish() }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    inline fun <reified T : Activity> startActivityAndFinishAnimation_ofActivityOptions(activity: Activity): Boolean =
        startContext(activity, Intent(activity, T::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()).also { activity.finish() }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    inline fun <reified T : Activity> startActivityAndFinishAnimation_ofActivityOptions(activity: Activity, block: IExt_Listener<Intent>): Boolean =
        startContext(activity, Intent(activity, T::class.java).apply(block), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()).also { activity.finish() }


    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun startActivityForResult(activity: Activity, requestCode: Int, intent: Intent): Boolean =
        try {
            activity.startActivityForResult(intent, requestCode)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "startActivityForResult: ", e)
            false
        }

    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int): Boolean =
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java))

    @JvmStatic
    inline fun <reified T : Activity> startActivityForResult(activity: Activity, requestCode: Int, block: IExt_Listener<Intent>): Boolean =
        startActivityForResult(activity, requestCode, Intent(activity, T::class.java).apply(block))

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun startContextByPackageName(context: Context, strPackageName: String): Boolean {
        val intent = UtilKIntentWrapper.getMainLauncher_ofComponent(context, strPackageName) ?: return false
        return context.startContext(intent)
    }

    @JvmStatic
    fun startContextByPackageName(context: Context, strPackageName: String, strActivityName: String): Boolean =
        context.startContext(UtilKIntentWrapper.getComponent(strPackageName, strActivityName))

    /////////////////////////////////////////////////////////////////////////////////
}