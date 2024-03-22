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
fun View.applyVisible() {
    UtilKViewFormat.applyVisible(this)
}

fun View.applyInVisible() {
    UtilKViewFormat.applyInVisible(this)
}

fun View.applyGone() {
    UtilKViewFormat.applyGone(this)
}

//////////////////////////////////////////////////////////////////

fun View.applyVisibleIfElseGone(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.applyVisibleIfElseGone(this, invoke)
}

fun View.applyVisibleIfElseGone(boolean: Boolean) {
    UtilKViewFormat.applyVisibleIfElseGone(this, boolean)
}

fun View.applyVisibleIfElseInVisible(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.applyVisibleIfElseInVisible(this, invoke)
}

fun View.applyVisibleIfElseInVisible(boolean: Boolean) {
    UtilKViewFormat.applyVisibleIfElseInVisible(this, boolean)
}

//////////////////////////////////////////////////////////////////

fun View.applyVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.applyVisibleIf(this, invoke)
}

fun View.applyVisibleIf(boolean: Boolean) {
    UtilKViewFormat.applyVisibleIf(this, boolean)
}

fun View.applyInVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.applyInVisibleIf(this, invoke)
}

fun View.applyInVisibleIf(boolean: Boolean) {
    UtilKViewFormat.applyInVisibleIf(this, boolean)
}

fun View.applyGoneIf(invoke: I_AListener<Boolean>) {
    UtilKViewFormat.applyGoneIf(this, invoke)
}

fun View.applyGoneIf(boolean: Boolean) {
    UtilKViewFormat.applyGoneIf(this, boolean)
}

object UtilKViewFormat {


    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyVisible(view: View) {
        if (!UtilKView.isVisible(view))
            view.visibility = View.VISIBLE
    }

    @JvmStatic
    fun applyInVisible(view: View) {
        if (!UtilKView.isInvisible(view))
            view.visibility = View.INVISIBLE
    }

    @JvmStatic
    fun applyGone(view: View) {
        if (!UtilKView.isGone(view))
            view.visibility = View.GONE
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyVisibleIfElseGone(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
        else applyGone(view)
    }

    @JvmStatic
    fun applyVisibleIfElseGone(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
        else applyGone(view)
    }

    @JvmStatic
    fun applyVisibleIfElseInVisible(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
        else applyInVisible(view)
    }

    @JvmStatic
    fun applyVisibleIfElseInVisible(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
        else applyInVisible(view)
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
    }

    @JvmStatic
    fun applyVisibleIf(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
    }

    @JvmStatic
    fun applyInVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyInVisible(view)
    }

    @JvmStatic
    fun applyInVisibleIf(view: View, boolean: Boolean) {
        if (boolean) applyInVisible(view)
    }

    @JvmStatic
    fun applyGoneIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyGone(view)
    }

    @JvmStatic
    fun applyGoneIf(view: View, boolean: Boolean) {
        if (boolean) applyGone(view)
    }
}