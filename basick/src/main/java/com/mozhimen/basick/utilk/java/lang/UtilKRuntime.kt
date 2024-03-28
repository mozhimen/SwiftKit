package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.BufferedReader
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
    fun exec(cmdarray: Array<String>): Process =
        get().exec(cmdarray)

    @JvmStatic
    @Throws(IOException::class)
    fun exec(command: String): Process =
        get().exec(command)

    ///////////////////////////////////////////////////////////////////////////

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
            (inputBufferedReader.readLine() != null).also { UtilKLogWrapper.d(TAG, "isWhichAvailable: $it") }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
            false.also { UtilKLogWrapper.d(TAG, "isWhichAvailable: $it") }
        } finally {
            inputBufferedReader?.close()
            inputStreamReader?.close()
            inputStream?.close()
            process?.destroy()
        }
    }

    //////////////////////////////////////////////////////////////////////////////////

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
            "execShC line $strLineInput error $strLineError".d(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            "executeShellCmd: IOException ${e.message}".e(TAG)
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
     */
    @JvmStatic
    fun execSuOrSh(command: String, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        execSuOrSh(arrayOf(command), isRoot, isNeedResultMsg)

    /**
     * 执行多条shell命令
     */
    @JvmStatic
    fun execSuOrSh(commands: List<String>, isRoot: Boolean, isNeedResultMsg: Boolean = true): MResultISS =
        execSuOrSh(commands.toTypedArray(), isRoot, isNeedResultMsg)

    /**
     * 执行shell命令核心方法
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
            UtilKLogWrapper.e(TAG, "execSuOrSh: Exception ${e.message}")
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