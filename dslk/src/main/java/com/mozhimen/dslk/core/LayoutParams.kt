package com.mozhimen.dslk.core

/**
 * @ClassName LayoutParams
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.view.View
import android.view.ViewGroup

/**
 * **A LESS CAPITALIZED ALIAS** to [ViewGroup.LayoutParams.MATCH_PARENT] that is only
 * visible inside [ViewGroup]s.
 */
@Suppress("unused")
inline val View.matchParent
    get() = ViewGroup.LayoutParams.MATCH_PARENT

/**
 * **A LESS CAPITALIZED ALIAS** to [ViewGroup.LayoutParams.WRAP_CONTENT] that is only
 * visible inside [ViewGroup]s.
 */
@Suppress("unused")
inline val View.wrapContent
    get() = ViewGroup.LayoutParams.WRAP_CONTENT