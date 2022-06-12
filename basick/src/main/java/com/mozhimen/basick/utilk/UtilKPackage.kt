package com.mozhimen.basick.utilk

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/11 17:21
 * @Version 1.0
 */
object UtilKPackage {
    private val _context = UtilKGlobal.instance.getApp()!!

    //程序包名
    fun getPkgVersionName(): String {
        var pi: PackageInfo? = null
        try {
            pi = _context.packageManager.getPackageInfo(_context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pi!!.versionName
    }

    fun silenceInstall(pathApk: String): Boolean {
        var process: Process? = null
        var resSuccess: BufferedReader? = null
        var resError: BufferedReader? = null

        val msgSuccess = StringBuilder()
        val msgError = StringBuilder()
        val cmd: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayOf("pm", "install", "-i", _context.packageName, "-r", pathApk)
        } else {
            arrayOf("pm", "install", "-r", pathApk)
        }
        try {
            process = ProcessBuilder(*cmd).start()
            resSuccess = BufferedReader(InputStreamReader(process.inputStream))
            resError = BufferedReader(InputStreamReader(process.errorStream))
            var s: String?
            while (resSuccess.readLine().also { s = it } != null) {
                msgSuccess.append(s)
            }
            while (resError.readLine().also { s = it } != null) {
                msgError.append(s)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                resSuccess?.close()
                resError?.close()
            } catch (e: Exception) {
            }
            process?.destroy()
        }
        //如果含有success单词则认为安装成功
        return msgSuccess.toString().equals("success", ignoreCase = true)
    }
}