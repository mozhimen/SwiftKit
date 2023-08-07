package com.mozhimen.basick.utilk.android.view

import android.view.View
import com.mozhimen.basick.elemk.commons.I_AListener

/**
 * @ClassName UtilKViewFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 14:13
 * @Version 1.0
 */
fun View.asVisible() {
    UtilKViewFormat.asVisible(this)
}

fun View.asInVisible() {
    UtilKViewFormat.asInVisible(this)
}

fun View.asGone() {
    UtilKViewFormat.asGone(this)
}

fun View.asVisibleIfElseGone(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.asVisibleIfElseGone(this, invoke)
}

fun View.asVisibleIfElseGone(boolean: Boolean) {
    UtilKViewFormat.asVisibleIfElseGone(this, boolean)
}

fun View.asVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.asVisibleIf(this, invoke)
}

fun View.asInVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.asInVisibleIf(this, invoke)
}

fun View.asGoneIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.asGoneIf(this, invoke)
}

fun View.asVisibleIf(boolean: Boolean) {
    UtilKViewFormat.asVisibleIf(this, boolean)
}

fun View.asInVisibleIf(boolean: Boolean) {
    UtilKViewFormat.asInVisibleIf(this, boolean)
}

fun View.asGoneIf(boolean: Boolean) {
    UtilKViewFormat.asGoneIf(this, boolean)
}

object UtilKViewFormat {
    @JvmStatic
    fun asVisible(view: View) {
        if (!UtilKView.isVisible(view)) view.visibility = View.VISIBLE
    }

    @JvmStatic
    fun asInVisible(view: View) {
        if (!UtilKView.isInvisible(view)) view.visibility = View.INVISIBLE
    }

    @JvmStatic
    fun asGone(view: View) {
        if (!UtilKView.isGone(view)) view.visibility = View.GONE
    }

    @JvmStatic
    fun asVisibleIfElseGone(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) asVisible(view) else asGone(view)
    }

    @JvmStatic
    fun asVisibleIfElseGone(view: View, boolean: Boolean) {
        if (boolean) asVisible(view) else asGone(view)
    }

    @JvmStatic
    fun asVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) asVisible(view)
    }

    @JvmStatic
    fun asVisibleIf(view: View, boolean: Boolean) {
        if (boolean) asVisible(view)
    }

    @JvmStatic
    fun asInVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) asInVisible(view)
    }

    @JvmStatic
    fun asInVisibleIf(view: View, boolean: Boolean) {
        if (boolean) asInVisible(view)
    }

    @JvmStatic
    fun asGoneIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) asGone(view)
    }

    @JvmStatic
    fun asGoneIf(view: View, boolean: Boolean) {
        if (boolean) asGone(view)
    }
}