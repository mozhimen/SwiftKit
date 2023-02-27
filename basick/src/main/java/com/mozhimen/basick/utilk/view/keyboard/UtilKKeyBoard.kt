package com.mozhimen.basick.utilk.view.keyboard

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.*
import android.util.Log
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.java.UtilKReflect
import com.mozhimen.basick.utilk.view.UtilKView
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import java.lang.reflect.Field
import kotlin.math.abs

typealias IUtilKKeyBoardChangeListener = (height: Int) -> Unit

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
    private const val TAG_ON_GLOBAL_LAYOUT_LISTENER = -8

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
     */
    @JvmStatic
    fun show() {
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 显示软键盘
     * @param activity Activity
     */
    @JvmStatic
    fun show(activity: Activity) {
        if (!isKeyBoardVisible(activity)) {
            toggle()
        }
    }

    /**
     * Show the soft input.
     * @param view The view.
     */
    @JvmStatic
    fun show(view: View) {
        show(view, 0)
    }

    /**
     * Show the soft input.
     * @param view The view.
     * @param flags Provides additional operating flags.  Currently may be
     * 0 or have the [InputMethodManager.SHOW_IMPLICIT] bit set.
     */
    @JvmStatic
    fun show(view: View, flags: Int) {
        val inputMethodManager = _context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        inputMethodManager.showSoftInput(view, flags, object : ResultReceiver(Handler()) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggle()
                }
            }
        })
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * Hide the soft input.
     * @param activity The activity.
     */
    fun hide(activity: Activity) {
        hide(activity.window)
    }

    /**
     * Hide the soft input.
     * @param window The window.
     */
    fun hide(window: Window) {
        var view = window.currentFocus
        if (view == null) {
            val decorView = window.decorView
            val focusView = decorView.findViewWithTag<View>("keyboardTagView")
            if (focusView == null) {
                view = EditText(window.context).apply { tag = "keyboardTagView" }
                (decorView as ViewGroup).addView(view, 0, 0)
            } else {
                view = focusView
            }
            view.requestFocus()
        }
        hide(view)
    }

    /**
     * Hide the soft input.
     * @param view The view.
     */
    fun hide(view: View) {
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private var _millis: Long = 0

    /**
     * Hide the soft input.
     * @param activity The activity.
     */
    fun hideSoftInputByToggle(activity: Activity) {
        val nowMillis = SystemClock.elapsedRealtime()
        val delta = nowMillis - _millis
        if (abs(delta) > 500 && isKeyBoardVisible(activity)) {
            toggle()
        }
        _millis = nowMillis
    }

    /**
     * Toggle the soft input display or not.
     */
    fun toggle() {
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, 0)
    }

    /**
     * Return whether soft input is visible.
     * @param activity The activity.
     * @return `true`: yes<br></br>`false`: no
     */
    fun isKeyBoardVisible(activity: Activity): Boolean {
        return getDecorViewInvisibleHeight(activity.window) > 0
    }

    private var _decorViewDelta = 0
    private fun getDecorViewInvisibleHeight(window: Window): Int {
        val decorView = window.decorView
        val outRect = Rect()
        decorView.getWindowVisibleDisplayFrame(outRect)
        Log.d(TAG, "getDecorViewInvisibleHeight: " + (decorView.bottom - outRect.bottom))
        val delta = abs(decorView.bottom - outRect.bottom)
        if (delta <= UtilKNavigationBar.getNavigationBarHeight() + UtilKStatusBar.getStatusBarHeight()) {
            _decorViewDelta = delta
            return 0
        }
        return delta - _decorViewDelta
    }


    /**
     * Register soft input changed listener.
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    fun registerKeyBoardChangeListener(
        activity: Activity,
        listener: IUtilKKeyBoardChangeListener
    ) {
        registerKeyBoardChangeListener(activity.window, listener)
    }

    /**
     * Register soft input changed listener.
     * @param window The window.
     * @param listener The soft input changed listener.
     */
    fun registerKeyBoardChangeListener(
        window: Window,
        listener: IUtilKKeyBoardChangeListener
    ) {
        val flags = window.attributes.flags
        if (flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        val decorViewInvisibleHeightPre = intArrayOf(getDecorViewInvisibleHeight(window))
        val onGlobalLayoutListener = OnGlobalLayoutListener {
            val height = getDecorViewInvisibleHeight(window)
            if (decorViewInvisibleHeightPre[0] != height) {
                listener.invoke(height)
                decorViewInvisibleHeightPre[0] = height
            }
        }
        contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        contentView.setTag(TAG_ON_GLOBAL_LAYOUT_LISTENER, onGlobalLayoutListener)
    }

    /**
     * Unregister soft input changed listener.
     *
     * @param window The window.
     */
    fun unregisterSoftInputChangedListener(window: Window) {
        val contentView = window.findViewById<View>(R.id.content) ?: return
        val tag = contentView.getTag(TAG_ON_GLOBAL_LAYOUT_LISTENER)
        if (tag is OnGlobalLayoutListener) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(tag)
                //这里会发生内存泄漏 如果不设置为null
                contentView.setTag(TAG_ON_GLOBAL_LAYOUT_LISTENER, null)
            }
        }
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * Don't set adjustResize
     *
     * @param activity The activity.
     */
    fun fixAndroidBug5497(activity: Activity) {
        fixAndroidBug5497(activity.window)
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * It will clean the adjustResize
     *
     * @param window The window.
     */
    fun fixAndroidBug5497(window: Window) {
        val softInputMode = window.attributes.softInputMode
        window.setSoftInputMode(
            softInputMode and WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE.inv()
        )
        val contentView = window.findViewById<FrameLayout>(R.id.content)
        val contentViewChild = contentView.getChildAt(0)
        val paddingBottom = contentViewChild.paddingBottom
        val contentViewInvisibleHeightPre5497 = intArrayOf(getContentViewInvisibleHeight(window))
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = getContentViewInvisibleHeight(window)
            if (contentViewInvisibleHeightPre5497[0] != height) {
                contentViewChild.setPadding(
                    contentViewChild.paddingLeft,
                    contentViewChild.paddingTop, contentViewChild.paddingRight,
                    paddingBottom + getDecorViewInvisibleHeight(window)
                )
                contentViewInvisibleHeightPre5497[0] = height
            }
        }
    }

    private fun getContentViewInvisibleHeight(window: Window): Int {
        val contentView = window.findViewById<View>(R.id.content) ?: return 0
        val outRect = Rect()
        contentView.getWindowVisibleDisplayFrame(outRect)
        Log.d(
            "KeyboardUtils",
            "getContentViewInvisibleHeight: " + (contentView.bottom - outRect.bottom)
        )
        val delta = Math.abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getStatusBarHeight() + UtilKNavigationBar.getNavigationBarHeight()) {
            0
        } else delta
    }

    /**
     * Fix the leaks of soft input.
     *
     * @param activity The activity.
     */
    fun fixSoftInputLeaks(activity: Activity) {
        fixSoftInputLeaks(activity.window)
    }

    /**
     * Fix the leaks of soft input.
     *
     * @param window The window.
     */
    fun fixSoftInputLeaks(window: Window) {
        val inputMethodManager = Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
        val leakViews = arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
        for (leakView in leakViews) {
            try {
                val leakViewField = InputMethodManager::class.java.getDeclaredField(leakView)
                if (!leakViewField.isAccessible) {
                    leakViewField.isAccessible = true
                }
                val obj = leakViewField[inputMethodManager] as? View ?: continue
                if (obj.rootView === window.decorView.rootView) {
                    leakViewField[inputMethodManager] = null
                }
            } catch (ignore: Throwable) { /**/
            }
        }
    }

    /**
     * Click blank area to hide soft input.
     *
     * Copy the following code in ur activity.
     */
    fun clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.")
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    KeyboardUtils.hideSoftInput(this);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // Return whether touch the view.
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if ((v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationOnScreen(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getRawX() > left && event.getRawX() < right
                        && event.getRawY() > top && event.getRawY() < bottom);
            }
            return false;
        }
        */
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 显示软键盘
     * @param view View
     */
    @JvmStatic
    fun show(view: View) {
        if (!isActive(view)) {
            var focusView = view
            if (!view.hasFocus()) {
                UtilKView.requestFocus(view)
                view.findFocus()?.let { focusView = it }
            }
            (focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(focusView, 0)
        } else {
            Log.d(TAG, "show: isActive")
        }
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
        if (activity.window.peekDecorView() != null && isActive(activity)) {
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    /**
     * 隐藏软键盘
     * @param view View
     */
    @JvmStatic
    fun hide(view: View) {
        if (isActive(view)) {
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 是否打开
     * @return Boolean
     */
    @JvmStatic
    fun isActive(activity: Activity): Boolean =
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive

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