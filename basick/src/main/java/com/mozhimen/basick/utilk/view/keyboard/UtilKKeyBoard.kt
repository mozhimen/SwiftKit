package com.mozhimen.basick.utilk.view.keyboard

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewParent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.java.UtilKReflect
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.view.UtilKView
import java.lang.reflect.Field

/**
 * @ClassName UtilKKeyBoard
 * @Description 如果你希望他在7.0生效,你还需设置manifest->android:windowSoftInputMode="stateAlwaysHidden"
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:01
 * @Version 1.0
 */
object UtilKKeyBoard {
    private const val TAG = "UtilKKeyBoard>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * 显示软键盘
     * @param context Context
     */
    @JvmStatic
    fun toggle(context: Context) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, 0)
    }

    /**
     * 显示软键盘
     * @param view View
     */
    @JvmStatic
    fun show(view: View) {
        var focusView = view
        if (!view.hasFocus()) {
            UtilKView.requestFocus(view)
            view.findFocus()?.let { focusView = it }
        }
        (focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(focusView, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 延迟显示软键盘
     * @param view View
     * @param delayMillis Long
     */
    @JvmStatic
    fun showPostDelay(view: View, delayMillis: Long) {
        view.postDelayed({ show(view) }, delayMillis)
    }

    /**
     * 关闭软键盘
     * @param activity Activity
     */
    @JvmStatic
    fun hide(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window.peekDecorView() != null && inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    /**
     * 隐藏软键盘
     * @param view View
     */
    @JvmStatic
    fun hide(view: View) {
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 是否打开
     * @return Boolean
     */
    @JvmStatic
    fun isActive(): Boolean =
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive

    /**
     * 是否打开
     * @param view View
     * @return Boolean
     */
    @JvmStatic
    fun isActive(view: View): Boolean =
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive(view)


    /**
     * 是否需要隐藏软键盘
     * @param view View 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
     * @param event MotionEvent
     * @return Boolean
     */
    @JvmStatic
    fun isNeedHide(view: View, event: MotionEvent): Boolean {
        if (view is EditText) {
            val ints = intArrayOf(0, 0)//left,top
            view.getLocationInWindow(ints)
            val right = ints[0] + view.getWidth()
            val bottom = ints[1] + view.getHeight()
            return !(event.x > ints[0] && event.x < right && event.y > ints[1] && event.y < bottom)
        }
        return false
    }

    /**
     * 修复在RecyclerView中持有内存泄漏的问题
     * @param context Context
     */
    @JvmStatic
    fun fixInputMethodLeak(context: Context, tag: String = TAG) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
            fixInputMethodLeakAfterQ(context, tag)
        } else {
            fixInputMethodLeakBeforeQ(context, tag)
        }
    }

    @JvmStatic
    fun fixInputMethodLeakAfterQ(context: Context, tag: String) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val field: Field
        val fieldObj: Any?
        try {
            field = UtilKReflect.getField(inputMethodManager, "mCurRootView")
            if (!field.isAccessible) field.isAccessible = true
            fieldObj = field.get(inputMethodManager)
            if (fieldObj != null && fieldObj is ViewParent) {
                field.set(inputMethodManager, null)
                Log.d(TAG, "fixInputMethodLeak: $tag set view mCurRootView null in inputMethodManager")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    @JvmStatic
    fun fixInputMethodLeakBeforeQ(context: Context, tag: String) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val viewNames = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var field: Field
        var fieldObj: Any?
        for (viewName in viewNames) {
            try {
                field = UtilKReflect.getField(inputMethodManager, viewName)
                if (!field.isAccessible) field.isAccessible = true
                fieldObj = field.get(inputMethodManager)
                if (fieldObj != null && fieldObj is View) {
                    Log.d(TAG, "fixInputMethodLeak: fieldObj.context ${fieldObj.context} context $context")
                    if (fieldObj.context == context) {                        //注意需要判断View关联的Context是不是当前Activity，否则有可能造成正常的输入框输入失效
                        field.set(inputMethodManager, null)
                        Log.d(TAG, "fixInputMethodLeak: $tag set view $viewName null in inputMethodManager")
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
    }
}