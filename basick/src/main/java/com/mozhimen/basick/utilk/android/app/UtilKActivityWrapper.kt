package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.android.app.cons.CActivity
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.view.cons.CWindow
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.android.view.UtilKContentView
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKWindowManagerWrapper
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKAlertDialog
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKClazz

/**
 * @ClassName UtilKActivityWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 17:27
 * @Version 1.0
 */
fun <V : View> Activity.getContentView(): V =
    UtilKActivityWrapper.getContentView(this)

fun <V : View> Activity.getDecorView(): V =
    UtilKActivityWrapper.getDecorView(this)

fun <A : Annotation> Activity.getAnnotation(annotationClazz: Class<A>): A? =
    UtilKActivityWrapper.getAnnotation(this, annotationClazz)

fun Activity.isFinishingOrDestroyed(): Boolean =
    UtilKActivityWrapper.isFinishingOrDestroyed(this)

/////////////////////////////////////////////////////////////////////////

fun Activity.applyResult_ofCANCELED(data: Intent? = null, isFinish: Boolean = true) {
    UtilKActivityWrapper.applyResult_ofCANCELED(this, data, isFinish)
}

fun Activity.applyResult_ofOK(data: Intent? = null, isFinish: Boolean = true) {
    UtilKActivityWrapper.applyResult_ofOK(this, data, isFinish)
}

/////////////////////////////////////////////////////////////////////////

fun Activity.showAlertDialog(intResStrMsg: Int, intResStrLabel: Int, block: I_Listener) {
    UtilKActivityWrapper.showAlertDialog(this, intResStrMsg, intResStrLabel, block)
}

fun Activity.showAlertDialog(strMsg: String, strLabel: String, block: I_Listener) {
    UtilKActivityWrapper.showAlertDialog(this, strMsg, strLabel, block)
}

/////////////////////////////////////////////////////////////////////////

object UtilKActivityWrapper : IUtilK {
    @JvmStatic
    @OApiUse_BaseApplication
    fun get_ofContext(context: Context): Activity? =
        get_ofContext(context, true).also { UtilKLogWrapper.d(TAG, "get_ofContext: $it") }

    //判断context是否是Activity 这里注意一定要再Application中加入StackK并初始化
    @JvmStatic
    @OApiUse_BaseApplication
    @OptIn(OApiInit_InApplication::class)
    fun get_ofContext(context: Context, returnTopIfNull: Boolean): Activity? {
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
    fun get_ofView(view: View): Activity? =
        get_ofContext(view.context)

    //寻找Activity从Obj
    @JvmStatic
    @OApiUse_BaseApplication
    fun get_ofObj(obj: Any): Activity? =
        get_ofObj(obj, false)

    //寻找Activity从Obj
    @JvmStatic
    @OApiUse_BaseApplication
    @OptIn(OApiInit_InApplication::class)
    fun get_ofObj(obj: Any, returnTopIfNull: Boolean): Activity? {
        var activity: Activity? = null
        when (obj) {
            is Context -> activity = get_ofContext(obj, true)
            is Fragment -> activity = obj.activity
            is Dialog -> activity = get_ofContext(obj.context, true)
        }
        if (activity == null && returnTopIfNull) {
            activity = StackKCb.instance.getStackTopActivity()
        }
        return activity
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun <V : View> getContentView(activity: Activity): V =
        UtilKContentView.get_ofPac(activity)

    @JvmStatic
    fun <V : View> getDecorView(activity: Activity): V =
        UtilKDecorView.getAs(activity)

    @JvmStatic
    fun <A : Annotation> getAnnotation(activity: Activity, annotationClazz: Class<A>): A? =
        UtilKClazz.getAnnotation(activity.javaClass, annotationClazz)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasFloatWindow_ofToken(activity: Activity): Boolean {
        val targetDecorView = activity.window.decorView// 获取目标 Activity 的 decorView
        val targetSubToken = targetDecorView.windowToken// 获取目标 Activity 的 子窗口的 token
        val mView = UtilKWindowManagerWrapper.getViews().map { it }.toList()//  拿到 mView 集合，找到目标 Activity 所在的 index 位置
        val targetIndex = mView.indexOfFirst { it == targetDecorView }
        val mParams = UtilKWindowManagerWrapper.getParams()// 获取 mParams 集合
        val targetToken = mParams[targetIndex].token// 根据目标 index 从 mParams 集合中找到目标 token
        return mParams
            .map { it.token }
            .filter { it == targetSubToken || it == null || it == targetToken }
            .size > 1// 遍历判断时，目标 Activity 自己不能包括,所以 size 需要大于 1
    }

    @JvmStatic
    fun isFinishingOrDestroyed(activity: Activity): Boolean =
        (UtilKActivity.isFinishing(activity) || UtilKActivity.isDestroyed(activity)).also { UtilKLogWrapper.d(UtilKActivity.TAG, "isFinishingOrDestroyed: activity $activity $it") }

    //判断Activity是否被销毁
    @JvmStatic
    @OApiUse_BaseApplication
    fun isFinishingOrDestroyed(context: Context): Boolean {
        val activity: Activity? = get_ofContext(context)
        return (if (activity != null) isFinishingOrDestroyed(activity) else true).also { UtilKLogWrapper.d(TAG, "isFinishingOrDestroyed: $it") }
    }
    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyResult_ofCANCELED(activity: Activity, data: Intent? = null, isFinish: Boolean = true) {
        UtilKActivity.applyResult(activity, CActivity.RESULT_CANCELED, data)
        if (isFinish)
            activity.finish()
    }

    @JvmStatic
    fun applyResult_ofOK(activity: Activity, data: Intent? = null, isFinish: Boolean = true) {
        UtilKActivity.applyResult(activity, CActivity.RESULT_OK, data)
        if (isFinish)
            activity.finish()
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showAlertDialog(activity: Activity, intResStrMsg: Int, intResStrLabel: Int, block: I_Listener) {
        UtilKAlertDialog.show(activity, intResStrMsg, intResStrLabel, block)
    }

    @JvmStatic
    fun showAlertDialog(activity: Activity, strMsg: String, strLabel: String, block: I_Listener) {
        UtilKAlertDialog.show(activity, strMsg, strLabel, block)
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun requestWindowFeature_ofCONTENT_TRANSITIONS(activity: Activity) {
        UtilKActivity.requestWindowFeature(activity, CWindow.FEATURE_CONTENT_TRANSITIONS)
    }
}