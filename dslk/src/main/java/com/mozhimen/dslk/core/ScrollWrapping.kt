@file:OptIn(ExperimentalContracts::class)

package com.mozhimen.dslk.core

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.IdRes
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * @ClassName ScrollWrapping
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */

inline fun View.wrapInScrollView(
    @IdRes id: Int = View.NO_ID,
    height: Int = wrapContent,
    initView: ScrollView.() -> Unit = {}
): ScrollView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::ScrollView, id) {
        add(this@wrapInScrollView, lParams(width = matchParent, height = height))
    }.apply(initView)
}

inline fun View.wrapInHorizontalScrollView(
    @IdRes id: Int = View.NO_ID,
    height: Int = wrapContent,
    initView: HorizontalScrollView.() -> Unit = {}
): HorizontalScrollView {
    contract { callsInPlace(initView, InvocationKind.EXACTLY_ONCE) }
    return view(::HorizontalScrollView, id) {
        add(this@wrapInHorizontalScrollView, lParams(width = matchParent, height = height))
    }.apply(initView)
}