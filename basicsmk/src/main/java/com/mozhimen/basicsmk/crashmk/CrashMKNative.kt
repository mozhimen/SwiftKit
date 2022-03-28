package com.mozhimen.basicsmk.crashmk

import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import java.io.File

/**
 * @ClassName CrashMKNative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:34
 * @Version 1.0
 */
object CrashMKNative {
    const val CRASHMK_NATIVE_DIR = "crashmk_native_dir"
    private var _crashFullPath: String? = null

    fun init(crashDir: String) {
        this._crashFullPath = crashDir
    }

    fun getNativeCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles()
    }

    fun getNativeCrashDir(): File {
        val nativeCrashFile = File(UtilMKGlobal.instance.getApp()!!.cacheDir, CRASHMK_NATIVE_DIR)
        if (!nativeCrashFile.exists()) {
            nativeCrashFile.mkdirs()
        }
        return nativeCrashFile
    }
}