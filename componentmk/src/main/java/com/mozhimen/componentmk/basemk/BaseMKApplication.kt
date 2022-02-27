package com.mozhimen.componentmk.basemk

import android.app.Application

/**
 * @ClassName BaseMKApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
open class BaseMKApplication : Application() {
    val TAG = "${this.javaClass.simpleName}>>>>>"
}