package com.mozhimen.swiftmk.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:07
 * @Version 1.0
 */
open class BaseApplication : Application() {
    val mTag: String = this.javaClass.canonicalName

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}