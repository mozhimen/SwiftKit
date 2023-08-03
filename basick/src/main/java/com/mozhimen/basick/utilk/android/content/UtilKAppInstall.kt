package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.elemk.android.content.cons.CPackageInstaller
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optin.OptInDeviceRoot
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.UtilKInputStream
import java.io.*

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
    fun hasPackageInstalls(): Boolean =
        UtilKPermission.hasPackageInstalls().also { Log.d(TAG, "isAppInstallsPermissionEnable: $it") }

    ///////////////////////////////////////////////////////////////////////////

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
        var process: Process? = null
        var outputStream: OutputStream? = null
        var bufferedReader: BufferedReader? = null
        try {
            process = Runtime.getRuntime().exec("su")

            outputStream = process.outputStream
            outputStream.write("pm install -r $apkPathWithName\n".toByteArray())
            outputStream.flush()
            outputStream.write("exit\n".toByteArray())
            outputStream.flush()

            process.waitFor()
            bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null)
                stringBuilder.append(line)
            "installRoot msg is $stringBuilder".dt(TAG)
            return !stringBuilder.toString().contains("failure", ignoreCase = true)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream?.flush()
            outputStream?.close()
            bufferedReader?.close()
            process?.destroy()
        }
        return false
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
        var strRes = ""
        val cmd =
            if (UtilKBuildVersion.isAfterV_24_7_N()) arrayOf("pm", "install", "-r", "-i", pkgName, "--user", "0", apkPathWithName)
            else arrayOf("pm", "install", "-i", pkgName, "-r", apkPathWithName)

        val byteArrayOutputStream = ByteArrayOutputStream()
        val processBuilder = ProcessBuilder(*cmd)
        var process: Process? = null
        var inputStream: InputStream? = null
        try {
            process = processBuilder.start()
            byteArrayOutputStream.write('/'.code)
            inputStream = process.inputStream
            strRes = UtilKInputStream.inputStream2str2(inputStream, byteArrayOutputStream)
            "installSilence result $strRes".dt(TAG)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            inputStream?.close()
            process?.destroy()
        }
        return strRes.contains("success", ignoreCase = true)
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
     * @param apkPathWithName String
     * @param receiver Class<LoadKReceiverInstall>
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun installSilence(apkPathWithName: String, receiver: Class<*>) {
        if (UtilKBuildVersion.isAfterV_28_9_P()) installSilenceAfter28(apkPathWithName, receiver)
        else installSilenceBefore28(apkPathWithName)
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

        val stringBuilderSuccess = StringBuilder()
        val stringBuilderFail = StringBuilder()
        val cmd: Array<String> =
            if (UtilKBuildVersion.isAfterV_24_7_N())
                arrayOf("pm", "install", "-i", UtilKPackage.getPackageName(), "-r", apkPathWithName)
            else arrayOf("pm", "install", "-r", apkPathWithName)
        try {
            process = ProcessBuilder(*cmd).start()
            resSuccessBufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            resErrorBufferedReader = BufferedReader(InputStreamReader(process.errorStream))
            var msg: String?
            while (resSuccessBufferedReader.readLine().also { msg = it } != null) {
                stringBuilderSuccess.append(msg)
            }
            while (resErrorBufferedReader.readLine().also { msg = it } != null) {
                stringBuilderFail.append(msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "installSilenceBefore28: Exception ${e.message}")
        } finally {
            resSuccessBufferedReader?.close()
            resErrorBufferedReader?.close()
            process?.destroy()
        }
        return stringBuilderSuccess.toString().contains("success", ignoreCase = true)        //如果含有success单词则认为安装成功
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
        val packageInstaller = UtilKPackageInstaller.get(_context)
        val sessionParams = PackageInstaller.SessionParams(CPackageInstaller.SessionParams.MODE_FULL_INSTALL)
        sessionParams.setSize(apkFile.length())
        val sessionId: Int = UtilKPackageInstaller.createSession(packageInstaller, sessionParams)
        Log.d(TAG, "installSilenceAfter28 sessionId $sessionId")

        if (sessionId != -1) {
            val isCopySuccess = UtilKPackageInstaller.copyBaseApk(packageInstaller, sessionId, apkPathWithName)
            Log.d(TAG, "installSilenceAfter28 isCopySuccess $isCopySuccess")
            if (isCopySuccess)
                UtilKPackageInstaller.commitSession(packageInstaller, sessionId, receiver)
        }
    }
}