package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.ISuspend_AListener

/**
 * @ClassName UtilKKotlin
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
object UtilKResult {
    @JvmStatic
    tailrec suspend fun <T> runCatching_ofRetry(retries: Int, block: ISuspend_AListener<T>): Result<T> {
        require(retries >= 1)
        val result = runCatching { block() }
        return when {
            retries == 1 -> result
            result.isSuccess -> result
            else -> runCatching_ofRetry(retries - 1, block)
        }
    }
}

