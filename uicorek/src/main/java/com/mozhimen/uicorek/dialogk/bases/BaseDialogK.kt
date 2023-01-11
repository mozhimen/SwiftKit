package com.mozhimen.uicorek.dialogk.bases

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentDialog
import androidx.annotation.StyleRes
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.utilk.UtilKScreen.getCurrentScreenWidth
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.dialogk.bases.annors.DialogMode
import com.mozhimen.uicorek.dialogk.commons.IDialogKClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @ClassName BaseDialog
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 22:31
 * @Version 1.0
 */
@APermissionRequire(CPermission.SYSTEM_ALERT_WINDOW)
abstract class BaseDialogK<I : IDialogKClickListener> @JvmOverloads constructor(context: Context, @StyleRes themeResId: Int = R.style.BaseDialog_Style) : ComponentDialog(context, themeResId) {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"
    private var _isHasSetWindowAttr = false
    private var _dialogMode = DialogMode.BOTH
    private var _dialogView: View? = null
    protected var _dialogClickListener: I? = null

    fun getDialogClickListener(): I? {
        return _dialogClickListener
    }

    @DialogMode
    fun getDialogMode(): Int {
        return _dialogMode
    }

    /**
     * 设置dialog的模式, 设置后会回调到[.onInitMode]
     * @param mode
     */
    fun setDialogMode(@DialogMode mode: Int): BaseDialogK<*> {
        return setDialogMode(mode, true)
    }

    /**
     * 设置dialog的模式
     * 设置后会回调到[.onInitMode]
     * @param mode
     * @param callModeChange false 禁止回调[.onInitMode]
     */
    protected fun setDialogMode(@DialogMode mode: Int, callModeChange: Boolean): BaseDialogK<*> {
        val hasChange = this._dialogMode != mode
        this._dialogMode = mode
        if (hasChange && callModeChange) {
            onInitMode(mode)
        }
        return this
    }

    fun setDialogClickListener(onDialogButtonClickListener: I): BaseDialogK<*> {
        this._dialogClickListener = onDialogButtonClickListener
        return this
    }

    fun setDialogCancelable(flag: Boolean): BaseDialogK<*> {
        setCancelable(flag)
        return this
    }

    fun show(delayMillis: Long = 0) {
        if (delayMillis <= 0) {
            super.show()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(delayMillis)
                super@BaseDialogK.show()
            }
        }
    }

    /**
     * 不依附Activity来show，比如在service里面
     * 此举将会把dialog的window level提升为system
     * 需要权限
     * <h3>uses-permission Android:name="android.permission.SYSTEM_ALERT_WINDOW"
    </h3> */
    fun showInSystemWindow() {
        try {
            val window = window ?: return
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
            show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (_dialogView == null) {
            _dialogView = onCreateView(LayoutInflater.from(context))
        }
        if (_dialogView == null) return
        onFindView(_dialogView!!)
        onInitMode(_dialogMode)
        setContentView(_dialogView!!)
        if (window != null && !_isHasSetWindowAttr) {
            val layoutParams = window!!.attributes
            layoutParams.width = onInitWindowWidth()
            layoutParams.height = onInitWindowHeight()
            layoutParams.gravity = onInitWindowGravity()
            window!!.attributes = layoutParams
            _isHasSetWindowAttr = true
        }
    }

    protected abstract fun onCreateView(inflater: LayoutInflater): View?

    protected abstract fun onFindView(dialogView: View)

    protected abstract fun onInitMode(@DialogMode mode: Int)

    /**
     * 初始化window宽度
     * 默认屏幕宽度左右间距25dp
     * @return
     */
    protected open fun onInitWindowWidth(): Int {
        return (getCurrentScreenWidth() * 0.8f).roundToInt()
    }

    /**
     * 初始化window高度
     * 默认wrap_content
     * @return
     */
    protected open fun onInitWindowHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 初始化window的gravity
     * @return 默认返回 Gravity.CENTER
     * @see Gravity
     */
    protected fun onInitWindowGravity(): Int {
        return Gravity.CENTER
    }

    protected fun onPositiveClick() {
        if (_dialogClickListener != null) {
            val dismiss: Boolean = _dialogClickListener!!.onClickPositive(_dialogView)
            if (dismiss && isShowing) dismiss()
        } else {
            if (isShowing) dismiss()
        }
    }

    protected fun onNegativeClick() {
        if (_dialogClickListener != null) {
            val dismiss: Boolean = _dialogClickListener!!.onClickNegative(_dialogView)
            if (dismiss && isShowing) dismiss()
        } else {
            if (isShowing) dismiss()
        }
    }
}