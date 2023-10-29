package com.mozhimen.basick.utilk.android.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.Exception

/**
 * @ClassName UtilKApplication
 * @Description 这种方式获取全局的Application 是一种拓展思路。
 * 对于组件化项目,不可能把项目实际的Application下沉到Base,而且各个module也不需要知道Application真实名字
 * 这种一次反射就能获取全局Application对象的方式相比于在Application#OnCreate保存一份的方式显示更加通用了
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 15:02
 * @Version 1.0
 */
class UtilKApplicationReflect {
    companion object {
        @JvmStatic//单例内部静态类,线程安全
        val instance = INSTANCE.holder
    }

    private var _application: Application? = null

    /////////////////////////////////////////////////////////////

    /**
     * 获取全局上下文
     */
    @SuppressLint("PrivateApi")
    fun get(): Application {
        if (_application == null) {
            try {
                _application = "android.app.ActivityThread".strPackage2clazz().getMethod("currentApplication").invoke(null) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return _application!!
    }

    val applicationContext: Context
        get() = get().applicationContext

    /////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = UtilKApplicationReflect()
    }
}