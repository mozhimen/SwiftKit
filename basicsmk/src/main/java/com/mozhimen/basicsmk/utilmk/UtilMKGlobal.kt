package com.mozhimen.basicsmk.utilmk

import android.annotation.SuppressLint
import android.app.Application
import java.lang.Exception

/**
 * @ClassName GlobalMK
 * @Description 这种方式获取全局的Application 是一种拓展思路。
 * 对于组件化项目,不可能把项目实际的Application下沉到Base,而且各个module也不需要知道Application真实名字
 * 这种一次反射就能获取全局Application对象的方式相比于在Application#OnCreate保存一份的方式显示更加通用了
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:02
 * @Version 1.0
 */
class UtilMKGlobal {
    companion object {
        //单例内部静态类,线程安全
        val instance = UtilMkGlobalProvider.holder
    }

    private object UtilMkGlobalProvider {
        val holder = UtilMKGlobal()
    }

    private var application: Application? = null

    @SuppressLint("PrivateApi")
    fun getApp(): Application? {
        if (application == null) {
            try {
                application = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null) as Application
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return application
    }
}