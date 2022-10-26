package com.mozhimen.componentk.netk.mos

/**
 * @ClassName NetKRep
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 18:42
 * @Version 1.0
 */
open class NetKRep<out T> {
    object Uninitialized : NetKRep<Nothing>()

    object Loading : NetKRep<Nothing>()

    object Empty : NetKRep<Nothing>()

    data class Success<T>(val data: T) : NetKRep<T>()

    data class Error(val exception: Throwable) : NetKRep<Nothing>()
}