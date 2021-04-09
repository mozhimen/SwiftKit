package com.swiftmk.library.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @ClassName StatusBarIniter1
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/9 17:15
 * @Version 1.0
 */

class BaseApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}