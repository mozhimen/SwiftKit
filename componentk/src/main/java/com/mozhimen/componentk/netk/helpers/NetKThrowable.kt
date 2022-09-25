package com.mozhimen.componentk.netk.helpers

/**
 * @ClassName NetKThrowable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 23:32
 * @Version 1.0
 */
class NetKThrowable : Exception {

    var code = 0
    override var message: String? = null

    constructor(throwable: Throwable?, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(code: Int, message: String?) {
        this.code = code
        this.message = message
    }
}