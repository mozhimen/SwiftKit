package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.kotlin.UtilKKotlinVersion
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
fun <T> T.flushClose() where T : Closeable?, T : Flushable? {
    UtilKFlushable.flushClose(this)
}

inline fun <T, R> T.use(block: (T) -> R): R where T : Closeable?, T : Flushable? =
    UtilKFlushable.flushClose(this, block)

inline fun <T, R> T.flushClose(block: (T) -> R): R where T : Closeable?, T : Flushable? =
    UtilKFlushable.flushClose(this, block)

fun <T> T?.flushCloseFinally(cause: Throwable?): Unit where T : Closeable?, T : Flushable? =
    UtilKFlushable.flushCloseFinally(this, cause)

//////////////////////////////////////////////////////////////////////////////////////

object UtilKFlushable {
    @JvmStatic
    fun <T> flushClose(t: T) where T : Closeable?, T : Flushable? {
        t?.apply { flush();close() }
    }

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
                UtilKKotlinVersion.isAtLeast(1, 1, 0) -> t.flushCloseFinally(exception)
                t == null -> {}
                exception == null -> {
                    t.flush()
                    t.close()
                }

                else ->
                    try {
                        t.flush()
                        t.close()
                    } catch (closeException: Throwable) {
                        // cause.addSuppressed(closeException) // ignored here
                    }
            }
        }
    }

    @SinceKotlin("1.1")
    @PublishedApi
    @JvmStatic
    internal fun <T> flushCloseFinally(t: T, cause: Throwable?): Unit where T : Closeable?, T : Flushable? {
        when {
            t == null -> {}
            cause == null -> {
                t.flush()
                t.close()
            }

            else ->
                try {
                    t.flush()
                    t.close()
                } catch (closeException: Throwable) {
                    cause.addSuppressed(closeException)
                }
        }
    }
}