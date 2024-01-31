package com.mozhimen.basick.utilk.android.widget

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.os.UtilKHandler
import com.mozhimen.basick.utilk.android.app.isFinishingOrDestroyed
import com.mozhimen.basick.utilk.android.os.UtilKLooper
import java.lang.Exception


/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 1:11
 * @Version 1.0
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToast(this, duration)
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToast(this, duration)
}

fun String.showToastLong() {
    UtilKToast.showToast(this, Toast.LENGTH_LONG)
}

fun Int.showToastLong() {
    UtilKToast.showToast(this, Toast.LENGTH_LONG)
}

////////////////////////////////////////////////////////////////

fun String.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(this, duration)
}

fun Int.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(this, duration)
}

////////////////////////////////////////////////////////////////

fun Context.showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(msg, duration)
}

fun Context.showToastOnMain(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToast.showToastOnMain(getString(intResStr), duration)
}

////////////////////////////////////////////////////////////////

fun Exception.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    this.message?.let { UtilKToast.showToastOnMain(it, duration) }
}

object UtilKToast : BaseUtilK() {
    /**
     * 用法1: "...".showToast(context)
     */
    @JvmStatic
    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, msg, duration)
            }
        } else {
            makeToast(context, msg, duration)
        }
    }

    /**
     * 用法2: R.string.app_name.showToast(context)
     */
    @JvmStatic
    fun showToast(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed()) {
                makeToast(context, intResStr, duration)
            }
        } else {
            makeToast(context, intResStr, duration)
        }
    }

    ////////////////////////////////////////////////////////////////

    /**
     * 在主线程show
     * 用法1: "...".showToastOnMain(context)
     */
    @JvmStatic
    fun showToastOnMain(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKLooper.isMainLooper()) {
            showToast(msg, duration)
        } else {
            UtilKHandler.postOnMain { showToast(msg, duration) }
        }
    }

    /**
     * 在主线程show
     * 用法2: R.string.app_name.showToastOnMain(context)
     */
    @JvmStatic
    fun showToastOnMain(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT) {
        if (UtilKLooper.isMainLooper()) {
            showToast(intResStr, duration)
        } else {
            UtilKHandler.postOnMain { showToast(intResStr, duration) }
        }
    }

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun makeToast(context: Context, msg: String, duration: Int) {
        Toast.makeText(context, msg, duration).show()
    }

    @JvmStatic
    fun makeToast(context: Context, @StringRes intResStr: Int, duration: Int) {
        Toast.makeText(context, intResStr, duration).show()
    }
}