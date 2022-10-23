package com.mozhimen.componentk.netk.coroutine

/**
 * @ClassName NetKRep
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 18:42
 * @Version 1.0
 */
class NetKRep<T> {
    enum class Status {
        Uninitlized,
        Loading,
        Success,
        Error
    }

    val status: Status = Status.Uninitlized
    val data: T? = null
    val msg: String = ""
//    object Uninitialized : NetKRep<Nothing>()
//
//    object Loading : NetKRep<Nothing>()
//
//    data class Success<T>(val data: T) : NetKRep<T>()
//
//    data class Error(val exception: Throwable) : NetKRep<Nothing>()
}