package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.view.UtilKView

/**
 * @ClassName UtilKKeyBoard
 * @Description 如果你希望他在7.0生效,你还需设置manifest->android:windowSoftInputMode="stateAlwaysHidden"
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:01
 * @Version 1.0
 */
object UtilKKeyBoard {
    private val _context = UtilKApplication.instance.get()

    /**
     * 显示软键盘
     * @param view View
     */
    @JvmStatic
    fun open(view: View) {
        var focusView = view
        if (!view.hasFocus()) {
            UtilKView.requestFocus(view)
            view.findFocus()?.let { focusView = it }
        }
        (focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(focusView, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 显示软键盘
     * @param context Context
     */
    @JvmStatic
    fun open(context: Context) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 延迟显示软键盘
     * @param view View
     * @param delayMillis Long
     */
    @JvmStatic
    fun openPostDelay(view: View, delayMillis: Long) {
        view.postDelayed({ open(view) }, delayMillis)
    }

    /**
     * 关闭软键盘
     * @param activity Activity
     */
    @JvmStatic
    fun close(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.window.peekDecorView() != null || inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 隐藏软键盘
     * @param view View
     */
    @JvmStatic
    fun close(view: View) {
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 是否打开
     * @return Boolean
     */
    @JvmStatic
    fun isOpen(): Boolean =
        (_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).isActive

    /**
     * 是否打开
     * @param view View
     * @return Boolean
     */
    @JvmStatic
    fun isOpen(view: View): Boolean =
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

    @JvmStatic
    fun observerKeyboardChangeByView(view: View): OnGlobalLayoutListener? {
        val activity: Activity = UtilKActivity.getActivityByContext(view.context, true) ?: return null
        return observerKeyboardChange(activity, object : IUtilKKeyboardListener {
            private val _location = intArrayOf(0, 0)
            override fun onKeyboardChange(keyboardBounds: Rect, isVisible: Boolean) {
                if (isVisible) {
                    view.getLocationOnScreen(_location)
                    view.translationY = view.translationY + keyboardBounds.top - (_location[1] + view.height)
                } else {
                    view.animate().translationY(0f).setDuration(300).setStartDelay(100).start()
                }
            }
        })
    }

    @JvmStatic
    fun observerKeyboardChange(activity: Activity, listener: IUtilKKeyboardListener): OnGlobalLayoutListener {
        val decorView = activity.window.decorView
        val onGlobalLayoutListener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
            private var _rect = Rect()
            private var _keyboardRect = Rect()
            private var _originalContentRect = Rect()
            private var _lastVisible = false
            private var _lastHeight = 0

            override fun onGlobalLayout() {
                val contentView = decorView.findViewById<View>(android.R.id.content) ?: return
                if (_originalContentRect.isEmpty) {
                    val destView: View = UtilKView.findViewFromParentByView(decorView, contentView)!!
                    _originalContentRect[destView.left, destView.top, destView.right] = destView.bottom
                }
                decorView.getWindowVisibleDisplayFrame(_rect)
                _keyboardRect[_rect.left, _rect.bottom, _rect.right] = _originalContentRect.bottom
                val isVisible = _keyboardRect.height() > _originalContentRect.height() shr 2 && isOpen()
                if (isVisible == _lastVisible && _keyboardRect.height() == _lastHeight) return
                _lastVisible = isVisible
                _lastHeight = _keyboardRect.height()
                listener.onKeyboardChange(_keyboardRect, isVisible)
            }
        }
        UtilKView.safeAddGlobalLayoutListener(decorView, onGlobalLayoutListener)
        return onGlobalLayoutListener
    }

    interface IUtilKKeyboardListener {
        fun onKeyboardChange(keyboardBounds: Rect, isVisible: Boolean)
    }
}