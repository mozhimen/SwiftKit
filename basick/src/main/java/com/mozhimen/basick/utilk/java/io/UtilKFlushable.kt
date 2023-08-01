package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.kotlin.internal.UtilKPlatformImplementations
import java.io.Closeable
import java.io.Flushable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * @ClassName UtilKCloseable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 17:28
 * @Version 1.0
 */
inline fun <T, R> T.flushClose(block: (T) -> R): R where T : Closeable?, T : Flushable? =
    UtilKFlushable.flushClose(this, block)

fun Closeable?.closeFinally(cause: Throwable?) =
    UtilKFlushable.closeFinally(this, cause)

object UtilKFlushable {
    @OptIn(ExperimentalContracts::class)
    inline fun <T, R> flushClose(t: T, block: (T) -> R): R where T : Closeable?, T : Flushable? {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }
        var exception: Throwable? = null
        try {
            return block(t)
        } catch (e: Throwable) {
            exception = e
            throw e
        } finally {
            when {
                UtilKPlatformImplementations.apiVersionIsAtLeast(1, 1, 0) -> t.closeFinally(exception)
                t == null -> {}
                exception == null -> t.apply { flush();close() }
                else ->
                    try {
                        t.apply { flush();close() }
                    } catch (closeException: Throwable) {
                        // cause.addSuppressed(closeException) // ignored here
                    }
            }
        }
    }

    @SinceKotlin("1.1")
    @PublishedApi
    @JvmStatic
    internal fun closeFinally(closeable: Closeable?, cause: Throwable?): Unit =
        when {
            closeable == null -> {}
            cause == null -> closeable.close()
            else ->
                try {
                    closeable.close()
                } catch (closeException: Throwable) {
                    cause.addSuppressed(closeException)
                }
        }
}