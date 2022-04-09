package com.mozhimen.basicsmk.crashmk

import java.io.File

/**
 * @ClassName CrashMKMgr
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:32
 * @Version 1.0
 */
object CrashMKMgr {

    fun init() {
        val javaCrashDir = CrashMKJava.getJavaCrashDir()
        val nativeCrashDir = CrashMKNative.getNativeCrashDir()

        CrashMKJava.init(javaCrashDir.absolutePath)
        CrashMKNative.init(nativeCrashDir.absolutePath)
    }

    fun getCrashFiles(): Array<File> {
        return CrashMKJava.getJavaCrashFiles() + CrashMKNative.getNativeCrashFiles()
    }
}