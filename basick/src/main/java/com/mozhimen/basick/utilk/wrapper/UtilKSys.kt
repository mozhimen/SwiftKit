package com.mozhimen.basick.utilk.wrapper

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.utilk.android.os.UtilKBuild
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileWrapper
import com.mozhimen.basick.utilk.java.lang.UtilKRuntime
import com.mozhimen.basick.utilk.java.lang.UtilKRuntimeWrapper
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.File


/**
 * @ClassName UtilKAppRoot
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 18:04
 * @Version 1.0
 */
object UtilKSys : IUtilK {

    //判断手机是否拥有Root权限:
    //有root权限返回true, 否则返回false
    @JvmStatic
    @OPermission_READ_EXTERNAL_STORAGE
    fun isRoot(): Boolean =
            try {
                isSuAvailable() || isBusyboxAvailable() || isWhichAvailable() || hasSuperuserApk() || isSystemBeta()
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.e(TAG)
                false
            }.also { UtilKLogWrapper.d(TAG, "isRoot: $it") }

    //是否存在su命令，并且有执行权限
    @JvmStatic
    @Throws(Exception::class)
    @OPermission_READ_EXTERNAL_STORAGE
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
            if (UtilKFileWrapper.isFileExist(file) && file.canExecute())
                return true.also { UtilKLogWrapper.d(TAG, "isSuAvailable: $it") }
        }
        return false.also { UtilKLogWrapper.d(TAG, "isSuAvailable: $it") }
    }

    //系统是否包含busybox
    @JvmStatic
    @Throws(Exception::class)
    @OPermission_READ_EXTERNAL_STORAGE
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
                return true.also { UtilKLogWrapper.d(TAG, "isBusyboxAvailable: $it") }
        }
        return false.also { UtilKLogWrapper.d(TAG, "isBusyboxAvailable: $it") }
    }

    //系统是否包含which
    @JvmStatic
    @Throws(Exception::class)
    @OPermission_READ_EXTERNAL_STORAGE
    fun isWhichAvailable(): Boolean =
            UtilKRuntimeWrapper.exec_su_system_xbin_which()

    //检测系统内是否安装了Superuser.apk之类的App
    @JvmStatic
    @OPermission_READ_EXTERNAL_STORAGE
    fun hasSuperuserApk(): Boolean =
        UtilKFileWrapper.isFileExist(File("/system/app/Superuser.apk")).also { UtilKLogWrapper.d(TAG, "hasSuperuserApk: $it") }

    //系统是否是非官方发布版
    @JvmStatic
    fun isSystemBeta(): Boolean =
            UtilKBuild.getTags() != null && UtilKBuild.getTags()!!.contains("test-keys").also { UtilKLogWrapper.d(TAG, "isSystemBeta: $it") }
}