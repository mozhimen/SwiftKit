package com.mozhimen.basick.utilk.keyboard

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import com.mozhimen.basick.utilk.context.UtilKActivity
import com.mozhimen.basick.utilk.view.UtilKView


/**
 * @ClassName UtilKKeyboardChange
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/20 15:16
 * @Version 1.0
 */
object UtilKKeyboardChange {
    @JvmStatic
    fun observerKeyboardChangeByView(view: View): ViewTreeObserver.OnGlobalLayoutListener? {
        val activity: Activity = UtilKActivity.getActivityByContext(view.context, true) ?: return null
        return observerKeyboardChange(activity, object : IUtilKKeyboardChangeListener {
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
    fun observerKeyboardChange(activity: Activity, listener: IUtilKKeyboardChangeListener): ViewTreeObserver.OnGlobalLayoutListener {
        val decorView = activity.window.decorView
        val onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
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
                val isVisible = _keyboardRect.height() > _originalContentRect.height() shr 2 && UtilKKeyBoard.isActive()
                if (isVisible == _lastVisible && _keyboardRect.height() == _lastHeight) return
                _lastVisible = isVisible
                _lastHeight = _keyboardRect.height()
                listener.onKeyboardChange(_keyboardRect, isVisible)
            }
        }
        UtilKView.safeAddGlobalLayoutListener(decorView, onGlobalLayoutListener)
        return onGlobalLayoutListener
    }

    interface IUtilKKeyboardChangeListener {
        fun onKeyboardChange(keyboardBounds: Rect, isVisible: Boolean)
    }
}