package com.mozhimen.componentk.netk.http.commons

/**
 * @ClassName NetKRep
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 18:42
 * @Version 1.0
 */
sealed class NetKRep<out T> {
    object Uninitialized : NetKRep<Nothing>()

    object Loading : NetKRep<Nothing>()

    object Empty : NetKRep<Nothing>()

    data class MSuccess<T>(val data: T) : NetKRep<T>()

    data class MError(val exception: Throwable) : NetKRep<Nothing>()
}