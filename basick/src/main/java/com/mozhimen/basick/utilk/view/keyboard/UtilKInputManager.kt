package com.mozhimen.basick.utilk.view.keyboard

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.java.UtilKReflect
import com.mozhimen.basick.utilk.view.UtilKView
import com.mozhimen.basick.utilk.view.window.UtilKWindow
import java.lang.reflect.Field

/**
 * @ClassName UtilKKeyBoard
 * @Description 如果你希望他在7.0生效,你还需设置manifest->android:windowSoftInputMode="stateAlwaysHidden"
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:01
 * @Version 1.0
 */
object UtilKInputManager {
    private const val TAG = "UtilKKeyBoard>>>>>"

    /**
     * 获取InputManager
     * @param context Context
     * @return InputMethodManager
     */
    @JvmStatic
    fun get(context: Context): InputMethodManager =
        UtilKContext.getInputMethodManager(context)

    /**
     * 显示软键盘
     * @param context Context
     */
    @Deprecated(
        "google Deprecated", ReplaceWith(
            "(context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, 0)",
            "android.content.Context", "android.view.inputmethod.InputMethodManager"
        )
    )
    @JvmStatic
    fun toggle(context: Context) {
        get(context).toggleSoftInput(0, 0)
    }

    /**
     * 显示软键盘
     * @param activity Activity
     */
    @JvmStatic
    fun show(activity: Activity) {
        if (get(activity).isActive && UtilKActivity.getCurrentFocus(activity) != null) {
            show(UtilKActivity.getCurrentFocus(activity)!!)
        }
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
        get(focusView.context).showSoftInput(focusView, 0)
    }

    /**
     * 延迟显示软键盘
     * @param view View
     * @param delayMillis Long
     */
    @JvmStatic
    fun showByDelay(view: View, delayMillis: Long) {
        view.postDelayed({ show(view) }, delayMillis)
    }

    /**
     * 关闭软键盘
     * @param activity Activity
     */
    @JvmStatic
    fun hide(activity: Activity) {
        if (((UtilKWindow.getPeekDecorView(activity) != null || get(activity).isActive) && UtilKActivity.getCurrentFocus(activity) != null) && isShow(activity)) {
            hide(UtilKActivity.getCurrentFocus(activity)!!)
        }
    }

    /**
     * 隐藏软键盘
     * @param view View
     */
    @JvmStatic
    fun hide(view: View) {
        get(view.context).hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 点击空白地方隐藏软键盘
     * Click blank area to hide soft input.
     * Copy the following code in ur activity.
     */
    @JvmStatic
    fun hideByClickOther() {
        Log.d(TAG, "hideByClickOther: Please refer to the following code.")
        //kotlin
        /*override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                val focusView: View? = currentFocus
                if (focusView != null && UtilKKeyBoard.isShouldHide(focusView, event)) {
                    UtilKKeyBoard.hide(this)
                }
            }
            return super.dispatchTouchEvent(event)
        }*/
        //java
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View view = getCurrentFocus();
                if (UtilKKeyBoard.isShouldHideKeyboard(view, ev)) {
                    UtilKKeyBoard.hide(this);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        */
    }

    /**
     * Click blank area to hide soft input.
     * Copy the following code in ur activity.
     */
    @JvmStatic
    fun clickBlankAreaToHide() {

    }

    /**
     * 是否激活
     * @return Boolean
     */
    @JvmStatic
    fun isActive(context: Context): Boolean =
        get(context).isActive

    /**
     * 是否激活
     * @param view View
     * @return Boolean
     */
    @JvmStatic
    fun isActive(view: View): Boolean =
        get(view.context).isActive(view)

    /**
     * 是否显示
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isShow(activity: Activity): Boolean {
        return UtilKWindow.getDecorViewInvisibleHeight(UtilKWindow.get(activity)) > 0
    }

    /**
     * 是否需要隐藏软键盘
     * Return whether touch the view.
     * for Example:
     *
     * inActivity:
     *
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
    if (event?.action == MotionEvent.ACTION_DOWN) {
    val focusView: View? = currentFocus
    if (focusView != null && UtilKKeyBoard.isShouldHide(focusView, event)) {
    UtilKKeyBoard.hide(this)
    }
    }
    return super.dispatchTouchEvent(event)
    }
     *
     * inFragment:
     *
    @SuppressLint("ClickableViewAccessibility")
    override fun inflateView(viewGroup: ViewGroup?) {
    viewGroup?.setOnTouchListener { _, event ->
    if (event?.action == MotionEvent.ACTION_DOWN) {
    val focusView: View? = requireActivity().currentFocus
    if (focusView != null && UtilKKeyBoard.isShouldHide(focusView, event)) {
    UtilKKeyBoard.hide(requireActivity())
    }
    }
    false
    }
    super.inflateView(viewGroup)
    }
     * @param view View 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
     * @param event MotionEvent
     * @return Boolean
     */
    fun isShouldHide(view: View, event: MotionEvent): Boolean {
        if (view is EditText) {
            val ints = intArrayOf(0, 0)
            view.getLocationOnScreen(ints)
            val left = ints[0]
            val top = ints[1]
            val bottom = top + view.getHeight()
            val right = left + view.getWidth()
            return !(event.rawX > left && event.rawX < right && event.rawY > top && event.rawY < bottom)
        }
        return false
    }

    /**
     * 修复在RecyclerView中持有内存泄漏的问题
     * @param context Context
     */
    @JvmStatic
    fun fixInputMethodLeak(context: Context, tag: String) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
            fixInputMethodLeakAfterQ(context, tag)
        } else {
            fixInputMethodLeakBeforeQ(context, tag)
        }
    }

    /**
     * 修复在RecyclerView中持有内存泄漏的问题
     * @param context Context
     * @param tag String
     */
    @JvmStatic
    fun fixInputMethodLeakAfterQ(context: Context, tag: String) {
        if (Build.VERSION.SDK_INT < CVersionCode.V_29_10_Q) return
        val inputMethodManager = get(context)
        try {
            val mCurRootViewField = UtilKReflect.getField(inputMethodManager, "mCurRootView")
            if (!mCurRootViewField.isAccessible) mCurRootViewField.isAccessible = true
            val mCurRootViewObj = mCurRootViewField.get(inputMethodManager)
            if (mCurRootViewObj != null) {
                val mImeFocusControllerField = UtilKReflect.getField(mCurRootViewObj, "mImeFocusController")
                if (!mImeFocusControllerField.isAccessible) mImeFocusControllerField.isAccessible = true
                val mImeFocusControllerObj = mImeFocusControllerField.get(mCurRootViewObj)
                if (mImeFocusControllerObj != null) {
                    val mNextServedViewField = UtilKReflect.getField(mImeFocusControllerObj, "mNextServedView")
                    if (!mNextServedViewField.isAccessible) mNextServedViewField.isAccessible = true
                    val mNextServedViewObj = mNextServedViewField.get(mImeFocusControllerObj)
                    if (mNextServedViewObj != null) {
                        val mParentField = UtilKReflect.getField(mNextServedViewObj, "mParent")
                        if (!mParentField.isAccessible) mParentField.isAccessible = true
                        val mParentObj = mParentField.get(mNextServedViewObj)
                        if (mParentObj != null) {
                            mParentField.set(mParentObj, null)
                            Log.d(TAG, "fixInputMethodLeak: $tag set view mNextServedView: mParent null in inputMethodManager")
                        }
//                            val mParent1Field = UtilKReflect.getField(mParentObj, "mParent")
//                            if (!mParent1Field.isAccessible) mParent1Field.isAccessible = true
//                            val mParent1Obj = mParent1Field.get(mParentObj)
//                            if (mParent1Obj != null) {
//                                mParent1Field.set(mParent1Obj, null)
//                                Log.d(TAG, "fixInputMethodLeak: $tag set view mNextServedView: mParent null in inputMethodManager")
//                            }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    /**
     * 修复在RecyclerView中持有内存泄漏的问题
     * @param context Context
     * @param tag String
     */
    @JvmStatic
    fun fixInputMethodLeakBeforeQ(context: Context, tag: String) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) return
        val inputMethodManager = UtilKContext.getInputMethodManager(context)
        val leakViews = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var leakViewField: Field
        var fieldObj: Any?
        for (leakView in leakViews) {
            try {
                leakViewField = UtilKReflect.getField(inputMethodManager, leakView)
                if (!leakViewField.isAccessible) leakViewField.isAccessible = true
                fieldObj = leakViewField.get(inputMethodManager)
                if (fieldObj != null && fieldObj is View) {
                    if (fieldObj.context == context) {                        //注意需要判断View关联的Context是不是当前Activity，否则有可能造成正常的输入框输入失效
                        leakViewField.set(inputMethodManager, null)
                        Log.d(TAG, "fixInputMethodLeak: $tag set view $leakView null in inputMethodManager")
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

//object UtilKKeyBoard {
//    private const val TAG = "UtilKKeyBoard>>>>>"
//    private val _context = UtilKApplication.instance.get()
//
//    /**
//     * 显示软键盘
//     * @param context Context
//     */
//    @JvmStatic
//    fun toggle(context: Context) {
//        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, 0)
//    }
//
//    /**
//     * Toggle the soft input display or not.
//     */
//    fun toggle() {
//        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, 0)
//    }
//
//    /**
//     * 显示软键盘
//     */
//    @JvmStatic
//    fun show() {
//        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
//    }
//
//    /**
//     * 显示软键盘
//     * @param activity Activity
//     */
//    @JvmStatic
//    fun show(activity: Activity) {
//        if (!isKeyBoardVisible(activity)) toggle()
//    }
//
//    /**
//     * Show the soft input.
//     * @param view The view.
//     */
//    @JvmStatic
//    fun show(view: View) {
//        show(view, 0)
//    }
//
//    /**
//     * Show the soft input.
//     * @param view The view.
//     * @param flags Provides additional operating flags.  Currently may be
//     * 0 or have the [InputMethodManager.SHOW_IMPLICIT] bit set.
//     */
//    @JvmStatic
//    fun show(view: View, flags: Int) {
//        val inputMethodManager = _context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        view.isFocusable = true
//        view.isFocusableInTouchMode = true
//        view.requestFocus()
//        inputMethodManager.showSoftInput(view, flags, object : ResultReceiver(Handler()) {
//            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
//                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN || resultCode == InputMethodManager.RESULT_HIDDEN) toggle()
//            }
//        })
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
//    }
//
//    /**
//     * 延迟显示软键盘
//     * @param view View
//     * @param delayMillis Long
//     */
//    @JvmStatic
//    fun showPostDelay(view: View, delayMillis: Long) {
//        view.postDelayed({ show(view) }, delayMillis)
//    }
//
//    /**
//     * 显示软键盘
//     * @param view View
//     */
//    /*@JvmStatic
//    fun show(view: View) {
//        var focusView = view
//        if (!view.hasFocus()) {
//            UtilKView.requestFocus(view)
//            view.findFocus()?.let { focusView = it }
//        }
//        (focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(focusView, 0)
//    }*/
//
//    /**
//     * Hide the soft input.
//     * @param activity The activity.
//     */
//    @JvmStatic
//    fun hide(activity: Activity) {
//        hide(activity.window)
//    }
//
//    /**
//     * 关闭软键盘
//     * @param activity Activity
//     */
//    /*@JvmStatic
//    fun hide(activity: Activity) {
//        if (activity.window.peekDecorView() != null && isActive(activity)) {
//            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
//        }
//    }*/
//
//    /**
//     * Hide the soft input.
//     * @param window The window.
//     */
//    @JvmStatic
//    fun hide(window: Window) {
//        var view = window.currentFocus
//        if (view == null) {
//            val decorView = window.decorView
//            val focusView = decorView.findViewWithTag<View>("keyboardTagView")
//            if (focusView == null) {
//                view = EditText(window.context).apply { tag = "keyboardTagView" }
//                (decorView as ViewGroup).addView(view, 0, 0)
//            } else {
//                view = focusView
//            }
//            view.requestFocus()
//        }
//        hide(view)
//    }
//
//    /**
//     * Hide the soft input.
//     * @param view The view.
//     */
//    /*@JvmStatic
//    fun hide(view: View) {
//        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
//    }*/
//
//    /**
//     * 隐藏软键盘
//     * @param view View
//     */
//    @JvmStatic
//    fun hide(view: View) {
//        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    private var _millis: Long = 0
//
//    /**
//     * Hide the soft input.
//     * @param activity The activity.
//     */
//    @JvmStatic
//    fun hideByToggle(activity: Activity) {
//        val nowMillis = SystemClock.elapsedRealtime()
//        val delta = nowMillis - _millis
//        if (abs(delta) > 500 && isKeyBoardVisible(activity)) {
//            toggle()
//        }
//        _millis = nowMillis
//    }
//
//    /**
//     * Return whether soft input is visible.
//     * @param activity The activity.
//     * @return `true`: yes<br></br>`false`: no
//     */
//    @JvmStatic
//    fun isKeyBoardVisible(activity: Activity): Boolean {
//        return UtilKWindow.getDecorViewInvisibleHeight(activity.window) > 0
//    }
//
//
//    /**
//     * 是否需要隐藏软键盘
//     * @param view View 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
//     * @param event MotionEvent
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isNeedHide(view: View, event: MotionEvent): Boolean {
//        if (view is EditText) {
//            val ints = intArrayOf(0, 0)//left,top
//            view.getLocationInWindow(ints)
//            val right = ints[0] + view.getWidth()
//            val bottom = ints[1] + view.getHeight()
//            return !(event.x > ints[0] && event.x < right && event.y > ints[1] && event.y < bottom)
//        }
//        return false
//    }
//
//    /**
//     * 是否打开
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isActive(activity: Activity): Boolean =
//        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive
//
//    /**
//     * 是否打开
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isActive(): Boolean =
//        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive
//
//    /**
//     * 是否打开
//     * @param view View
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isActive(view: View): Boolean =
//        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive(view)
//
//    /**
//     * 修复在RecyclerView中持有内存泄漏的问题
//     * @param context Context
//     */
//    @JvmStatic
//    fun fixInputMethodLeak(context: Context, tag: String = TAG) {
//        if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
//            fixInputMethodLeakAfterQ(context, tag)
//        } else {
//            fixInputMethodLeakBeforeQ(context, tag)
//        }
//    }
//
//    /**
//     * 修复在RecyclerView中持有内存泄漏的问题
//     * @param context Context
//     * @param tag String
//     */
//    @JvmStatic
//    fun fixInputMethodLeakAfterQ(context: Context, tag: String) {
//        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        val field: Field
//        val fieldObj: Any?
//        try {
//            field = UtilKReflect.getField(inputMethodManager, "mCurRootView")
//            if (!field.isAccessible) field.isAccessible = true
//            fieldObj = field.get(inputMethodManager)
//            if (fieldObj != null && fieldObj is ViewParent) {
//                field.set(inputMethodManager, null)
//                Log.d(TAG, "fixInputMethodLeak: $tag set view mCurRootView null in inputMethodManager")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            e.message?.et(TAG)
//        }
//    }
//
//    /**
//     * Fix the leaks of soft input.
//     * @param activity The activity.
//     */
//    @JvmStatic
//    fun fixInputMethodLeakBeforeQ(activity: Activity) {
//        fixInputMethodLeakBeforeQ(activity.window)
//    }
//
//    /**
//     * Fix the leaks of soft input.
//     * @param window The window.
//     */
//    @JvmStatic
//    fun fixInputMethodLeakBeforeQ(window: Window) {
//        val inputMethodManager = _context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        val leakViews = arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
//        for (leakView in leakViews) {
//            try {
//                val leakField = InputMethodManager::class.java.getDeclaredField(leakView)
//                if (!leakField.isAccessible) leakField.isAccessible = true
//                val fieldObj = leakField.get(inputMethodManager) as? View ?: continue
//                if (fieldObj.rootView == window.decorView.rootView) {
//                    leakField.set(inputMethodManager, null)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                e.message?.et(TAG)
//            }
//        }
//    }
//
//    /**
//     * 修复在RecyclerView中持有内存泄漏的问题
//     * @param context Context
//     * @param tag String
//     */
//    @JvmStatic
//    fun fixInputMethodLeakBeforeQ(context: Context, tag: String) {
//        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        val leakViews = arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
//        var leakField: Field
//        var fieldObj: Any?
//        for (leakView in leakViews) {
//            try {
//                leakField = UtilKReflect.getField(inputMethodManager, leakView)
//                if (!leakField.isAccessible) leakField.isAccessible = true
//                fieldObj = leakField.get(inputMethodManager) as? View ?: continue
//                if (fieldObj is View) {
//                    if (fieldObj.context == context) {                        //注意需要判断View关联的Context是不是当前Activity，否则有可能造成正常的输入框输入失效
//                        leakField.set(inputMethodManager, null)
//                        Log.d(TAG, "fixInputMethodLeak: $tag set view $leakView null in inputMethodManager")
//                    } else {
//                        break
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                e.message?.et(TAG)
//            }
//        }
//    }
//
//    /**
//     * Fix the bug of 5497 in Android.
//     * Don't set adjustResize
//     * @param activity The activity.
//     */
//    @JvmStatic
//    fun fixAndroidBug5497(activity: Activity) {
//        fixAndroidBug5497(activity.window)
//    }
//
//    /**
//     * Fix the bug of 5497 in Android.
//     * It will clean the adjustResize
//     * @param window The window.
//     */
//    @JvmStatic
//    fun fixAndroidBug5497(window: Window) {
//        val softInputMode = window.attributes.softInputMode
//        window.setSoftInputMode(softInputMode and WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE.inv())
//        val contentView = window.findViewById<FrameLayout>(R.id.content)
//        val contentViewChild = contentView.getChildAt(0)
//        val paddingBottom = contentViewChild.paddingBottom
//        val contentViewInvisibleHeightPre5497 = intArrayOf(UtilKWindow.getContentViewInvisibleHeight(window))
//        contentView.viewTreeObserver.addOnGlobalLayoutListener {
//            val height = UtilKWindow.getContentViewInvisibleHeight(window)
//            if (contentViewInvisibleHeightPre5497[0] != height) {
//                contentViewChild.setPadding(
//                    contentViewChild.paddingLeft,
//                    contentViewChild.paddingTop,
//                    contentViewChild.paddingRight,
//                    paddingBottom + UtilKWindow.getDecorViewInvisibleHeight(window)
//                )
//                contentViewInvisibleHeightPre5497[0] = height
//            }
//        }
//    }
//}