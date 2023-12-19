package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
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

/////////////////////////////////////////////////////////////////////////////////

inline fun <reified A : Activity> Activity.startActivityAndFinish() {
    UtilKContextStart.startActivityAndFinish<A>(this)
}

inline fun <reified A : Activity> Activity.startActivityAndFinish(block: IExtension_Listener<Intent>) {
    UtilKContextStart.startActivityAndFinish<A>(this, block)
}

/////////////////////////////////////////////////////////////////////////////////

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int) {
    UtilKContextStart.startActivityForResult<T>(this, requestCode)
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, block: IExtension_Listener<Intent>) {
    UtilKContextStart.startActivityForResult<T>(this, requestCode, block)
}

object UtilKContextStart : BaseUtilK() {
    @JvmStatic
    fun startContext(context: Context, intent: Intent) {
        if (context !is Activity) intent.addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmStatic
    fun startContext(context: Context, clazz: Class<*>) {
        startContext(context, UtilKIntent.get(context, clazz))
    }

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context) {
        startContext(context, UtilKIntent.get<T>(context))
    }

    @JvmStatic
    inline fun <reified T : Context> startContext(context: Context, block: IExtension_Listener<Intent>) {
        startContext(context, UtilKIntent.get<T>(context, block))
    }

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    inline fun <reified T : Activity> startActivityAndFinish(activity: Activity) {
        startContext(activity, Intent(activity, T::class.java))
        activity.finish()
    }

    @JvmStatic
    inline fun <reified T : Activity> startActivityAndFinish(activity: Activity, block: IExtension_Listener<Intent>) {
        startContext(activity, Intent(activity, T::class.java).apply(block))
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

    /////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun startContextByPackageName(context: Context, packageName: String) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            setPackage(packageName) //包名
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }// 指定入口,启动类型,包名//入口Main// 启动LAUNCHER,跟MainActivity里面的配置类似
        val resolveInfos = UtilKPackageManager.queryIntentActivities(context, intent, 0) //查询要启动的Activity
        if (resolveInfos.isNotEmpty()) { //如果包名存在
            val resolveInfo = resolveInfos[0]
            val componentName = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)//组装包名和类名
            intent.component = componentName//设置给Intent
            context.startContext(intent)//根据包名类型打开Activity
        } else {
            Toast.makeText(context, "找不到包名;$packageName", Toast.LENGTH_SHORT).show()
        }
    }
}