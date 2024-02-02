package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.view.cons.CWindow
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.lintk.optin.OptInApiUse_BaseApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.android.content.UtilKIntentWrapper
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.view.UtilKContentView
import com.mozhimen.basick.utilk.android.view.UtilKWindow
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKClazz
import com.mozhimen.basick.utilk.kotlin.UtilKString

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 13:44
 * @Version 1.0
 */
fun <A : Annotation> Activity.getAnnotation(annotationClazz: Class<A>): A? =
    UtilKActivity.getAnnotation(this, annotationClazz)

fun <V : View> Activity.getContentView(): V =
    UtilKActivity.getContentView(this)

//////////////////////////////////////////////////////////////////////////////////////////

fun Activity.isFinishingOrDestroyed(): Boolean =
    UtilKActivity.isFinishingOrDestroyed(this)

//////////////////////////////////////////////////////////////////////////////////////////

object UtilKActivity : IUtilK {
    /**
     * 判断context是否是Activity 这里注意一定要再Application中加入StackK并初始化
     * @param context Context
     * @param returnTopIfNull Boolean
     * @return Activity?
     */
    @OptIn(OptInApiInit_InApplication::class)
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun getByContext(context: Context, returnTopIfNull: Boolean = false): Activity? {
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

    /**
     * 根据View获取Activity
     * @param view View
     * @return Activity?
     */
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun getByView(view: View): Activity? =
        getByContext(view.context)

    /**
     * 寻找Activity从Obj
     * @param obj Any
     * @param returnTopIfNull Boolean
     * @return Activity?
     */
    @OptInApiUse_BaseApplication
    @OptIn(OptInApiInit_InApplication::class)
    @JvmStatic
    fun getByObj(obj: Any, returnTopIfNull: Boolean = false): Activity? {
        var activity: Activity? = null
        when (obj) {
            is Context -> activity = getByContext(obj, true)
            is Fragment -> activity = obj.activity
            is Dialog -> activity = getByContext(obj.context, true)
        }
        if (activity == null && returnTopIfNull) {
            activity = StackKCb.instance.getStackTopActivity()
        }
        return activity
    }

    /**
     * 获取启动Activity
     * @param strPackageName String
     * @return String?
     */
    @JvmStatic
    @AManifestKRequire(CPermission.QUERY_ALL_PACKAGES)
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getLauncherActivityName(context: Context, strPackageName: String): String {
        if (UtilKString.hasSpace(strPackageName) || strPackageName.isEmpty()) return ""
        val resolveInfos = UtilKPackageManager.queryIntentActivities(context, UtilKIntentWrapper.getMainLauncher(strPackageName, null), 0)
        return if (resolveInfos.isEmpty()) "" else resolveInfos[0].activityInfo.name
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun <A : Annotation> getAnnotation(activity: Activity, annotationClazz: Class<A>): A? =
        UtilKClazz.getAnnotation(activity.javaClass, annotationClazz)

    @JvmStatic
    fun getCurrentFocus(activity: Activity): View? =
        activity.currentFocus

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getDisplay(activity: Activity): Display =
        activity.display!!

    @JvmStatic
    fun getWindowManager(activity: Activity): WindowManager =
        activity.windowManager

    @JvmStatic
    fun <V : View> getContentView(activity: Activity): V =
        UtilKContentView.get(activity)

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getViewDrawHeight(activity: Activity): Int =
        UtilKWindow.getContentViewOfWindow<View>(activity).top

    //////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isFinishingOrDestroyed(activity: Activity): Boolean =
        (isFinishing(activity) || isDestroyed(activity)).also { Log.d(TAG, "isFinishingOrDestroyed: activity $activity $it") }

    /**
     * 判断Activity是否被销毁
     * @param context Context
     * @return Boolean
     */
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun isDestroyed(context: Context): Boolean {
        val activity: Activity? = getByContext(context)
        return if (activity != null) isDestroyed(activity) else true
    }

    @JvmStatic
    fun isFinishing(activity: Activity): Boolean =
        activity.isFinishing.also { Log.d(TAG, "isFinishing: activity $activity $it") }

    @JvmStatic
    fun isDestroyed(activity: Activity): Boolean =
        (if (UtilKBuildVersion.isAfterV_17_42_J1())
            activity.isDestroyed || isFinishing(activity)
        else isFinishing(activity)).also { Log.d(TAG, "isDestroyed: activity $activity $it") }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * android.util.AndroidRuntimeException: requestFeature() must be called before adding content
     * setContentView顺序正常，并且也可以在继承AppCompatActivity情况下也能正常运行，
     * 导致我不正常的是Title隐藏选项：getSupportActionBar().hide();
     * //注意顺序，一定要在requestWindowFeature下面，否则报错
     */
    @JvmStatic
    fun requestWindowFeature(activity: Activity, featureId: Int) {
        activity.requestWindowFeature(featureId)
    }
}