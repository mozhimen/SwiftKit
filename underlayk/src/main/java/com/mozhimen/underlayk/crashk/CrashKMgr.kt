package com.mozhimen.underlayk.crashk

import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.underlayk.crashk.commons.ICrashKListener
import com.mozhimen.underlayk.crashk.commons.ICrashKMgr
import java.io.File

/**
 * @ClassName CrashKMgr
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/28 14:32
 * @Version 1.0
 */
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE)
class CrashKMgr : ICrashKMgr {

    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    //////////////////////////////////////////////////////////////

    private val _crashKJava by lazy { CrashKJava() }
    private val _crashKNative by lazy { CrashKNative() }

    fun init(
        crashKJavaListener: ICrashKListener? = null,
        crashKNativeListener: ICrashKListener? = null,
        isRestart: Boolean = false
    ) {
        _crashKJava.setEnableRestart(isRestart).init(crashKJavaListener)
        _crashKNative.init(crashKNativeListener)
    }

    override fun getJavaCrashFiles(): Array<File> =
        _crashKJava.getCrashFiles()

    override fun getNativeCrashFiles(): Array<File> =
        _crashKNative.getCrashFiles()

    override fun getCrashFiles(): Array<File> =
        getJavaCrashFiles() + getNativeCrashFiles()

    //////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = CrashKMgr()
    }
}