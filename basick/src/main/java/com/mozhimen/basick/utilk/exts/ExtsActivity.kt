package com.mozhimen.basick.utilk.exts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.utilk.content.UtilKContextStart
import com.mozhimen.basick.utilk.content.activity.UtilKActivity

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
inline fun <reified T> Activity.startContext() where T : Activity {
    UtilKContextStart.startContext<T>(this)
}

/**
 * 带参数的跳转
 * @receiver Context
 * @param block [@kotlin.ExtensionFunctionType] Function1<Intent, Unit>
 */
inline fun <reified T> Activity.startContext(block: Intent.() -> Unit) where T : Activity {
    UtilKContextStart.startContext<T>(this, block)
}

fun Activity.isFinishingOrDestroyed(): Boolean =
    UtilKActivity.isFinishingOrDestroyed(this)

fun AppCompatActivity.runOnBackThread(block: () -> Unit) {
    UtilKActivity.runOnBackThread(this, block)
}
