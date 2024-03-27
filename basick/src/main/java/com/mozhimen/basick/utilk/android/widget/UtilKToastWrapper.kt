package com.mozhimen.basick.utilk.android.widget

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.mozhimen.basick.elemk.android.widget.cons.CToast
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
/**
 * 用法1: "...".showToast(context)
 */
/**
 * 用法2: R.string.app_name.showToast(context)
 */
/**
 * 在主线程show
 * 用法1: "...".showToastOnMain(context)
 */
/**
 * 在主线程show
 * 用法2: R.string.app_name.showToastOnMain(context)
 */

fun CharSequence.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(this, duration)
}

fun CharSequence.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(this, duration, context)
}

fun Context.showToast(chars: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(chars, duration, this)
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(this, duration)
}

fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(this, duration, context)
}

fun Context.showToast(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToast(intResStr, duration, this)
}

////////////////////////////////////////////////////////////////

fun CharSequence.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(this, duration)
}

fun CharSequence.showToastOnMain(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(this, duration, context)
}

fun Context.showToastOnMain(chars: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(chars, duration, this)
}

fun Int.showToastOnMain(duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(this, duration)
}

fun Int.showToastOnMain(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(this, duration, context)
}

fun Context.showToastOnMain(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT) {
    UtilKToastWrapper.showToastOnMain(intResStr, duration, this)
}

////////////////////////////////////////////////////////////////

fun Exception.showToast(duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToast(this, duration)
}

fun Exception.showToast(context: Context, duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToast(this, duration, context)
}

fun Context.showToast(exception: Exception, duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToast(exception, duration, this)
}

fun Exception.showToastOnMain(duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToastOnMain(this, duration)
}

fun Exception.showToastOnMain(context: Context, duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToastOnMain(this, duration, context)
}

fun Context.showToastOnMain(exception: Exception, duration: Int = Toast.LENGTH_LONG) {
    UtilKToastWrapper.showToastOnMain(exception, duration, this)
}

////////////////////////////////////////////////////////////////


object UtilKToastWrapper : BaseUtilK() {

    @JvmStatic
    fun showToast(chars: CharSequence, duration: Int = CToast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed())
                UtilKToast.show(context, chars, duration)
        } else
            UtilKToast.show(context, chars, duration)
    }

    @JvmStatic
    fun showToast(@StringRes intResStr: Int, duration: Int = CToast.LENGTH_SHORT, context: Context = _context) {
        if (context is Activity) {
            if (!context.isFinishingOrDestroyed())
                UtilKToast.show(context, intResStr, duration)
        } else
            UtilKToast.show(context, intResStr, duration)
    }

    @JvmStatic
    fun showToast(exception: Exception, duration: Int = CToast.LENGTH_SHORT, context: Context = _context) {
        exception.message?.let { showToast(it, duration, context) }
    }

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showToastOnMain(chars: CharSequence, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (UtilKLooper.isMainLooper())
            showToast(chars, duration, context)
        else
            UtilKHandler.postOnMain { showToast(chars, duration, context) }
    }

    @JvmStatic
    fun showToastOnMain(@StringRes intResStr: Int, duration: Int = Toast.LENGTH_SHORT, context: Context = _context) {
        if (UtilKLooper.isMainLooper())
            showToast(intResStr, duration, context)
        else
            UtilKHandler.postOnMain { showToast(intResStr, duration, context) }
    }

    @JvmStatic
    fun showToastOnMain(exception: Exception, duration: Int = CToast.LENGTH_SHORT, context: Context = _context) {
        exception.message?.let { showToastOnMain(it, duration, context) }
    }
}