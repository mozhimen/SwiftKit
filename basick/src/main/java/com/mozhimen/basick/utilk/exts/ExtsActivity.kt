package com.mozhimen.basick.utilk.exts

import android.app.Activity
import android.content.Intent
import com.mozhimen.basick.utilk.content.UtilKActivityStart

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
inline fun <reified T> Activity.start() where T : Activity {
    UtilKActivityStart.start<T>(this)
}

/**
 * 带参数的跳转
 * @receiver Context
 * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
 */
inline fun <reified T> Activity.start(block: Intent.() -> Unit) where T : Activity {
    UtilKActivityStart.start<T>(this, block)
}