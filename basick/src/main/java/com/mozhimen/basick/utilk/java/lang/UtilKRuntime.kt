package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKInputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import kotlin.jvm.Throws

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

    @JvmStatic
    fun execGetProp(strPackage: String): String? {
        var process: Process? = null
        var inputStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null

        try {
            process = exec("getprop $strPackage")
            inputStream = process.inputStream
            inputStreamReader = InputStreamReader(inputStream)
            inputBufferedReader = BufferedReader(inputStreamReader, 1024)
            return inputBufferedReader.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getProp Unable to read prop strPackage $strPackage msg ${e.message}")
        } finally {
            inputBufferedReader?.close()
            inputStreamReader?.close()
            inputStream?.close()
            process?.destroy()
        }
        return ""
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 静默安装适配 SDK28 之前的安装方法
     * @param apkPathWithName String
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun execInstallBefore28(apkPathWithName: String): Boolean {
        val command: Array<String> =
                if (UtilKBuildVersion.isAfterV_24_7_N())
                    arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", apkPathWithName)
                else arrayOf("pm", "install", "-r", apkPathWithName)

        val inputStringBuilder = StringBuilder()
        val errorStringBuilder = StringBuilder()

        var process: Process? = null
        var inputStream: InputStream? = null
        var errorStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var errorStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null
        var errorBufferedReader: BufferedReader? = null

        try {
            process = ProcessBuilder(*command).start()
            inputStream = process.inputStream
            errorStream = process.errorStream
            inputStreamReader = InputStreamReader(inputStream)
            errorStreamReader = InputStreamReader(errorStream)
            inputBufferedReader = BufferedReader(inputStreamReader)
            errorBufferedReader = BufferedReader(errorStreamReader)

            var msg: String?
            while (inputBufferedReader.readLine().also { msg = it } != null)
                inputStringBuilder.append(msg)

            while (errorBufferedReader.readLine().also { msg = it } != null)
                errorStringBuilder.append(msg)
        } catch (e: Exception) {
            e.printStackTrace()
            "execInstallBefore28: Exception ${e.message}".et(TAG)
        } finally {
            errorBufferedReader?.close()
            inputBufferedReader?.close()
            errorStreamReader?.close()
            inputStreamReader?.close()
            errorStream?.close()
            inputStream?.close()
            process?.destroy()
        }
        return inputStringBuilder.toString().contains("success", ignoreCase = true)        //如果含有success单词则认为安装成功
    }

    /**
     * 静默安装
     * @param apkPathWithName String 绝对路径
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun execInstallBefore282(apkPathWithName: String): Boolean {
        val command =
                if (UtilKBuildVersion.isAfterV_24_7_N())
                    arrayOf("pm", "install", "-r", "-i", UtilKPackage.getPackageName(), "--user", "0", apkPathWithName)
                else arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", apkPathWithName)

        var strInput = ""
        var process: Process? = null
        var inputStream: InputStream? = null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            process = ProcessBuilder(*command).start()
            inputStream = process.inputStream
            byteArrayOutputStream = ByteArrayOutputStream()
            byteArrayOutputStream.write('/'.code)
            strInput = UtilKInputStream.inputStream2str2(inputStream, byteArrayOutputStream)
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
     * @throws Exception
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
        var strInput: String
        var strError: String
        try {
            process = exec(arrayOf("sh", "-c", command))
            inputStream = process.inputStream
            errorStream = process.errorStream
            inputStreamReader = InputStreamReader(inputStream)
            errorStreamReader = InputStreamReader(errorStream)
            inputBufferedReader = BufferedReader(inputStreamReader)
            errorBufferedReader = BufferedReader(errorStreamReader)

            while (inputBufferedReader.readLine().also { strInput = it } != null && !TextUtils.isEmpty(strInput)) {
                data = """
                $data$strInput
                
                """.trimIndent()
            }
            while (errorBufferedReader.readLine().also { strError = it } != null && !TextUtils.isEmpty(strError)) {
                data = """
                $data$strError
                
                """.trimIndent()
            }
            "execShC line $strInput error $strError".dt(TAG)
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
                dataOutputStream.writeBytes(UtilKSystem.getPropertyLineSeparator())
                dataOutputStream.flush()
            }
            dataOutputStream.writeBytes("exit${UtilKSystem.getPropertyLineSeparator()}")
            dataOutputStream.flush()
            result = process.waitFor()

            if (isNeedResultMsg) {
                inputStream = process.inputStream
                errorStream = process.errorStream
                inputStreamReader = InputStreamReader(inputStream, CCharsets.UTF_8)
                errorStreamReader = InputStreamReader(errorStream, CCharsets.UTF_8)
                inputBufferedReader = BufferedReader(inputStreamReader)
                errorBufferedReader = BufferedReader(errorStreamReader)

                var line: String?
                if (inputBufferedReader.readLine().also { line = it } != null) {
                    inputStringBuilder.append(line)
                    while (inputBufferedReader.readLine().also { line = it } != null)
                        inputStringBuilder.append(UtilKSystem.getPropertyLineSeparator()).append(line)
                }
                if (errorBufferedReader.readLine().also { line = it } != null) {
                    errorStringBuilder.append(line)
                    while (errorBufferedReader.readLine().also { line = it } != null)
                        errorStringBuilder.append(UtilKSystem.getPropertyLineSeparator()).append(line)
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