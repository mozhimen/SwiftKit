package com.mozhimen.uicorek.popwink.bases.helpers

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.lintk.optin.OptInApiUse_BaseApplication
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.android.app.UtilKActivity.getByContext
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.view.UtilKView.removeViewFromParent
import com.mozhimen.basick.utilk.android.view.UtilKWindow
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.popwink.bases.commons.IClearMemoryListener


/**
 * @ClassName BasePopwinKDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/27 12:05
 * @Version 1.0
 */
class BasePopwinKDelegate(context: BasePopwinKContextWrapper) : PopupWindow(context), IClearMemoryListener, IUtilK {

    private var _basePopwinKContextWrapper: BasePopwinKContextWrapper? = null
    private var _oldFocusable = true
    private var _isHandledFullScreen = false

    fun getBasePopwinKContextWrapper(): BasePopwinKContextWrapper? = _basePopwinKContextWrapper

    init {
        _basePopwinKContextWrapper = context
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable())
        inputMethodMode = INPUT_METHOD_NEEDED
    }


    override fun update() {
        try {
            _basePopwinKContextWrapper!!.getWindowManagerDelegate()!!.update()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    private fun handleFullScreenFocusable() {
        _oldFocusable = isFocusable
        isFocusable = false
        _isHandledFullScreen = true
    }

    private fun restoreFocusable() {
        updateFocusable(_oldFocusable)
        isFocusable = _oldFocusable
        _isHandledFullScreen = false
    }

    fun updateFocusable(focusable: Boolean) {
        if (_basePopwinKContextWrapper != null && _basePopwinKContextWrapper!!.getWindowManagerDelegate() != null) {
            _basePopwinKContextWrapper!!.getWindowManagerDelegate()!!.updateFocus(focusable)
        }
    }

    fun updateFlag(mode: Int, updateImmediately: Boolean, vararg flags: Int) {
        if (_basePopwinKContextWrapper != null && _basePopwinKContextWrapper!!.getWindowManagerDelegate() != null) {
            _basePopwinKContextWrapper!!.getWindowManagerDelegate()!!.updateFlag(mode, updateImmediately, *flags)
        }
    }

    @OptIn(OptInApiUse_BaseApplication::class)
    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        if (isShowing) return
        val activity = getByContext(parent.context, false)
        if (activity == null) {
            Log.e(TAG, UtilKRes.getString(R.string.base_popwink_error_non_act_context))
            return
        }
        onBeforeShowExec(activity)
        super.showAtLocation(parent, gravity, x, y)
        onAfterShowExec(activity)
    }

    fun onBeforeShowExec(activity: Activity) {
        if (UtilKWindow.isFullScreen(activity)) {
            handleFullScreenFocusable()
        }
    }

    fun onAfterShowExec(act: Activity?) {
        if (_isHandledFullScreen) {
            var flag = (
                    CView.SystemUiFlag.LAYOUT_STABLE
                            or CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION
                            or CView.SystemUiFlag.HIDE_NAVIGATION
                            or CView.SystemUiFlag.IMMERSIVE_STICKY
                            or CView.SystemUiFlag.LAYOUT_FULLSCREEN
                            or CView.SystemUiFlag.FULLSCREEN
                    )
            if (act != null) {
                flag = act.window.decorView.systemUiVisibility
            }
            contentView.systemUiVisibility = flag
            restoreFocusable()
        }
    }

    override fun dismiss() {
        if (_basePopwinKContextWrapper != null && _basePopwinKContextWrapper!!.getHelper() != null) {
            _basePopwinKContextWrapper!!.getHelper()!!.dismiss(true)
        }
    }

    fun superDismiss() {
        try {
            if (_basePopwinKContextWrapper != null) {
                WindowManagerDelegate.PopupWindowQueueManager.getInstance().remove(_basePopwinKContextWrapper!!.getWindowManagerDelegate())
            }
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            clear(false)
        }
    }

    fun prevWindow(): WindowManagerDelegate? {
        return if (_basePopwinKContextWrapper == null || _basePopwinKContextWrapper!!.getWindowManagerDelegate() == null) {
            null
        } else _basePopwinKContextWrapper!!.getWindowManagerDelegate()!!.preWindow()
    }

    override fun clear(destroy: Boolean) {
        if (_basePopwinKContextWrapper != null) {
            _basePopwinKContextWrapper!!.clear(destroy)
        }
        try {
            removeViewFromParent(contentView)
        } catch (e: Exception) {
            e.message?.et(TAG)
        }
        if (destroy) {
            _basePopwinKContextWrapper = null
        }
    }

    /**
     * 采取ContextWrapper来hook WindowManager，从此再也无需反射及各种黑科技了~ 感谢
     * @xchengDroid https://github.com/xchengDroid  提供的方案
     */
    class BasePopwinKContextWrapper(base: Context, private var _helper: BasePopupHelper?) : ContextWrapper(base), IClearMemoryListener {
        private var _windowManagerDelegate: WindowManagerDelegate? = null

        fun getWindowManagerDelegate(): WindowManagerDelegate? =
            _windowManagerDelegate

        fun getHelper(): BasePopupHelper? =
            _helper

        override fun getSystemService(name: String): Any {
            if (TextUtils.equals(name, WINDOW_SERVICE)) {
                if (_windowManagerDelegate != null) {
                    return _windowManagerDelegate as Any
                }
                val windowManager = super.getSystemService(name) as WindowManager
                _windowManagerDelegate = WindowManagerDelegate(windowManager, _helper)
                return _windowManagerDelegate!!
            }
            return super.getSystemService(name)
        }

        override fun clear(destroy: Boolean) {
            if (_windowManagerDelegate != null) {
                _windowManagerDelegate!!.clear(destroy)
            }
            if (destroy) {
                _helper = null
                _windowManagerDelegate = null
            }
        }
    }
}