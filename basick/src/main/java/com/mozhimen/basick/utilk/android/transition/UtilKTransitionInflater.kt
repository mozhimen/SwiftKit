package com.mozhimen.basick.utilk.android.transition

import android.content.Context
import android.transition.Transition
import android.transition.TransitionInflater
import androidx.annotation.TransitionRes

/**
 * @ClassName TransitionInflater
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/25
 * @Version 1.0
 */
object UtilKTransitionInflater {
    @JvmStatic
    fun from_inflateTransition(context: Context, @TransitionRes intTransitionRes: Int): Transition =
        TransitionInflater.from(context).inflateTransition(intTransitionRes)
}