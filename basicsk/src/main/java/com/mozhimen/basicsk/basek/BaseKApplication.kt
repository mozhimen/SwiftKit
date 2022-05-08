package com.mozhimen.basicsk.basek

import android.app.Application

/**
 * @ClassName BaseKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
open class BaseKApplication : Application() {
    val TAG = "${this.javaClass.simpleName}>>>>>"
}