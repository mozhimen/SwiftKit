package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optin.OptInDeviceRoot
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.io.*
import java.nio.charset.Charset

/**
 * @ClassName UtilKAppInstall
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:29
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.INSTALL_PACKAGES,
    CPermission.REQUEST_INSTALL_PACKAGES,
    CPermission.READ_INSTALL_SESSIONS,
    CPermission.REPLACE_EXISTING_PACKAGE
)
object UtilKAppInstall : BaseUtilK() {

    /**
     * 是否有包安装权限
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun isAppInstallsPermissionEnable(): Boolean {
        return UtilKPermission.hasPackageInstalls().also { Log.d(TAG, "isAppInstallsPermissionEnable: $it") }
    }

    /**
     * 打开包安装权限
     * @param activity Activity
     */
    @JvmStatic
    fun openSettingAppInstall(activity: Activity) {
        UtilKLaunchActivity.startManageInstallSource(activity)
    }

    /**
     * 打开包安装权限
     * @param context Context
     */
    @JvmStatic
    fun openSettingAppInstall(context: Context) {
        UtilKLaunchActivity.startManageInstallSource(context)
    }

    @JvmStatic
    @Throws(Exception::class)
    @OptInDeviceRoot
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installRoot(apkPathWithName: String): Boolean {
        require(apkPathWithName.isNotEmpty()) { "$TAG please check apk file path" }
        var result = false
        var process: Process? = null
        var outputStream: OutputStream? = null
        var bufferedReader: BufferedReader? = null
        try {
            process = Runtime.getRuntime().exec("su")
            outputStream = process.outputStream
            val command = "pm install -r $apkPathWithName\n"
            outputStream.write(command.toByteArray())
            outputStream.flush()
            outputStream.write("exit\n".toByteArray())
            outputStream.flush()

            process.waitFor()
            bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
            val msg = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                msg.append(line)
            }
            Log.d(TAG, "installRoot msg is $msg")
            if (!msg.toString().contains("Failure", ignoreCase = true)) {
                result = true
            }
        } catch (e: Exception) {
            throw e
        } finally {
            outputStream?.flush()
            outputStream?.close()
            bufferedReader?.close()
            process?.destroy()
        }
        return result.also { Log.d(TAG, "installRoot: result $it apkPathWithName $apkPathWithName") }
    }

    /**
     * 手动安装
     * if sdk >= 24 add provider
     * @param apkPathWithName String
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installHand(apkPathWithName: String) {
        UtilKLaunchActivity.startInstall(_context, apkPathWithName)
    }

    /**
     * 静默安装
     * @param apkPathWithName String 绝对路径
     * @param pkgName String
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installSilence(apkPathWithName: String, pkgName: String): Boolean {
        var result = "EMPTY"
        val cmd =
            if (UtilKBuildVersion.isAfterV_24_7_N()) {
                arrayOf("pm", "install", "-r", "-i", pkgName, "--user", "0", apkPathWithName)
            } else {
                arrayOf("pm", "install", "-i", pkgName, "-r", apkPathWithName)
            }
        val processBuilder = ProcessBuilder(*cmd)
        var process: Process? = null
        var inputStream: InputStream? = null
        val byteArrayOutputStream = ByteArrayOutputStream()
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
     * @param apkPathWithName String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installSilence(apkPathWithName: String, receiver: Class<*>) {
        if (UtilKBuildVersion.isAfterV_28_9_P()) {
            installSilenceAfter28(apkPathWithName, receiver)
        } else {
            installSilenceBefore28(apkPathWithName)
        }
    }

    /**
     * 静默安装适配 SDK28 之前的安装方法
     * @param apkPathWithName String
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installSilenceBefore28(apkPathWithName: String): Boolean {
        var process: Process? = null
        var resSuccessBufferedReader: BufferedReader? = null
        var resErrorBufferedReader: BufferedReader? = null

        val msgSuccess = StringBuilder()
        val msgError = StringBuilder()
        val cmd: Array<String> =
            if (UtilKBuildVersion.isAfterV_24_7_N()) {
                arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", apkPathWithName)
            } else {
                arrayOf("pm", "install", "-r", apkPathWithName)
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
    @RequiresApi(CVersCode.V_28_9_P)
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installSilenceAfter28(apkPathWithName: String, receiver: Class<*>) {
        Log.d(TAG, "installSilenceAfter28 pathApk $apkPathWithName")
        val apkFile = File(apkPathWithName)
        val packageInstaller = UtilKPackageManager.getPackageInstaller(_context)
        val sessionParams = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
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

    /**
     * 命令安装
     * @param packageInstaller PackageInstaller
     * @param sessionId Int
     * @param receiver Class<*>
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
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

    /**
     * createSession
     * @param packageInstaller PackageInstaller
     * @param sessionParams SessionParams
     * @return Int
     */
    private fun createSession(packageInstaller: PackageInstaller, sessionParams: PackageInstaller.SessionParams): Int {
        var sessionId = -1
        try {
            sessionId = packageInstaller.createSession(sessionParams)
        } catch (e: IOException) {
            Log.e(TAG, "createSession: ${e.message}")
            e.printStackTrace()
        }
        return sessionId
    }

    /**
     * 拷贝
     * @param packageInstaller PackageInstaller
     * @param sessionId Int
     * @param apkFilePathWithName String
     * @return Boolean
     */
    fun copyApkFile(
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
            Log.d(TAG, "copyApkFile success")
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