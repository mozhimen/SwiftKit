@file:OptIn(ExperimentalContracts::class)

package com.mozhimen.dslk.core

/**
 * @ClassName LinearLayout
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.widget.LinearLayout
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun LinearLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    initParams: LinearLayout.LayoutParams.() -> Unit = {}
): LinearLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LinearLayout.LayoutParams(width, height).apply(initParams)
}

inline fun LinearLayout.lParams(
    width: Int = wrapContent,
    height: Int = wrapContent,
    gravity: Int = -1,
    weight: Float = 0f,
    initParams: LinearLayout.LayoutParams.() -> Unit = {}
): LinearLayout.LayoutParams {
    contract { callsInPlace(initParams, InvocationKind.EXACTLY_ONCE) }
    return LinearLayout.LayoutParams(width, height).also {
        it.gravity = gravity
        it.weight = weight
    }.apply(initParams)
}