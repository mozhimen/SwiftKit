package com.mozhimen.componentk.netk.helpers

/**
 * @ClassName NetKThrowable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 23:32
 * @Version 1.0
 */
class NetKThrowable(var code: Int, override var message: String) : Exception()