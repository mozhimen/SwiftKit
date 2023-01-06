package com.mozhimen.basick.utilk.app

import android.Manifest
import android.os.Build
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKFile
import java.io.File


/**
 * @ClassName UtilKAppRoot
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 18:04
 * @Version 1.0
 */
object UtilKAppRoot {
    /**
     * 判断手机是否拥有Root权限:
     * 有root权限返回true, 否则返回false
     * @return Boolean
     */
    @JvmStatic
    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun isRoot(): Boolean {
        var isRoot = false
        try {
            isRoot = UtilKFile.isFileExist("/system/bin/su") || UtilKFile.isFileExist("/system/xbin/su")
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
    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    private fun isSuAvailable(): Boolean {
        var file: File
        val paths = arrayOf("/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/")
        try {
            paths.forEach {
                file = File("${it}su")
                if (UtilKFile.isFileExist(file) && file.canExecute()) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return false
    }

    @JvmStatic
    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun isBusyboxAvailable(): Boolean {
        var file: File
        val paths = arrayOf("/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/")
        try {
            for (path in paths) {
                file = File(path + "${path}busybox")
                if (file.exists() && file.canExecute()) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun hasSuperuserApk(): Boolean {
        try {
            val file = File("/system/app/Superuser.apk")
            if (UtilKFile.isFileExist(file)) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun isSystemBeta(): Boolean {
        val buildTags = Build.TAGS
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true
        }
        return false
    }


}