package com.mozhimen.basick.utilk.google.material

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.StringRes
import com.mozhimen.basick.elemk.google.material.CSnackbar
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.os.UtilKHandler
import com.mozhimen.basick.utilk.android.os.UtilKLooper

/**
 * @ClassName UtilKSnackBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 17:37
 * @Version 1.0
 */
object UtilKSnackbarWrapper : BaseUtilK() {

    @JvmStatic
    fun showSnackbar(view: View, chars: CharSequence, duration: Int = CSnackbar.LENGTH_SHORT) {
        UtilKSnackbar.show(view, chars, duration)
    }

    @JvmStatic
    fun showSnackbar(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT) {
        UtilKSnackbar.show(view, intResStr, duration)
    }

    @JvmStatic
    fun showSnackbar_ofLines(view: View, chars: CharSequence, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        UtilKSnackbar.show_ofLines(view, chars, duration, maxLines)
    }

    @JvmStatic
    fun showSnackbar_ofLines(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        UtilKSnackbar.show_ofLines(view, intResStr, duration, maxLines)
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSnackbarOnMain(view: View, msg: String, duration: Int = CSnackbar.LENGTH_SHORT) {
        if (UtilKLooper.isMainLooper())
            showSnackbar(view, msg, duration)
        else
            UtilKHandler.postOnMain { showSnackbar(view, msg, duration) }
    }

    @JvmStatic
    fun showSnackbarOnMain(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT) {
        if (UtilKLooper.isMainLooper())
            showSnackbar(view, intResStr, duration)
        else
            UtilKHandler.postOnMain { showSnackbar(view, intResStr, duration) }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSnackbarOnMain_ofLines(view: View, chars: CharSequence, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        if (UtilKLooper.isMainLooper())
            showSnackbar_ofLines(view, chars, duration, maxLines)
        else
            UtilKHandler.postOnMain { showSnackbar_ofLines(view, chars, duration, maxLines) }
    }

    @JvmStatic
    fun showSnackbarOnMain_ofLines(view: View, @StringRes intResStr: Int, duration: Int = CSnackbar.LENGTH_SHORT, maxLines: Int = CSnackbar.MAX_LINES) {
        if (UtilKLooper.isMainLooper())
            showSnackbar_ofLines(view, intResStr, duration, maxLines)
        else
            UtilKHandler.postOnMain { showSnackbar_ofLines(view, intResStr, duration, maxLines) }
    }
}