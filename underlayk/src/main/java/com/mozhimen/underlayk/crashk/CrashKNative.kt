package com.mozhimen.underlayk.crashk

import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
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
    var _crashFullPath: String = UtilKGlobal.instance.getApp()!!.cacheDir.absolutePath + "/crashk_native"
        get() {
            val crashFullPath = UtilKGlobal.instance.getApp()!!.cacheDir.absolutePath + "/crashk_native"
            UtilKFile.createFolder(crashFullPath)
            return crashFullPath.also { field = it }
        }

    private var _crashKListener: ICrashKListener? = null

    fun init(crashKListener: ICrashKListener) {
        this._crashKListener = crashKListener
    }

    fun getNativeCrashFiles(): Array<File> {
        return File(_crashFullPath).listFiles() ?: emptyArray()
    }

    fun sss() =  _crashFullPath
}