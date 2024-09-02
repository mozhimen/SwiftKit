package com.mozhimen.dslk

/**
 * @ClassName Preview
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.view.View
import com.mozhimen.dslk.core.Ui
import com.mozhimen.kotlin.utilk.BuildConfig

//import splitties.views.dsl.core.BuildConfig
//import splitties.views.dsl.core.Ui

val View.isInPreview: Boolean
    // On release builds, BuildConfig.DEBUG is a compile time constant, and this
    //  inlined extension property evaluates to the constant false, allowing the
    //  compiler to strip any code in impossible conditions (does not even need R8).
    inline get() = BuildConfig.DEBUG && isInEditMode

val Ui.isInPreview: Boolean
    // Inlined to false on release builds, like extension for View above.
    inline get() = BuildConfig.DEBUG && root.isInEditMode