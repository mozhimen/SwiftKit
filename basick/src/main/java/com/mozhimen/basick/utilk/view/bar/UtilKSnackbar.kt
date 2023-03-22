package com.mozhimen.basick.utilk.view.bar

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.mozhimen.basick.elemk.handler.bases.BaseWeakClazzMainHandler
import com.mozhimen.basick.utilk.os.thread.UtilKThread
import com.mozhimen.basick.utilk.content.UtilKApplication

/**
 * @ClassName UtilKSnackBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 17:37
 * @Version 1.0
 */
object UtilKSnackbar {
    private const val SNACK_BAR_MAX_LINES = 50//能显示的最多行数
    private val _context by lazy { UtilKApplication.instance.get() }

    /**
     * 显示
     * @param view View
     * @param msg String
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @return Snackbar
     */
    @JvmStatic
    fun show(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        return make(view, msg, duration, action, listener).also { it.show() }
    }

    /**
     * 显示
     * @param view View
     * @param msgId Int
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @return Snackbar
     */
    @JvmStatic
    fun show(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        return make(view, msgId, duration, action, listener).also { it.show() }
    }

    /**
     * 多行显示
     * @param view View
     * @param msg String
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @param maxLines Int
     * @return Snackbar
     */
    @JvmStatic
    fun showMultiLines(
        view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        return makeMultiLines(view, msg, duration, action, listener, maxLines).also { it.show() }
    }

    /**
     * 多行显示
     * @param view View
     * @param msgId Int
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @param maxLines Int
     * @return Snackbar
     */
    @JvmStatic
    fun showMultiLines(
        view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        return makeMultiLines(view, msgId, duration, action, listener, maxLines).also { it.show() }
    }

    /**
     * 主线程显示
     * @param view View
     * @param msg String
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @return Snackbar
     */
    @JvmStatic
    fun showOnMain(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        return if (UtilKThread.isMainLooper()) {
            show(view, msg, duration, action, listener)
        } else {
            val snackbar = make(view, msg, duration, action, listener)
            BaseWeakClazzMainHandler(_context).post { snackbar.show() }
            return snackbar
        }
    }

    /**
     * 主线程显示
     * @param view View
     * @param msgId Int
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @return Snackbar
     */
    @JvmStatic
    fun showOnMain(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        return if (UtilKThread.isMainLooper()) {
            show(view, msgId, duration, action, listener)
        } else {
            val snackbar = make(view, msgId, duration, action, listener)
            BaseWeakClazzMainHandler(_context).post { snackbar.show() }
            return snackbar
        }
    }

    /**
     * 主线程多行显示
     * @param view View
     * @param msg String
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @param maxLines Int
     * @return Snackbar
     */
    @JvmStatic
    fun showMultiLinesOnMain(
        view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        return if (UtilKThread.isMainLooper()) {
            showMultiLines(view, msg, duration, action, listener, maxLines)
        } else {
            val snackbar = make(view, msg, duration, action, listener)
            BaseWeakClazzMainHandler(_context).post { snackbar.show() }
            return snackbar
        }
    }

    /**
     * 主线程多行显示
     * @param view View
     * @param msgId Int
     * @param duration Int
     * @param action String
     * @param listener OnClickListener?
     * @param maxLines Int
     * @return Snackbar
     */
    @JvmStatic
    fun showMultiLinesOnMain(
        view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        return if (UtilKThread.isMainLooper()) {
            showMultiLines(view, msgId, duration, action, listener, maxLines)
        } else {
            val snackbar = makeMultiLines(view, msgId, duration, action, listener, maxLines)
            BaseWeakClazzMainHandler(_context).post { snackbar.show() }
            return snackbar
        }
    }

    @SuppressLint("ShowToast")
    @JvmStatic
    private fun make(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        Snackbar.make(view, msg, duration).apply {
            if (action.isNotEmpty() && listener != null) setAction(action, listener)
            return this
        }
    }

    @SuppressLint("ShowToast")
    @JvmStatic
    private fun make(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar {
        Snackbar.make(view, msgId, duration).apply {
            if (action.isNotEmpty() && listener != null) setAction(action, listener)
            return this
        }
    }

    @JvmStatic
    private fun makeMultiLines(
        view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        val snackbar = Snackbar.make(view, msg, duration)
        enableMultiLines(snackbar, maxLines)
        snackbar.apply {
            if (action.isNotEmpty() && listener != null) setAction(action, listener)
            return this
        }
    }

    @JvmStatic
    private fun makeMultiLines(
        view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null,
        maxLines: Int = SNACK_BAR_MAX_LINES
    ): Snackbar {
        val snackbar = Snackbar.make(view, msgId, duration)
        enableMultiLines(snackbar, maxLines)
        snackbar.apply {
            if (action.isNotEmpty() && listener != null) setAction(action, listener)
            return this
        }
    }

    @SuppressLint("RestrictedApi")
    private fun enableMultiLines(snackbar: Snackbar, maxLines: Int) {
        ((snackbar.view as ViewGroup).getChildAt(0) as SnackbarContentLayout).messageView.maxLines = maxLines
    }
}