package com.mozhimen.basick.utilk

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.SessionParams
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.mozhimen.basick.prefabk.receiver.PrefabKReceiverInstall

import java.io.*
import java.nio.charset.Charset

/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/11 17:21
 * @Version 1.0
 */
object UtilKPackage {
    private const val TAG = "UtilKPackage>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getPkgVersionName(): String {
        return try {
            val pi = _context.packageManager.getPackageInfo(_context.packageName, 0)
            pi?.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "getPkgVersionName: NameNotFoundException ${e.message}")
            e.printStackTrace()
            ""
        }
    }

    /**
     * 获取程序版本号
     * @return Int
     */
    @JvmStatic
    fun getPkgVersionCode(): Int {
        return try {
            val pi = _context.packageManager.getPackageInfo(_context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                pi.longVersionCode.toInt()
            } else {
                pi.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "getPkgVersionCode: NameNotFoundException ${e.message}")
            e.printStackTrace()
            -1
        }
    }

    /**
     * 静默安装
     * @param apkPath String 绝对路径
     * @param pkgName String
     * @return Boolean
     */
    @JvmStatic
    fun installSilence(apkPath: String, pkgName: String): Boolean {
        val cmd = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayOf("pm", "install", "-r", "-i", pkgName, "--user", "0", apkPath)
        } else {
            arrayOf("pm", "install", "-i", pkgName, "-r", apkPath)
        }
        var result = "EMPTY"
        val processBuilder = ProcessBuilder(*cmd)
        var process: Process? = null
        var inputStream: InputStream? = null
        var outputStream: ByteArrayOutputStream? = null
        try {
            outputStream = ByteArrayOutputStream()
            var read: Int
            process = processBuilder.start()
            outputStream.write('/'.code)
            inputStream = process.inputStream
            while (inputStream.read().also { read = it } != -1) {
                outputStream.write(read)
            }
            val data = outputStream.toByteArray()
            result = String(data, Charset.forName("UTF-8"))
            Log.d(TAG, "installSilence result $result")
        } catch (e: IOException) {
            Log.e(TAG, "installSilence IOException ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                Log.e(TAG, "installSilence: IOException ${e.message}")
                e.printStackTrace()
            }
            process?.destroy()
        }
        return result.contains("Success")
    }

    /**
     * 静默安装
     * @param pathApk String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    fun installSilence(pathApk: String, receiver: Class<PrefabKReceiverInstall>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            installSilenceAfter28(pathApk, receiver)
        } else {
            installSilenceBefore28(pathApk)
        }
    }

    /**
     * 静默安装适配 SDK28 之前的安装方法
     * @param pathApk String
     * @return Boolean
     */
    @JvmStatic
    fun installSilenceBefore28(pathApk: String): Boolean {
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
            Log.e(TAG, "installSilence: Exception ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                resSuccess?.close()
                resError?.close()
            } catch (e: Exception) {
                Log.e(TAG, "installSilence: Exception ${e.message}")
                e.printStackTrace()
            }
            process?.destroy()
        }
        //如果含有success单词则认为安装成功
        return msgSuccess.toString().contains("success", ignoreCase = true)
    }

    /**
     * 静默安装 SDK28 之后的
     * @param pathApk String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    fun installSilenceAfter28(pathApk: String, receiver: Class<PrefabKReceiverInstall>) {
        Log.d(TAG, "install28 path $pathApk")
        val apkFile = File(pathApk)
        val packageInstaller = _context.packageManager.packageInstaller
        val sessionParams = SessionParams(SessionParams.MODE_FULL_INSTALL)
        sessionParams.setSize(apkFile.length())
        val sessionId: Int = createSession(packageInstaller, sessionParams)
        Log.d(TAG, "install28 sessionId $sessionId")
        if (sessionId != -1) {
            val copySuccess = copyInstallFile(packageInstaller, sessionId, pathApk)
            Log.d(TAG, "install28 copySuccess $copySuccess")
            if (copySuccess) {
                execInstall(packageInstaller, sessionId, receiver)
            }
        }
    }

    private fun execInstall(packageInstaller: PackageInstaller, sessionId: Int, receiver: Class<PrefabKReceiverInstall>) {
        var session: PackageInstaller.Session? = null
        try {
            session = packageInstaller.openSession(sessionId)
            val intent = Intent(_context, receiver)
            val pendingIntent = PendingIntent.getBroadcast(
                _context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            session.commit(pendingIntent.intentSender)
            Log.d(TAG, "execInstall begin")
        } catch (e: IOException) {
            Log.e(TAG, "execInstall: IOException ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.e(TAG, "execInstall: Exception ${e.message}")
            e.printStackTrace()
        } finally {
            session?.close()
        }
    }

    private fun createSession(packageInstaller: PackageInstaller, sessionParams: SessionParams): Int {
        var sessionId = -1
        try {
            sessionId = packageInstaller.createSession(sessionParams)
        } catch (e: IOException) {
            Log.e(TAG, "createSession: ${e.message}")
            e.printStackTrace()
        }
        return sessionId
    }

    private fun copyInstallFile(
        packageInstaller: PackageInstaller,
        sessionId: Int,
        apkFilePath: String
    ): Boolean {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        var session: PackageInstaller.Session? = null
        var success = false
        try {
            val apkFile = File(apkFilePath)
            session = packageInstaller.openSession(sessionId)
            outputStream = session.openWrite("base.apk", 0, apkFile.length())
            inputStream = FileInputStream(apkFile)
            var total = 0
            var count: Int
            val buffer = ByteArray(65536)
            while (inputStream.read(buffer).also { count = it } != -1) {
                total += count
                outputStream.write(buffer, 0, count)
            }
            session.fsync(outputStream)
            Log.d(TAG, "copyInstallFile streamed $total bytes")
            success = true
        } catch (e: IOException) {
            Log.e(TAG, "copyInstallFile: IOException ${e.message}")
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
                inputStream?.close()
                session?.close()
            } catch (e: IOException) {
                Log.e(TAG, "copyInstallFile: IOException ${e.message}")
                e.printStackTrace()
            }
        }
        return success
    }
}