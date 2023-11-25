package com.mozhimen.componentk.netk.app.download.mos

import com.mozhimen.componentk.netk.app.cons.intAppErrorCode2strAppError

/**
 * @ClassName AppDownloadException
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:10
 * @Version 1.0
 */
fun Int.int2appDownloadException(): AppDownloadException =
    AppDownloadException(this)

fun Int.int2appDownloadException(msg: String): AppDownloadException =
    AppDownloadException(this, msg)

class AppDownloadException : Exception {
    constructor(code: Int) : this(code, code.intAppErrorCode2strAppError())
    constructor(code: Int, msg: String) : super() {
        _code = code
        _msg = msg
    }

    private var _code: Int
    private var _msg: String

    override fun toString(): String {
        return "AppDownloadException(code=$_code, msg='$_msg')"
    }
}