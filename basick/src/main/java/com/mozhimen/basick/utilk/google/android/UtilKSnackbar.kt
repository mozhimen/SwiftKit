package com.mozhimen.basick.utilk.google.android

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.mozhimen.basick.elemk.cons.CCons
import com.mozhimen.basick.elemk.android.os.bases.BaseWeakRefMainHandler
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.os.UtilKHandler

/**
 * @ClassName UtilKSnackBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 17:37
 * @Version 1.0
 */
object UtilKSnackbar : BaseUtilK() {
    @JvmStatic
    fun showSnackbar(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null) {
        make(view, msg, duration, action, listener).show()
    }

    @JvmStatic
    fun showSnackbar(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null) {
        make(view, msgId, duration, action, listener).show()
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSnackbarMultiLines(
        view: View,
        msg: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ) {
        makeMultiLines(view, msg, duration, action, listener, maxLines).show()
    }

    @JvmStatic
    fun showSnackbarMultiLines(
        view: View,
        @StringRes msgId: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ) {
        makeMultiLines(view, msgId, duration, action, listener, maxLines).show()
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSnackbarOnMain(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null) {
        if (UtilKHandler.isMainLooper())
            showSnackbar(view, msg, duration, action, listener)
        else
            BaseWeakRefMainHandler(_context).post { showSnackbar(view, msg, duration, action, listener) }
    }

    @JvmStatic
    fun showSnackbarOnMain(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null) {
        if (UtilKHandler.isMainLooper())
            showSnackbar(view, msgId, duration, action, listener)
        else
            BaseWeakRefMainHandler(_context).post { showSnackbar(view, msgId, duration, action, listener) }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSnackbarMultiLinesOnMain(
        view: View,
        msg: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ) {
        if (UtilKHandler.isMainLooper())
            showSnackbarMultiLines(view, msg, duration, action, listener, maxLines)
        else
            BaseWeakRefMainHandler(_context).post { showSnackbarMultiLines(view, msg, duration, action, listener, maxLines) }
    }

    @JvmStatic
    fun showSnackbarMultiLinesOnMain(
        view: View,
        @StringRes msgId: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ) {
        if (UtilKHandler.isMainLooper())
            showSnackbarMultiLines(view, msgId, duration, action, listener, maxLines)
        else
            BaseWeakRefMainHandler(_context).post { showSnackbarMultiLines(view, msgId, duration, action, listener, maxLines) }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("ShowToast")
    @JvmStatic
    fun make(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar =
        Snackbar.make(view, msg, duration).apply { if (action.isNotEmpty() && listener != null) setAction(action, listener) }

    @SuppressLint("ShowToast")
    @JvmStatic
    fun make(view: View, @StringRes msgId: Int, duration: Int = Snackbar.LENGTH_SHORT, action: String = "", listener: View.OnClickListener? = null): Snackbar =
        Snackbar.make(view, msgId, duration).apply { if (action.isNotEmpty() && listener != null) setAction(action, listener) }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun makeMultiLines(
        view: View,
        msg: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ): Snackbar =
        make(view, msg, duration, action, listener).apply { enableMultiLines(this, maxLines) }

    @JvmStatic
    fun makeMultiLines(
        view: View,
        @StringRes msgId: Int,
        duration: Int = Snackbar.LENGTH_SHORT,
        action: String = "",
        listener: View.OnClickListener? = null,
        maxLines: Int = CCons.UTILK_SNACK_BAR_MAX_LINES
    ): Snackbar =
        make(view, msgId, duration, action, listener).apply { enableMultiLines(this, maxLines) }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @SuppressLint("RestrictedApi")
    fun enableMultiLines(snackbar: Snackbar, maxLines: Int) {
        ((snackbar.view as ViewGroup).getChildAt(0) as SnackbarContentLayout).messageView.maxLines = maxLines
    }
}