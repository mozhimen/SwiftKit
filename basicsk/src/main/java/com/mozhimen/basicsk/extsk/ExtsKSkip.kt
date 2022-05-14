package com.mozhimen.basicsk.extsk

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mozhimen.basicsk.utilk.UtilKSkip

/**
 * @ClassName ExtsKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:41
 * @Version 1.0
 */
/**
 * 不带参数的跳转
 * @receiver Context
 */
inline fun <reified T> Context.start() {
    UtilKSkip.start<T>(this)
}

/**
 * 带参数的跳转
 * @receiver Context
 * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
 */
inline fun <reified T> Context.start(block: Intent.() -> Unit) {
    UtilKSkip.start<T>(this, block)
}