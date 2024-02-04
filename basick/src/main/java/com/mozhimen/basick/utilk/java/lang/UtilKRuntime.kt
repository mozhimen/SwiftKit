package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.lintk.optins.ODeviceRoot
import com.mozhimen.basick.lintk.optins.permission.OPermission_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2strOfReadMultiLines
import com.mozhimen.basick.utilk.java.io.inputStream2strOfBytesOutStream
import com.mozhimen.basick.utilk.java.io.inputStream2strOfReadSingleLine
import com.mozhimen.basick.utilk.kotlin.getStrFolderPath
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream

/**
 * @ClassName UtilKShell
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 17:52
 * @Version 1.0
 */
object UtilKRuntime : BaseUtilK() {

    @JvmStatic
    fun get(): Runtime =
        Runtime.getRuntime()

    @JvmStatic
    @Throws(IOException::class)
    fun exec(cmdArray: Array<String>): Process =
        get().exec(cmdArray, null, null)

    @JvmStatic
    @Throws(IOException::class)
    fun exec(command: String): Process =
        get().exec(command)

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun chmod777(path: String) {
        try {
            get().exec("chmod 777 ${path.getStrFolderPath()}")
            get().exec("chmod 777 $path")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun execGetProp(strPackage: String): String? {
        var process: Process? = null
        var inputStream: InputStream? = null
        try {
            process = exec("getprop $strPackage")
            inputStream = process.inputStream
            return inputStream.inputStream2strOfReadSingleLine(readSize = 1024)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getProp Unable to read prop strPackage $strPackage msg ${e.message}")
        } finally {
            inputStream?.close()
            process?.destroy()
        }
        return ""
    }

    @JvmStatic
    @Throws(Exception::class)
    @ODeviceRoot
    @OPermission_INSTALL_PACKAGES
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun execSuInstall(strPathNameApk: String): Boolean {
        require(strPathNameApk.isNotEmpty()) { "$TAG please check apk file path" }

        var process: Process? = null
        var outputStream: OutputStream? = null
        var errorStream: InputStream? = null
        try {
            process = exec("su")

            outputStream = process.outputStream
            outputStream.write("pm install -r $strPathNameApk\n".toByteArray())
            outputStream.flush()
            outputStream.write("exit\n".toByteArray())
            outputStream.flush()

            process.waitFor()

            errorStream = process.errorStream
            val strError =errorStream.inputStream2strOfReadMultiLines()
            "execSuInstall msg is $strError".dt(TAG)
            return !strError.contains("failure", ignoreCase = true)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            errorStream?.close()
            outputStream?.flushClose()
            process?.destroy()
        }
        return false
    }

    /**
     * 静默安装适配 SDK28 之前的安装方法
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @OPermission_INSTALL_PACKAGES
    fun execInstallBefore28(strPathNameApk: String): Boolean {
        val command: Array<String> =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strPathNameApk)
            else arrayOf("pm", "install", "-r", strPathNameApk)

        var strInput = ""
        var process: Process? = null
        var inputStream: InputStream? = null

        try {
            process = ProcessBuilder(*command).start()
            inputStream = process.inputStream
            strInput = inputStream.inputStream2strOfReadMultiLines()
        } catch (e: Exception) {
            e.printStackTrace()
            "execInstallBefore28: Exception ${e.message}".et(TAG)
        } finally {
            inputStream?.close()
            process?.destroy()
        }
        return strInput.contains("success", ignoreCase = true)        //如果含有success单词则认为安装成功
    }

    /**
     * 静默安装
     * @param strPathNameApk String 绝对路径
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @OPermission_INSTALL_PACKAGES
    fun execInstallBefore282(strPathNameApk: String): Boolean {
        val command =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-r", "-i", UtilKPackage.getPackageName(), "--user", "0", strPathNameApk)
            else arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", strPathNameApk)

        var strInput = ""
        var process: Process? = null
        var inputStream: InputStream? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            process = ProcessBuilder(*command).start()
            inputStream = process.inputStream
            byteArrayOutputStream = ByteArrayOutputStream()
            byteArrayOutputStream.write('/'.code)
            strInput = inputStream.inputStream2strOfBytesOutStream(byteArrayOutputStream)
            "installSilence result $strInput".dt(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream?.flushClose()
            inputStream?.close()
            process?.destroy()
        }
        return strInput.contains("success", ignoreCase = true)
    }

    /**
     * 系统是否包含which
     * @return Boolean
     */
    @JvmStatic
    fun execSystemXbinWhich(): Boolean {
        var process: Process? = null
        var inputStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null

        return try {
            process = exec(arrayOf(CPath.SYSTEM_XBIN_WHICH, "su"))
            inputStream = process.inputStream
            inputStreamReader = InputStreamReader(inputStream)
            inputBufferedReader = BufferedReader(inputStreamReader)
            (inputBufferedReader.readLine() != null).also { Log.d(TAG, "isWhichAvailable: $it") }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false.also { Log.d(TAG, "isWhichAvailable: $it") }
        } finally {
            inputBufferedReader?.close()
            inputStreamReader?.close()
            inputStream?.close()
            process?.destroy()
        }
    }

    /**
     * 发射命令
     * @param command String
     */
    @JvmStatic
    fun execShC(command: String) {
        var process: Process? = null
        var inputStream: InputStream? = null
        var errorStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var errorStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null
        var errorBufferedReader: BufferedReader? = null
        var data = ""
        var strLineInput: String
        var strLineError: String

        try {
            process = exec(arrayOf("sh", "-c", command))
            inputStream = process.inputStream
            errorStream = process.errorStream
            inputStreamReader = InputStreamReader(inputStream)
            errorStreamReader = InputStreamReader(errorStream)
            inputBufferedReader = BufferedReader(inputStreamReader)
            errorBufferedReader = BufferedReader(errorStreamReader)

            while (inputBufferedReader.readLine().also { strLineInput = it } != null && !TextUtils.isEmpty(strLineInput)) {
                data = """
                $data$strLineInput
                
                """.trimIndent()
            }
            while (errorBufferedReader.readLine().also { strLineError = it } != null && !TextUtils.isEmpty(strLineError)) {
                data = """
                $data$strLineError
                
                """.trimIndent()
            }
            "execShC line $strLineInput error $strLineError".dt(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            "executeShellCmd: IOException ${e.message}".et(TAG)
        } finally {
            errorBufferedReader?.close()
            inputBufferedReader?.close()
            errorStreamReader?.close()
            inputStreamReader?.close()
            errorStream?.close()
            inputStream?.close()
            process?.destroy()
        }
    }

    /**
     * 执行单条shell命令
     * @param command String
     * @param isRoot Boolean
     * @return UtilKShellCmd?
     */
    @JvmStatic
    fun execSuOrSh(command: String, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        execSuOrSh(arrayOf(command), isRoot, isNeedResultMsg)

    /**
     * 执行多条shell命令
     * @param commands List<String>
     * @param isRoot Boolean
     * @return UtilKShellCmd?
     */
    @JvmStatic
    fun execSuOrSh(commands: List<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        execSuOrSh(commands.toTypedArray(), isRoot, isNeedResultMsg)

    /**
     * 执行shell命令核心方法
     * @param commands
     * @param isRoot
     * @param isNeedResultMsg
     * @return
     */
    @JvmStatic
    fun execSuOrSh(commands: Array<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS {
        var result = -1
        if (commands.isEmpty()) return MResultISS(result, "", "")

        val inputStringBuilder: StringBuilder = StringBuilder()
        val errorStringBuilder: StringBuilder = StringBuilder()

        var process: Process? = null
        var outputStream: OutputStream? = null
        var dataOutputStream: DataOutputStream? = null
        var inputStream: InputStream? = null
        var errorStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var errorStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null
        var errorBufferedReader: BufferedReader? = null

        try {
            process = exec(if (isRoot) "su" else "sh")
            outputStream = process.outputStream
            dataOutputStream = DataOutputStream(outputStream)
            for (command in commands) {
                dataOutputStream.write(command.toByteArray())
                dataOutputStream.writeBytes(UtilKSystemWrapper.getPropertyLineSeparator())
                dataOutputStream.flush()
            }
            dataOutputStream.writeBytes("exit${UtilKSystemWrapper.getPropertyLineSeparator()}")
            dataOutputStream.flush()
            result = process.waitFor()

            if (isNeedResultMsg) {
                inputStream = process.inputStream
                errorStream = process.errorStream
                inputStreamReader = InputStreamReader(inputStream, CCharsets.UTF_8)
                errorStreamReader = InputStreamReader(errorStream, CCharsets.UTF_8)
                inputBufferedReader = BufferedReader(inputStreamReader)
                errorBufferedReader = BufferedReader(errorStreamReader)

                var strLine: String?
                if (inputBufferedReader.readLine().also { strLine = it } != null) {
                    inputStringBuilder.append(strLine)
                    while (inputBufferedReader.readLine().also { strLine = it } != null)
                        inputStringBuilder.append(UtilKSystemWrapper.getPropertyLineSeparator()).append(strLine)
                }
                if (errorBufferedReader.readLine().also { strLine = it } != null) {
                    errorStringBuilder.append(strLine)
                    while (errorBufferedReader.readLine().also { strLine = it } != null)
                        errorStringBuilder.append(UtilKSystemWrapper.getPropertyLineSeparator()).append(strLine)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "execSuOrSh: Exception ${e.message}")
            e.printStackTrace()
        } finally {
            errorBufferedReader?.close()
            inputBufferedReader?.close()
            errorStreamReader?.close()
            inputStreamReader?.close()
            errorStream?.close()
            inputStream?.close()
            dataOutputStream?.flush()
            dataOutputStream?.close()
            outputStream?.flush()
            outputStream?.close()
            process?.destroy()
        }
        return MResultISS(result, inputStringBuilder.toString(), errorStringBuilder.toString())
    }
}