package com.mozhimen.basick.crashk

import com.mozhimen.basick.crashk.commons.ICrashKListener
import com.mozhimen.basick.utilk.UtilKGlobal
import java.io.File

/**
 * @ClassName CrashKNative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:34
 * @Version 1.0
 */
object CrashKNative {
    const val CRASHK_NATIVE_DIR = "crashk_native"
    private var _crashFullPath: String = getNativeCrashDir().absolutePath
    private var _crashKListener: ICrashKListener? = null

    fun init(crashKListener: ICrashKListener) {
        this._crashKListener = crashKListener
    }

    fun getNativeCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles() ?: emptyArray()
    }

    fun getNativeCrashDir(): File {
        val nativeCrashFile = File(UtilKGlobal.instance.getApp()!!.cacheDir, CRASHK_NATIVE_DIR)
        if (!nativeCrashFile.exists()) {
            nativeCrashFile.mkdirs()
        }
        return nativeCrashFile
    }
}