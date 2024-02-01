package com.mozhimen.basick.utilk.kotlinx.coroutines

import com.mozhimen.basick.elemk.commons.I_Listener
import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * @ClassName UtilKCancellableContinuation
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/1
 * @Version 1.0
 */
inline fun <T> Continuation<T>.safeResume(value: T, onCancel: I_Listener) {
    UtilKCancellableContinuation.safeResume(this, value, onCancel)
}

fun <T> Continuation<T>.safeResume(value: T) {
    UtilKCancellableContinuation.safeResume(this, value)
}

object UtilKCancellableContinuation {
    @JvmStatic
    inline fun <T> safeResume(continuation: Continuation<T>, value: T, onCancel: I_Listener) {
        if (continuation is CancellableContinuation) {
            if (continuation.isActive)
                continuation.resume(value)
            else
                onCancel.invoke()
        }
    }

    @JvmStatic
    fun <T> safeResume(continuation: Continuation<T>, value: T) {
        if (continuation is CancellableContinuation && continuation.isActive) {
            continuation.resume(value)
        }
    }
}