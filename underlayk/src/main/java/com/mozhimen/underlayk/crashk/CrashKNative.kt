package com.mozhimen.underlayk.crashk

import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.crashk_native.CrashKNativeLib
import java.io.File

/**
 * @ClassName CrashKNative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:34
 * @Version 1.0
 */
class CrashKNative {
    private var _crashKListener: ICrashKListener? = null
    var crashPathNative: String? = null
        get() {
            if (field != null) return field
            val crashFullPath = UtilKStrPath.Absolute.Internal.getCacheDir() + "/crashk_native"
            UtilKFile.createFolder(crashFullPath)
            return crashFullPath.also { field = it }
        }

    fun init(listener: ICrashKListener?) {
        listener?.let { this._crashKListener = it }
        CrashKNativeLib.init(crashPathNative!!)
    }

    fun getNativeCrashFiles(): Array<File> {
        return File(crashPathNative!!).listFiles() ?: emptyArray()
    }
}