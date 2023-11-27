package com.mozhimen.componentk.netk.app.download.mos

import com.mozhimen.componentk.netk.app.cons.intAppErrorCode2strAppError

/**
 * @ClassName AppDownloadException
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:10
 * @Version 1.0
 */
fun Int.intAppErrorCode2appDownloadException(): AppDownloadException =
    AppDownloadException(this)

fun Int.intAppErrorCode2appDownloadException(msg: String): AppDownloadException =
    AppDownloadException(this, msg)

class AppDownloadException : Exception {
    constructor(code: Int) : this(code, code.intAppErrorCode2strAppError())
    constructor(code: Int, msg: String) : super() {
        _code = code
        _msg = msg
    }

    private var _code: Int
    val code get() = _code
    private var _msg: String
    val msg get() = _msg

    override fun toString(): String {
        return "AppDownloadException(code=$_code, msg='$_msg')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AppDownloadException) return false

        if (_code != other._code) return false
        return _msg == other._msg
    }

    override fun hashCode(): Int {
        var result = _code
        result = 31 * result + _msg.hashCode()
        return result
    }
}