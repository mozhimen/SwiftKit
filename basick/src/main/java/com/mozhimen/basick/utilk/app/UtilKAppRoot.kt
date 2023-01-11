package com.mozhimen.basick.utilk.app

import android.os.Build
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basick.utilk.file.UtilKFile
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


/**
 * @ClassName UtilKAppRoot
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 18:04
 * @Version 1.0
 */
@APermissionKRequire(CPermission.READ_EXTERNAL_STORAGE)
object UtilKAppRoot {
    /**
     * 判断手机是否拥有Root权限:
     * 有root权限返回true, 否则返回false
     * @return Boolean
     */
    @JvmStatic
    fun isRoot(): Boolean {
        var isRoot = false
        try {
            isRoot = isSuAvailable() || isBusyboxAvailable() || isWhichAvailable() || hasSuperuserApk() || isSystemBeta()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isRoot
    }

    /**
     * 是否存在su命令，并且有执行权限
     * @return Boolean
     */
    @JvmStatic
    @Throws(Exception::class)
    private fun isSuAvailable(): Boolean {
        var file: File
        val paths = arrayOf(
            "/system/bin/su", "/system/xbin/su", "/system/sbin/su",
            "/sbin/su", "/vendor/bin/su", "/su/bin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
            "/system/bin/failsafe/su", "/data/local/su"
        )
        for (path in paths) {
            file = File(path)
            if (UtilKFile.isFileExist(file) && file.canExecute()) {
                return true
            }
        }
        return false
    }

    /**
     * 系统是否包含busybox
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isBusyboxAvailable(): Boolean {
        var file: File
        val paths = arrayOf(
            "/system/bin/busybox", "/system/xbin/busybox", "/system/sbin/busybox",
            "/sbin/busybox", "/vendor/bin/busybox", "/su/bin/busybox",
            "/data/local/xbin/busybox", "/data/local/bin/busybox", "/system/sd/xbin/busybox",
            "/system/bin/failsafe/busybox", "/data/local/busybox"
        )
        for (path in paths) {
            file = File(path)
            if (file.exists() && file.canExecute()) {
                return true
            }
        }
        return false
    }

    /**
     * 系统是否包含which
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun isWhichAvailable(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            bufferedReader.readLine() != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            process?.destroy()
        }
    }

    /**
     * 检测系统内是否安装了Superuser.apk之类的App
     * @return Boolean
     */
    @JvmStatic
    fun hasSuperuserApk(): Boolean =
        UtilKFile.isFileExist(File("/system/app/Superuser.apk"))

    /**
     * 系统是否是非官方发布版
     * @return Boolean
     */
    @JvmStatic
    fun isSystemBeta(): Boolean =
        Build.TAGS != null && Build.TAGS.contains("test-keys")
}