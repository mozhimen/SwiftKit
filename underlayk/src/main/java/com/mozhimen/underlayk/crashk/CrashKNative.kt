package com.mozhimen.underlayk.crashk

import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.crashk_native.CrashKNativeLib
import com.mozhimen.underlayk.crashk.commons.ICrashK
import java.io.File

/**
 * @ClassName CrashKNative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:34
 * @Version 1.0
 */
class CrashKNative : ICrashK {
    private var _crashKListener: ICrashKListener? = null

    var crashPathNative: String? = null
        get() {
            if (field != null) return field
            val crashFullPath = UtilKStrPath.Absolute.Internal.getCacheDir() + "/crashk_native"
            UtilKFile.createFolder(crashFullPath)
            return crashFullPath.also { field = it }
        }

    override fun init(listener: ICrashKListener?) {
        listener?.let { this._crashKListener = it }
        CrashKNativeLib.init(crashPathNative!!)
    }

    override fun getCrashFiles(): Array<File> =
        crashPathNative!!.getFolderFiles()
}