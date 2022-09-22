package com.mozhimen.underlayk.crashk

import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import java.io.File

/**
 * @ClassName CrashKMgr
 * @Description <uses-permission
android:name="android.permission.WRITE_EXTERNAL_STORAGE"
tools:ignore="ScopedStorage" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:32
 * @Version 1.0
 */
class CrashKMgr {

    companion object {
        @JvmStatic
        val instance = CrashKMgrHolder.holder
    }

    private object CrashKMgrHolder {
        val holder = CrashKMgr()
    }

    fun init(crashKListener: ICrashKListener) {
        CrashKJava.init(crashKListener)
        CrashKNative.init(crashKListener)
    }

    fun getCrashFiles(): Array<File> {
        return CrashKJava.getJavaCrashFiles() + CrashKNative.getNativeCrashFiles()
    }
}