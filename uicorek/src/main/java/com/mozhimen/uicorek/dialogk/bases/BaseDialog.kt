package com.mozhimen.uicorek.dialogk.bases

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentDialog
import androidx.annotation.StyleRes
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKScreen.getScreenWidth2
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
@APermissionK(permissions = [Manifest.permission.SYSTEM_ALERT_WINDOW])
abstract class BaseDialog<I : IDialogKClickListener> @JvmOverloads constructor(context: Context, @StyleRes themeResId: Int = R.style.BaseDialog_Style) : ComponentDialog(context, themeResId) {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"
    private var _isHasSetWindowAttr = false
    private var _dialogMode = DialogMode.BOTH
    private var _dialogView: View? = null
    private var _dialogClickListener: I? = null

    fun getDialogClickListener(): I? {
        return _dialogClickListener
    }

    fun setDialogClickListener(onDialogButtonClickListener: I): BaseDialog<*> {
        this._dialogClickListener = onDialogButtonClickListener
        return this
    }

    /**
     * 设置dialog的模式
     * 设置后会回调到[.onInitMode]
     * @param mode
     */
    fun setDialogMode(@DialogMode mode: Int): BaseDialog<*> {
        return setDialogMode(mode, true)
    }

    @DialogMode
    fun getDialogMode(): Int {
        return _dialogMode
    }

    fun show(delayMillis: Long = 0) {
        if (delayMillis <= 0) {
            super.show()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(delayMillis)
                super@BaseDialog.show()
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

    fun isCancelable(flag: Boolean): BaseDialog<*> {
        setCancelable(flag)
        return this
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
     * 设置dialog的模式
     * 设置后会回调到[.onInitMode]
     * @param mode
     * @param callModeChange false 禁止回调[.onInitMode]
     */
    protected fun setDialogMode(@DialogMode mode: Int, callModeChange: Boolean): BaseDialog<*> {
        val hasChange = this._dialogMode != mode
        this._dialogMode = mode
        if (hasChange && callModeChange) {
            onInitMode(mode)
        }
        return this
    }

    /**
     * 初始化window宽度
     * 默认屏幕宽度左右间距25dp
     * @return
     */
    protected fun onInitWindowWidth(): Int {
        return (getScreenWidth2() * 0.8f).roundToInt()
    }

    /**
     * 初始化window高度
     * 默认wrap_content
     * @return
     */
    protected fun onInitWindowHeight(): Int {
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
            val dismiss: Boolean = _dialogClickListener!!.onClickPositive()
            if (dismiss && isShowing) dismiss()
        } else {
            if (isShowing) dismiss()
        }
    }

    protected fun onNegativeClick() {
        if (_dialogClickListener != null) {
            val dismiss: Boolean = _dialogClickListener!!.onClickNegative()
            if (dismiss && isShowing) dismiss()
        } else {
            if (isShowing) dismiss()
        }
    }
}