package com.mozhimen.basick.utilk.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @ClassName UtilKToast
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKToast {
    @SuppressLint("ShowToast")
    @JvmStatic
    fun makeText(context: Context, chars: CharSequence, duration: Int): Toast =
        Toast.makeText(context, chars, duration)

    @SuppressLint("ShowToast")
    @JvmStatic
    fun makeText(context: Context, @StringRes intResStr: Int, duration: Int): Toast =
        Toast.makeText(context, intResStr, duration)

    @JvmStatic
    fun show(context: Context, chars: CharSequence, duration: Int) {
        makeText(context, chars, duration).show()
    }

    @JvmStatic
    fun show(context: Context, @StringRes intResStr: Int, duration: Int) {
        makeText(context, intResStr, duration).show()
    }
}