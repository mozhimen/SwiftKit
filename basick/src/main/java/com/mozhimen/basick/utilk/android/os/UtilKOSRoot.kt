package com.mozhimen.basick.utilk.android.os

import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.lang.UtilKRuntime
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.File


/**
 * @ClassName UtilKAppRoot
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 18:04
 * @Version 1.0
 */
@AManifestKRequire(CPermission.READ_EXTERNAL_STORAGE)
object UtilKOSRoot : IUtilK {

    /**
     * 判断手机是否拥有Root权限:
     * 有root权限返回true, 否则返回false
     */
    @JvmStatic
    fun isRoot(): Boolean =
            try {
                isSuAvailable() || isBusyboxAvailable() || isWhichAvailable() || hasSuperuserApk() || isSystemBeta()
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
                false
            }.also { Log.d(TAG, "isRoot: $it") }

    /**
     * 是否存在su命令，并且有执行权限
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isSuAvailable(): Boolean {
        var file: File
        val strFilePaths = arrayOf(
                CPath.SYSTEM_BIN_SU, CPath.SYSTEM_XBIN_SU, CPath.SYSTEM_SBIN_SU,
                CPath.SBIN_SU, CPath.VENDOR_BIN_SU, CPath.SU_BIN_SU,
                CPath.DATA_LOCAL_XBIN_SU, CPath.DATA_LOCAL_BIN_SU, CPath.SYSTEM_SD_XBIN_SU,
                CPath.SYSTEM_BIN_FAILSAFE_SU, CPath.DATA_LOCAL_SU
        )
        for (path in strFilePaths) {
            file = path.strFilePath2file()
            if (UtilKFile.isFileExist(file) && file.canExecute())
                return true.also { Log.d(TAG, "isSuAvailable: $it") }
        }
        return false.also { Log.d(TAG, "isSuAvailable: $it") }
    }

    /**
     * 系统是否包含busybox
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isBusyboxAvailable(): Boolean {
        var file: File
        val strFilePaths = arrayOf(
                CPath.SYSTEM_BIN_BUSYBOX, CPath.SYSTEM_XBIN_BUSYBOX, CPath.SYSTEM_SBIN_BUSYBOX,
                CPath.SBIN_BUSYBOX, CPath.VENDOR_BIN_BUSYBOX, CPath.SU_BIN_BUSYBOX,
                CPath.DATA_LOCAL_XBIN_BUSYBOX, CPath.DATA_LOCAL_BIN_BUSYBOX, CPath.SYSTEM_SD_XBIN_BUSYBOX,
                CPath.SYSTEM_BIN_FAILSAFE_BUSYBOX, CPath.DATA_LOCAL_BUSYBOX
        )
        for (path in strFilePaths) {
            file = path.strFilePath2file()
            if (file.exists() && file.canExecute())
                return true.also { Log.d(TAG, "isBusyboxAvailable: $it") }
        }
        return false.also { Log.d(TAG, "isBusyboxAvailable: $it") }
    }

    /**
     * 系统是否包含which
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isWhichAvailable(): Boolean =
            UtilKRuntime.execSystemXbinWhich()

    /**
     * 检测系统内是否安装了Superuser.apk之类的App
     */
    @JvmStatic
    fun hasSuperuserApk(): Boolean =
            UtilKFile.isFileExist(File("/system/app/Superuser.apk")).also { Log.d(TAG, "hasSuperuserApk: $it") }

    /**
     * 系统是否是非官方发布版
     */
    @JvmStatic
    fun isSystemBeta(): Boolean =
            UtilKBuild.getTags() != null && UtilKBuild.getTags()!!.contains("test-keys").also { Log.d(TAG, "isSystemBeta: $it") }
}