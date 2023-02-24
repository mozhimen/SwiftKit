package com.mozhimen.basick.utilk.app

import android.annotation.TargetApi
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.content.UtilKActivitySkip
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.java.io.file.UtilKFileUri
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
object UtilKAppInstall {
    private const val TAG = "UtilKAppInstall>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * 是否有包安装权限
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    fun isAppInstallsPermissionEnable(context: Context): Boolean {
        return context.packageManager.canRequestPackageInstalls().also { Log.d(TAG, "isAppInstallsPermissionEnable: $it") }
    }

    /**
     * 打开包安装权限
     * @param activity Activity
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    fun openSettingAppInstall(activity: Activity) {
        UtilKActivitySkip.start(
            activity,
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:${activity.packageName}"))
        )
    }

    /**
     * 打开包安装权限
     * @param context Context
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    fun openSettingAppInstall(context: Context) {
        UtilKActivitySkip.start(
            context,
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:${context.packageName}"))
        )
    }

    @JvmStatic
    @Throws(Exception::class)
    @ADescription("need you device has rooted")
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
     * 智能安装
     * if sdk >= 24 add provider
     * @param apkPathWithName String
     */
    @JvmStatic
    fun installHand(apkPathWithName: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {//判断安卓系统是否大于7.0  大于7.0使用以下方法
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(UtilKFileUri.file2Uri(apkPathWithName) ?: return, "application/vnd.android.package-archive")
        UtilKActivitySkip.start(_context, intent)
    }

    /**
     * 静默安装
     * @param apkPathWithName String 绝对路径
     * @param pkgName String
     * @return Boolean
     */
    @JvmStatic
    fun installSilence(apkPathWithName: String, pkgName: String): Boolean {
        var result = "EMPTY"
        val cmd = if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {
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
    fun installSilence(apkPathWithName: String, receiver: Class<*>) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
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
    fun installSilenceBefore28(apkPathWithName: String): Boolean {
        var process: Process? = null
        var resSuccessBufferedReader: BufferedReader? = null
        var resErrorBufferedReader: BufferedReader? = null

        val msgSuccess = StringBuilder()
        val msgError = StringBuilder()
        val cmd: Array<String> = if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {
            arrayOf("pm", "install", "-i", _context.packageName, "-r", apkPathWithName)
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
    @RequiresApi(CVersionCode.V_28_9_P)
    fun installSilenceAfter28(apkPathWithName: String, receiver: Class<*>) {
        Log.d(TAG, "installSilenceAfter28 pathApk $apkPathWithName")
        val apkFile = File(apkPathWithName)
        val packageInstaller = _context.packageManager.packageInstaller
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