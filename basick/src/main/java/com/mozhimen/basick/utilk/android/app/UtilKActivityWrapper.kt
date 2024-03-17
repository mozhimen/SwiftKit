package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.kotlin.UtilKClazz

/**
 * @ClassName UtilKActivityWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 17:27
 * @Version 1.0
 */
fun <A : Annotation> Activity.getAnnotation(annotationClazz: Class<A>): A? =
    UtilKActivityWrapper.getAnnotation(this, annotationClazz)

fun Activity.isFinishingOrDestroyed(): Boolean =
    UtilKActivityWrapper.isFinishingOrDestroyed(this)

/////////////////////////////////////////////////////////////////////////

object UtilKActivityWrapper {
    @JvmStatic
    @OApiUse_BaseApplication
    fun get(context: Context): Activity? =
        get(context, false)

    //判断context是否是Activity 这里注意一定要再Application中加入StackK并初始化
    @JvmStatic
    @OApiUse_BaseApplication
    @OptIn(OApiInit_InApplication::class)
    fun get(context: Context, returnTopIfNull: Boolean): Activity? {
        var tempContext = context
        if (tempContext is Activity) return tempContext
        var tryCount = 0
        while (tempContext is ContextWrapper) {
            if (tempContext is Activity) return tempContext
            if (tryCount > 20) {
                break
            }
            tempContext = tempContext.baseContext
            tryCount++
        }
        return if (returnTopIfNull) StackKCb.instance.getStackTopActivity() else null
    }

    //根据View获取Activity
    @JvmStatic
    @OApiUse_BaseApplication
    fun get(view: View): Activity? =
        get(view.context)

    //寻找Activity从Obj
    @JvmStatic
    @OApiUse_BaseApplication
    fun get(obj: Any): Activity? =
        get(obj, false)

    //寻找Activity从Obj
    @JvmStatic
    @OApiUse_BaseApplication
    @OptIn(OApiInit_InApplication::class)
    fun get(obj: Any, returnTopIfNull: Boolean): Activity? {
        var activity: Activity? = null
        when (obj) {
            is Context -> activity = get(obj, true)
            is Fragment -> activity = obj.activity
            is Dialog -> activity = get(obj.context, true)
        }
        if (activity == null && returnTopIfNull) {
            activity = StackKCb.instance.getStackTopActivity()
        }
        return activity
    }



    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun <A : Annotation> getAnnotation(activity: Activity, annotationClazz: Class<A>): A? =
        UtilKClazz.getAnnotation(activity.javaClass, annotationClazz)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFinishingOrDestroyed(activity: Activity): Boolean =
        (UtilKActivity.isFinishing(activity) || UtilKActivity.isDestroyed(activity)).also { UtilKLogWrapper.dt(UtilKActivity.TAG, "isFinishingOrDestroyed: activity $activity $it") }

    //判断Activity是否被销毁
    @JvmStatic
    @OApiUse_BaseApplication
    fun isFinishingOrDestroyed(context: Context): Boolean {
        val activity: Activity? = get(context)
        return if (activity != null) isFinishingOrDestroyed(activity) else true
    }
}