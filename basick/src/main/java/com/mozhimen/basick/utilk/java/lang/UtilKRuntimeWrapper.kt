package com.mozhimen.basick.utilk.java.lang

import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.ODeviceRoot
import com.mozhimen.basick.lintk.optins.permission.OPermission_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKByteArrayOutputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBytesOutStream
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import com.mozhimen.basick.utilk.kotlin.getStrFolderPath
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * @ClassName UtilKRuntimeWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKRuntimeWrapper : IUtilK {

    @JvmStatic
    fun exec_chmod_777(path: String) {
        try {
            UtilKRuntime.get().exec("chmod 777 ${path.getStrFolderPath()}")
            UtilKRuntime.get().exec("chmod 777 $path")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun exec_getprop(strPackage: String): String {
        var process: Process? = null
        try {
            process = UtilKRuntime.exec("getprop $strPackage")
            return process.inputStream.inputStream2str_use_ofBufferedReader(bufferSize = 1024)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(UtilKRuntime.TAG, "getProp Unable to read prop strPackage $strPackage msg ${e.message}")
        } finally {
            process?.destroy()
        }
        return ""
    }

    @JvmStatic
    @Throws(Exception::class)
    @ODeviceRoot
    @OPermission_INSTALL_PACKAGES
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun exec_su_pm_install(strApkPathName: String): Boolean {
        require(strApkPathName.isNotEmpty()) { "$TAG please check apk file path" }

        var process: Process? = null
        var outputStream: OutputStream? = null
        try {
            process = UtilKRuntime.exec("su")

            outputStream = process.outputStream
            outputStream.write("pm install -r $strApkPathName\n".toByteArray())
            outputStream.flush()
            outputStream.write("exit\n".toByteArray())
            outputStream.flush()

            process.waitFor()

            val strError = process.errorStream.inputStream2str_use_ofBufferedReader()
            "execSuInstall msg is $strError".d(UtilKRuntime.TAG)
            return !strError.contains("failure", ignoreCase = true)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        } finally {
            outputStream?.flushClose()
            process?.destroy()
        }
        return false
    }

    /**
     * 静默安装适配 SDK28 之前的安装方法
     */
    @JvmStatic
    @OPermission_INSTALL_PACKAGES
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun exec_pm_install_before28(strPathNameApk: String): Boolean {
        val command: Array<String> =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strPathNameApk)
            else arrayOf("pm", "install", "-r", strPathNameApk)

        var strInput = ""
        var process: Process? = null
        try {
            process = ProcessBuilder(*command).start()
            strInput = process.inputStream.inputStream2str_use_ofBufferedReader()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return strInput.contains("success", ignoreCase = true)        //如果含有success单词则认为安装成功
    }

    /**
     * 静默安装
     */
    @JvmStatic
    @OPermission_INSTALL_PACKAGES
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun execInstallBefore282(strApkPathName: String): Boolean {
        val command =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-r", "-i", UtilKPackage.getPackageName(), "--user", "0", strApkPathName)
            else arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strApkPathName)

        var strInput = ""
        var process: Process? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            process = ProcessBuilder(*command).start()
            byteArrayOutputStream = UtilKByteArrayOutputStream.get()
            byteArrayOutputStream.write('/'.code)
            strInput = process.inputStream.inputStream2str_use_ofBytesOutStream(byteArrayOutputStream)
            "installSilence result $strInput".d(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.flushClose()
            process?.destroy()
        }
        return strInput.contains("success", ignoreCase = true)
    }
}