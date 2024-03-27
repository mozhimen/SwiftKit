package com.mozhimen.basick.utilk.google.material

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.mozhimen.basick.elemk.google.material.CSnackbar

/**
 * @ClassName UtilKSnackbar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKSnackbar {
    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun getSnackbarContentLayout(snackbar: Snackbar): SnackbarContentLayout =
        (snackbar.view as ViewGroup).getChildAt(0) as SnackbarContentLayout

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun getMessageView(snackbar: Snackbar): TextView =
        getSnackbarContentLayout(snackbar).messageView


    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun make(view: View, @StringRes intResStr: Int, duration: Int): Snackbar =
        Snackbar.make(view, intResStr, duration)

    @JvmStatic
    fun make(view: View, chars: CharSequence, duration: Int): Snackbar =
        Snackbar.make(view, chars, duration)

    @JvmStatic
    fun make(context: Context, view: View, chars: CharSequence, duration: Int): Snackbar =
        Snackbar.make(context, view, chars, duration)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun show(view: View, @StringRes intResStr: Int, duration: Int) {
        make(view, intResStr, duration).show()
    }

    @JvmStatic
    fun show(view: View, chars: CharSequence, duration: Int) {
        make(view, chars, duration).show()
    }

    @JvmStatic
    fun show(context: Context, view: View, chars: CharSequence, duration: Int) {
        make(context, view, chars, duration).show()
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun show_ofLines(view: View, chars: CharSequence, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        make_ofLines(view, chars, duration, maxLines).show()
    }

    @JvmStatic
    fun show_ofLines(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        make_ofLines(view, intResStr, duration, maxLines).show()
    }

    @JvmStatic
    fun make_ofLines(view: View, chars: CharSequence, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES): Snackbar =
        UtilKSnackbar.make(view, chars, duration).apply { applyMessageView_maxLines(this, maxLines) }

    @JvmStatic
    fun make_ofLines(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES): Snackbar =
        make(view, intResStr, duration).apply { applyMessageView_maxLines(this, maxLines) }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @SuppressLint("RestrictedApi")
    fun applyMessageView_maxLines(snackbar: Snackbar, maxLines: Int) {
        getMessageView(snackbar).maxLines = maxLines
    }
}