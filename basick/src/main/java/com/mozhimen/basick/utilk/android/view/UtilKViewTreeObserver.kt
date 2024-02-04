package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.widget.FrameLayout
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.cons.CCons
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKViewTreeObserver
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/29 0:15
 * @Version 1.0
 */
object UtilKViewTreeObserver {
    /**
     * Register soft input changed listener.
     */
    @JvmStatic
    fun registerInputChangeListener(activity: Activity, listener: IA_Listener<Int>) {
        registerInputChangeListener(activity.window, listener)
    }

    /**
     * Register soft input changed listener.
     */
    @JvmStatic
    fun registerInputChangeListener(window: Window, listener: IA_Listener<Int>) {
        if (UtilKWindow.getAttributesFlags(window) and CWinMgr.Lpf.LAYOUT_NO_LIMITS != 0)
            window.clearFlags(CWinMgr.Lpf.LAYOUT_NO_LIMITS)
        val contentView = UtilKContentView.get<FrameLayout>(window)
        val decorViewInvisibleHeightPre = intArrayOf(UtilKDecorView.getInvisibleHeight(window))
        val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val height = UtilKDecorView.getInvisibleHeight(window)
            if (decorViewInvisibleHeightPre[0] != height) {
                listener.invoke(height)
                decorViewInvisibleHeightPre[0] = height
            }
        }
        contentView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        contentView.setTag(CCons.UTILK_INPUT_CHANGE_TAG_ON_GLOBAL_LAYOUT_LISTENER, onGlobalLayoutListener)
    }

    /**
     * Unregister soft input changed listener.
     */
    @JvmStatic
    fun unregisterInputChangeListener(window: Window) {
        val contentView = UtilKContentView.get<View>(window)
        val tag = contentView.getTag(CCons.UTILK_INPUT_CHANGE_TAG_ON_GLOBAL_LAYOUT_LISTENER)
        if (tag is ViewTreeObserver.OnGlobalLayoutListener) {
            if (UtilKBuildVersion.isAfterV_16_41_J()) {
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(tag)
                contentView.setTag(CCons.UTILK_INPUT_CHANGE_TAG_ON_GLOBAL_LAYOUT_LISTENER, null)//这里会发生内存泄漏 如果不设置为null
            }
        }
    }

    @OApiUse_BaseApplication
    @JvmStatic
    fun observerInputChangeByView(view: View): ViewTreeObserver.OnGlobalLayoutListener? {
        return observerInputChange(UtilKActivity.getByContext(view.context, true) ?: return null,
            object : IAB_Listener<Rect, Boolean> {
                private val _location = intArrayOf(0, 0)
                override fun invoke(keyboardBounds: Rect, isVisible: Boolean) {
                    if (isVisible) {
                        view.getLocationOnScreen(_location)
                        view.translationY = view.translationY + keyboardBounds.top - (_location[1] + view.height)
                    } else
                        view.animate().translationY(0f).setDuration(300).setStartDelay(100).start()
                }
            })
    }

    @JvmStatic
    fun observerInputChange(activity: Activity, listener: IAB_Listener<Rect, Boolean>): ViewTreeObserver.OnGlobalLayoutListener {
        val decorView = UtilKDecorView.get(activity)
        val onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            private var _rect = Rect()
            private var _keyboardRect = Rect()
            private var _originalContentRect = Rect()
            private var _lastVisible = false
            private var _lastHeight = 0

            override fun onGlobalLayout() {
                val contentView = UtilKDecorView.getContentView(activity) ?: return
                if (_originalContentRect.isEmpty) {
                    val destView: View = UtilKView.findViewForParentByView(decorView, contentView)!!
                    _originalContentRect[destView.left, destView.top, destView.right] = destView.bottom
                }
                decorView.getWindowVisibleDisplayFrame(_rect)
                _keyboardRect[_rect.left, _rect.bottom, _rect.right] = _originalContentRect.bottom
                val isVisible = _keyboardRect.height() > _originalContentRect.height() shr 2 && UtilKInputMethodManager.isActive(activity)
                if (isVisible == _lastVisible && _keyboardRect.height() == _lastHeight) return
                _lastVisible = isVisible
                _lastHeight = _keyboardRect.height()
                listener.invoke(_keyboardRect, isVisible)
            }
        }
        UtilKView.applySafeAddOnGlobalLayoutObserver(decorView, onGlobalLayoutListener)
        return onGlobalLayoutListener
    }
}

//interface IUtilKKeyboardChangeListener {
//    fun onChange(keyboardBounds: Rect, isVisible: Boolean)
//}
//interface IUtilKKeyboardChangeListener2 {
//    fun onChange(height: Int)
//}
//typealias IUtilKKeyboardChangeListener = IAB_Listener<Rect, Boolean>
//typealias IUtilKKeyboardChangeListener2 = IA_Listener<Int>