package com.mozhimen.basick.utilk

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.SessionParams
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.mozhimen.basick.utilk.context.UtilKApplication
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
    private const val ACTION = "wait.install.result"

    private val _context = UtilKApplication.instance.get()

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getPkgVersionName(): String {
        return try {
            val packageInfo = _context.packageManager.getPackageInfo(_context.packageName, 0)
            packageInfo?.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getPkgVersionName: NameNotFoundException ${e.message}")
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
            val packageInfo = _context.packageManager.getPackageInfo(_context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getPkgVersionCode: NameNotFoundException ${e.message}")
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
        var result = "EMPTY"
        val cmd = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayOf("pm", "install", "-r", "-i", pkgName, "--user", "0", apkPath)
        } else {
            arrayOf("pm", "install", "-i", pkgName, "-r", apkPath)
        }
        val processBuilder = ProcessBuilder(*cmd)
        var process: Process? = null
        var inputStream: InputStream? = null
        var byteArrayOutputStream = ByteArrayOutputStream()
        try {
            var readCount: Int
            process = processBuilder.start()
            byteArrayOutputStream.write('/'.code)
            inputStream = process.inputStream
            while (inputStream.read().also { readCount = it } != -1) {
                byteArrayOutputStream.write(readCount)
            }
            result = String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8"))
            Log.d(TAG, "installSilence result $result")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "installSilence IOException ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "installSilence Exception ${e.message}")
        } finally {
            byteArrayOutputStream.close()
            inputStream?.close()
            process?.destroy()
        }
        return result.contains("success", ignoreCase = true)
    }

    /**
     * 静默安装
     * @param pathApk String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    fun installSilence(pathApk: String, receiver: Class<*>) {
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
        var resSuccessBufferedReader: BufferedReader? = null
        var resErrorBufferedReader: BufferedReader? = null

        val msgSuccess = StringBuilder()
        val msgError = StringBuilder()
        val cmd: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arrayOf("pm", "install", "-i", _context.packageName, "-r", pathApk)
        } else {
            arrayOf("pm", "install", "-r", pathApk)
        }
        try {
            process = ProcessBuilder(*cmd).start()
            resSuccessBufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            resErrorBufferedReader = BufferedReader(InputStreamReader(process.errorStream))
            var msg: String?
            while (resSuccessBufferedReader.readLine().also { msg = it } != null) {
                msgSuccess.append(msg)
            }
            while (resErrorBufferedReader.readLine().also { msg = it } != null) {
                msgError.append(msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "installSilenceBefore28: Exception ${e.message}")
        } finally {
            resSuccessBufferedReader?.close()
            resErrorBufferedReader?.close()
            process?.destroy()
        }
        return msgSuccess.toString().contains("success", ignoreCase = true)        //如果含有success单词则认为安装成功
    }

    /**
     * 静默安装 SDK28 之后的
     * @param apkPathWithName String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    fun installSilenceAfter28(apkPathWithName: String, receiver: Class<*>) {
        Log.d(TAG, "installSilenceAfter28 pathApk $apkPathWithName")
        val apkFile = File(apkPathWithName)
        val packageInstaller = _context.packageManager.packageInstaller
        val sessionParams = SessionParams(SessionParams.MODE_FULL_INSTALL)
        sessionParams.setSize(apkFile.length())
        val sessionId: Int = createSession(packageInstaller, sessionParams)
        Log.d(TAG, "installSilenceAfter28 sessionId $sessionId")

        if (sessionId != -1) {
            val isCopySuccess = copyApkFile(packageInstaller, sessionId, apkPathWithName)
            Log.d(TAG, "installSilenceAfter28 copySuccess $isCopySuccess")
            if (isCopySuccess) {
                execInstall(packageInstaller, sessionId, receiver)
            }
        }
    }

    private fun execInstall(packageInstaller: PackageInstaller, sessionId: Int, receiver: Class<*>) {
        var session: PackageInstaller.Session? = null
        try {
            session = packageInstaller.openSession(sessionId)
            val pendingIntent = PendingIntent.getBroadcast(_context, 1, Intent(_context, receiver), PendingIntent.FLAG_UPDATE_CURRENT)
            session.commit(pendingIntent.intentSender)
            Log.d(TAG, "execInstall begin")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "execInstall: IOException ${e.message}")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "execInstall: Exception ${e.message}")
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

    private fun copyApkFile(
        packageInstaller: PackageInstaller,
        sessionId: Int,
        apkFilePathWithName: String
    ): Boolean {
        var fileInputStream: FileInputStream? = null
        var outputStream: OutputStream? = null
        var session: PackageInstaller.Session? = null
        var success = false
        try {
            val apkFile = File(apkFilePathWithName)
            session = packageInstaller.openSession(sessionId)
            outputStream = session.openWrite("base.apk", 0, apkFile.length())
            fileInputStream = FileInputStream(apkFile)
            var total = 0
            var count: Int
            val buffer = ByteArray(65536)
            while (fileInputStream.read(buffer).also { count = it } != -1) {
                total += count
                outputStream.write(buffer, 0, count)
            }
            session.fsync(outputStream)
            success = true
            Log.d(TAG, "copyApkFile success $success")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "copyApkFile: IOException ${e.message}")
        } finally {
            session?.close()
            outputStream?.flush()
            outputStream?.close()
            fileInputStream?.close()
        }
        return success
    }
}