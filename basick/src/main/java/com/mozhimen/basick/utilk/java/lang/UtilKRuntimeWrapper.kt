package com.mozhimen.basick.utilk.java.lang

import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.cons.CCmd
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.lintk.optins.ODeviceRoot
import com.mozhimen.basick.lintk.optins.permission.OPermission_FLASHLIGHT
import com.mozhimen.basick.lintk.optins.permission.OPermission_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKByteArrayOutputStream
import com.mozhimen.basick.utilk.java.io.UtilKInputStreamReader
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBytesOutStream
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import com.mozhimen.basick.utilk.kotlin.getStrFolderPath
import java.io.DataOutputStream
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
    fun exec_chmod_777(strFilePath: String) {
        try {
            UtilKRuntime.get().exec("chmod 777 ${strFilePath.getStrFolderPath()}")
            UtilKRuntime.get().exec("chmod 777 $strFilePath")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun exec_getprop(strPackage: String): String? {
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
        return null
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
    fun exec_pm_install_before28(strApkPathName: String): Boolean {
        val command: Array<String> =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strApkPathName)
            else arrayOf("pm", "install", "-r", strApkPathName)

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
    fun exec_pm_install_before28_ofBytesOutStream(strApkPathName: String): Boolean {
        val command =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-r", "-i", UtilKPackage.getPackageName(), "--user", "0", strApkPathName)
            else arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strApkPathName)

        var strInput = ""
        var process: Process? = null
        try {
            process = ProcessBuilder(*command).start()
            val byteArrayOutputStream = UtilKByteArrayOutputStream.get()
            byteArrayOutputStream.write('/'.code)
            strInput = process.inputStream.inputStream2str_use_ofBytesOutStream(byteArrayOutputStream)
            "installSilence result $strInput".d(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return strInput.contains("success", ignoreCase = true)
    }

    //系统是否包含which
    @JvmStatic
    fun exec_su_system_xbin_which(): Boolean {
        var process: Process? = null
        return try {
            process = UtilKRuntime.exec(arrayOf(CPath.SYSTEM_XBIN_WHICH, "su"))
            (UtilKInputStreamReader.readLine_use(process.inputStream, bufferSize = 0) != null).also { UtilKLogWrapper.d(TAG, "isWhichAvailable: $it") }
        } catch (e: Exception) {
            e.printStackTrace()
            false.also { UtilKLogWrapper.d(TAG, "exec_su_system_xbin_which: $it") }
        } finally {
            process?.destroy()
        }
    }

    //开补光灯
    @JvmStatic
    @OPermission_FLASHLIGHT
    @RequiresPermission(CPermission.FLASHLIGHT)
    fun openFlashLight() {
        exec_sh_c(CCmd.FILL_LIGHT_OPEN)
    }

    //关补光灯
    @JvmStatic
    @OPermission_FLASHLIGHT
    @RequiresPermission(CPermission.FLASHLIGHT)
    fun closeFlashLight() {
        exec_sh_c(CCmd.FILL_LIGHT_CLOSE)
    }

    /**
     * 发射命令
     */
    @JvmStatic
    fun exec_sh_c(command: String) {
        var process: Process? = null
        try {
            process = UtilKRuntime.exec(arrayOf("sh", "-c", command))
            val strLineInput = process.inputStream.inputStream2str_use_ofBufferedReader()
            val strLineError = process.errorStream.inputStream2str_use_ofBufferedReader()
            "execShC line $strLineInput error $strLineError".d(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            "executeShellCmd: IOException ${e.message}".e(TAG)
        } finally {
            process?.destroy()
        }
    }

    /**
     * 执行单条shell命令
     */
    @JvmStatic
    fun exec_su_or_sh(command: String, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        exec_su_or_sh(arrayOf(command), isRoot, isNeedResultMsg)

    /**
     * 执行多条shell命令
     */
    @JvmStatic
    fun exec_su_or_sh(commands: List<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        exec_su_or_sh(commands.toTypedArray(), isRoot, isNeedResultMsg)

    /**
     * 执行shell命令核心方法
     */
    @JvmStatic
    fun exec_su_or_sh(commands: Array<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS {
        var result = -1
        if (commands.isEmpty()) return MResultISS(result, "", "")

        var process: Process? = null
        var outputStream: OutputStream? = null
        var dataOutputStream: DataOutputStream? = null
        var strLineInput = ""
        var strLineError = ""

        try {
            process = UtilKRuntime.exec(if (isRoot) "su" else "sh")
            outputStream = process.outputStream
            dataOutputStream = DataOutputStream(outputStream)
            for (command in commands) {
                dataOutputStream.write(command.toByteArray())
                dataOutputStream.writeBytes(UtilKSystemWrapper.getProperty_ofLineSeparator())
                dataOutputStream.flush()
            }
            dataOutputStream.writeBytes("exit${UtilKSystemWrapper.getProperty_ofLineSeparator()}")
            dataOutputStream.flush()
            result = process.waitFor()

            if (isNeedResultMsg) {
                strLineInput = process.inputStream.inputStream2str_use_ofBufferedReader(CCharsets.UTF_8)
                strLineError = process.errorStream.inputStream2str_use_ofBufferedReader(CCharsets.UTF_8)
            }
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, "execSuOrSh: Exception ${e.message}")
            e.printStackTrace()
        } finally {
            dataOutputStream?.flushClose()
            outputStream?.flushClose()
            process?.destroy()
        }
        return MResultISS(result, strLineInput, strLineError)
    }

//    /**
//     * 执行shell命令核心方法
//     */
//    @JvmStatic
//    fun execSuOrSh(commands: Array<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS {
//        var result = -1
//        if (commands.isEmpty()) return MResultISS(result, "", "")
//
//        val inputStringBuilder: StringBuilder = StringBuilder()
//        val errorStringBuilder: StringBuilder = StringBuilder()
//
//        var process: Process? = null
//        var outputStream: OutputStream? = null
//        var dataOutputStream: DataOutputStream? = null
//        var inputStream: InputStream? = null
//        var errorStream: InputStream? = null
//        var inputStreamReader: InputStreamReader? = null
//        var errorStreamReader: InputStreamReader? = null
//        var inputBufferedReader: BufferedReader? = null
//        var errorBufferedReader: BufferedReader? = null
//
//        try {
//            process = UtilKRuntime.exec(if (isRoot) "su" else "sh")
//            outputStream = process.outputStream
//            dataOutputStream = DataOutputStream(outputStream)
//            for (command in commands) {
//                dataOutputStream.write(command.toByteArray())
//                dataOutputStream.writeBytes(UtilKSystemWrapper.getPropertyLineSeparator())
//                dataOutputStream.flush()
//            }
//            dataOutputStream.writeBytes("exit${UtilKSystemWrapper.getPropertyLineSeparator()}")
//            dataOutputStream.flush()
//            result = process.waitFor()
//
//            if (isNeedResultMsg) {
//                inputStream = process.inputStream
//                errorStream = process.errorStream
//                inputStreamReader = InputStreamReader(inputStream, CCharsets.UTF_8)
//                errorStreamReader = InputStreamReader(errorStream, CCharsets.UTF_8)
//                inputBufferedReader = BufferedReader(inputStreamReader)
//                errorBufferedReader = BufferedReader(errorStreamReader)
//
//                var strLine: String?
//                if (inputBufferedReader.readLine().also { strLine = it } != null) {
//                    inputStringBuilder.append(strLine)
//                    while (inputBufferedReader.readLine().also { strLine = it } != null)
//                        inputStringBuilder.append(UtilKSystemWrapper.getPropertyLineSeparator()).append(strLine)
//                }
//                if (errorBufferedReader.readLine().also { strLine = it } != null) {
//                    errorStringBuilder.append(strLine)
//                    while (errorBufferedReader.readLine().also { strLine = it } != null)
//                        errorStringBuilder.append(UtilKSystemWrapper.getPropertyLineSeparator()).append(strLine)
//                }
//            }
//        } catch (e: Exception) {
//            UtilKLogWrapper.e(TAG, "execSuOrSh: Exception ${e.message}")
//            e.printStackTrace()
//        } finally {
//            errorBufferedReader?.close()
//            inputBufferedReader?.close()
//            errorStreamReader?.close()
//            inputStreamReader?.close()
//            errorStream?.close()
//            inputStream?.close()
//            dataOutputStream?.flush()
//            dataOutputStream?.close()
//            outputStream?.flush()
//            outputStream?.close()
//            process?.destroy()
//        }
//        return MResultISS(result, inputStringBuilder.toString(), errorStringBuilder.toString())
//    }

//    /**
//     * 发射命令
//     */
//    @JvmStatic
//    fun exec_sh_c(command: String) {
//        var process: Process? = null
//        var inputStream: InputStream? = null
//        var errorStream: InputStream? = null
//        var inputStreamReader: InputStreamReader? = null
//        var errorStreamReader: InputStreamReader? = null
//        var inputBufferedReader: BufferedReader? = null
//        var errorBufferedReader: BufferedReader? = null
//        var data = ""
//        var strLineInput: String
//        var strLineError: String
//
//        try {
//            process = UtilKRuntime.exec(arrayOf("sh", "-c", command))
//            inputStream = process.inputStream
//            errorStream = process.errorStream
//            inputStreamReader = InputStreamReader(inputStream)
//            errorStreamReader = InputStreamReader(errorStream)
//            inputBufferedReader = BufferedReader(inputStreamReader)
//            errorBufferedReader = BufferedReader(errorStreamReader)
//
//            while (inputBufferedReader.readLine().also { strLineInput = it } != null && !TextUtils.isEmpty(strLineInput)) {
//                data = """
//                $data$strLineInput
//
//                """.trimIndent()
//            }
//            while (errorBufferedReader.readLine().also { strLineError = it } != null && !TextUtils.isEmpty(strLineError)) {
//                data = """
//                $data$strLineError
//
//                """.trimIndent()
//            }
//            "execShC line $strLineInput error $strLineError".d(TAG)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            "executeShellCmd: IOException ${e.message}".e(TAG)
//        } finally {
//            errorBufferedReader?.close()
//            inputBufferedReader?.close()
//            errorStreamReader?.close()
//            inputStreamReader?.close()
//            errorStream?.close()
//            inputStream?.close()
//            process?.destroy()
//        }
//    }
}