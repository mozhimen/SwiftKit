package com.mozhimen.basick.utilk.androidx.core

import android.content.Context
import androidx.annotation.AnimRes
import androidx.core.app.ActivityOptionsCompat

/**
 * @ClassName UtilKActivityOptionsCompat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/25
 * @Version 1.0
 */
object UtilKActivityOptionsCompat {
    @JvmStatic
    fun makeCustomAnimation(context: Context, @AnimRes intAnimResEnter: Int, @AnimRes intAnimResExit: Int): ActivityOptionsCompat =
        ActivityOptionsCompat.makeCustomAnimation(context, intAnimResEnter, intAnimResExit)
}