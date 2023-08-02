package com.mozhimen.basick.utilk.java.lang

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.mos.MResultISS
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/**
 * @ClassName UtilKShell
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 17:52
 * @Version 1.0
 */
object UtilKRuntime : BaseUtilK() {
    private val SHELL_SEP = System.getProperty(CPackage.LINE_SEPARATOR)

    @JvmStatic
    fun getProp(packageStr: String): String? {
        val line: String
        var inputBuffer: BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec("getprop $packageStr")
            inputBuffer = BufferedReader(InputStreamReader(process.inputStream), 1024)
            line = inputBuffer.readLine()
            inputBuffer.close()
        } catch (e: IOException) {
            Log.e(TAG, "getProp IOException Unable to read prop $packageStr $e")
            return null
        } finally {
            inputBuffer?.let {
                try {
                    inputBuffer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                }
            }
        }
        return line
    }

    ///////////////////////////////////////////////////////////

    /**
     * 执行单条shell命令
     * @param cmd String
     * @param isRooted Boolean
     * @return UtilKShellCmd?
     */
    @JvmStatic
    fun execCmd(cmd: String, isRooted: Boolean): MResultISS {
        return execCmds(arrayOf(cmd), isRooted, true)
    }

    /**
     * 执行多条shell命令
     * @param cmds List<String>
     * @param isRooted Boolean
     * @return UtilKShellCmd?
     */
    @JvmStatic
    fun execCmds(cmds: List<String>, isRooted: Boolean): MResultISS {
        return execCmds(cmds.toTypedArray(), isRooted, true)
    }

    /**
     * 执行多条shell命令
     * @param cmds Array<String>?
     * @param isRooted Boolean
     * @return UtilKShellCmd?
     */
    @JvmStatic
    fun execCmds(cmds: Array<String>, isRooted: Boolean): MResultISS {
        return execCmds(cmds, isRooted, true)
    }

    /**
     * 发射命令
     * @param cmd String
     */
    @JvmStatic
    fun execCmd(cmd: String) {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(arrayOf("sh", "-c", cmd))
        } catch (var8: IOException) {
            var8.printStackTrace()
        }
        var data = ""
        val inputStream = BufferedReader(InputStreamReader(process!!.inputStream))
        val errorStream = BufferedReader(InputStreamReader(process.errorStream))
        var line: String
        var error: String
        try {
            while (inputStream.readLine().also { line = it } != null && !TextUtils.isEmpty(line)) {
                data = """
                $data$line
                
                """.trimIndent()
            }
            while (errorStream.readLine().also { error = it } != null && !TextUtils.isEmpty(error)) {
                data = """
                $data$error
                
                """.trimIndent()
            }
        } catch (e: IOException) {
            Log.e(TAG, "executeShellCmd: IOException ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 执行shell命令核心方法
     * @param cmds
     * @param isRooted
     * @param isNeedResultMsg
     * @return
     */
    @JvmStatic
    fun execCmds(cmds: Array<String>, isRooted: Boolean, isNeedResultMsg: Boolean): MResultISS {
        var result = -1
        if (cmds.isEmpty()) {
            return MResultISS(result, "", "")
        }
        var process: Process? = null
        var successResult: BufferedReader? = null
        var errorResult: BufferedReader? = null
        var successMsg: StringBuilder? = null
        var errorMsg: StringBuilder? = null
        var outputStream: DataOutputStream? = null
        try {
            process = Runtime.getRuntime().exec(if (isRooted) "su" else "sh")
            outputStream = DataOutputStream(process.outputStream)
            for (cmd in cmds) {
                outputStream.write(cmd.toByteArray())
                outputStream.writeBytes(SHELL_SEP)
                outputStream.flush()
            }
            outputStream.writeBytes("exit$SHELL_SEP")
            outputStream.flush()
            result = process.waitFor()
            if (isNeedResultMsg) {
                successMsg = StringBuilder()
                errorMsg = StringBuilder()
                successResult = BufferedReader(
                    InputStreamReader(process.inputStream, "UTF-8")
                )
                errorResult = BufferedReader(
                    InputStreamReader(process.errorStream, "UTF-8")
                )
                var line: String?
                if (successResult.readLine().also { line = it } != null) {
                    successMsg.append(line)
                    while (successResult.readLine().also { line = it } != null) {
                        successMsg.append(SHELL_SEP).append(line)
                    }
                }
                if (errorResult.readLine().also { line = it } != null) {
                    errorMsg.append(line)
                    while (errorResult.readLine().also { line = it } != null) {
                        errorMsg.append(SHELL_SEP).append(line)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "execCmd: Exception ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
                successResult?.close()
                errorResult?.close()
                process?.destroy()
            } catch (e: IOException) {
                Log.e(TAG, "execCmd: IOException ${e.message}")
                e.printStackTrace()
            }
        }
        return MResultISS(result, successMsg?.toString() ?: "", errorMsg?.toString() ?: "")
    }
}