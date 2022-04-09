package com.mozhimen.basicsk.crashk

import java.io.File

/**
 * @ClassName CrashKMgr
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:32
 * @Version 1.0
 */
object CrashKMgr {

    fun init() {
        val javaCrashDir = CrashKJava.getJavaCrashDir()
        val nativeCrashDir = CrashKNative.getNativeCrashDir()

        CrashKJava.init(javaCrashDir.absolutePath)
        CrashKNative.init(nativeCrashDir.absolutePath)
    }

    fun getCrashFiles(): Array<File> {
        return CrashKJava.getJavaCrashFiles() + CrashKNative.getNativeCrashFiles()
    }
}