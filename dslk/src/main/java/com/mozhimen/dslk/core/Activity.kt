package com.mozhimen.dslk.core

/**
 * @ClassName Activity
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/2
 * @Version 1.0
 */
import android.app.Activity
import android.view.View
import kotlin.DeprecationLevel.HIDDEN

inline var Activity.contentView: View
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setContentView(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Activity.setContentView(ui: Ui) = setContentView(ui.root)