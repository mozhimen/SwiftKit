package com.mozhimen.basicsk.crashk

import com.mozhimen.basicsk.utilk.UtilKGlobal
import java.io.File

/**
 * @ClassName CrashKNative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:34
 * @Version 1.0
 */
object CrashKNative {
    const val CRASHK_NATIVE_DIR = "crashk_native_dir"
    private var _crashFullPath: String? = null

    fun init(crashDir: String) {
        this._crashFullPath = crashDir
    }

    fun getNativeCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles()
    }

    fun getNativeCrashDir(): File {
        val nativeCrashFile = File(UtilKGlobal.instance.getApp()!!.cacheDir, CRASHK_NATIVE_DIR)
        if (!nativeCrashFile.exists()) {
            nativeCrashFile.kdirs()
        }
        return nativeCrashFile
    }
}