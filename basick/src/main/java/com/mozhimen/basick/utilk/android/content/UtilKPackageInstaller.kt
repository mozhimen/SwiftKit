package com.mozhimen.basick.utilk.android.content

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2outputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.OutputStream

/**
 * @ClassName UtilKPackageInstaller
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/3 21:53
 * @Version 1.0
 */
object UtilKPackageInstaller : BaseUtilK() {
    fun get(context: Context): PackageInstaller =
        UtilKPackageManager.getPackageInstaller(context)

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun createSession(packageInstaller: PackageInstaller, sessionParams: PackageInstaller.SessionParams): Int {
        var sessionId = -1
        try {
            sessionId = packageInstaller.createSession(sessionParams)
        } catch (e: IOException) {
            "createSession: ${e.message}".et(TAG)
            e.printStackTrace()
        }
        return sessionId
    }

    /**
     * 命令安装
     * @param packageInstaller PackageInstaller
     * @param sessionId Int
     * @param receiverClazz Class<*>
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    fun commitSession(packageInstaller: PackageInstaller, sessionId: Int, receiverClazz: Class<*>) {
        var session: PackageInstaller.Session? = null
        try {
            session = packageInstaller.openSession(sessionId)
            session.commit(PendingIntent.getBroadcast(_context, 1, Intent(_context, receiverClazz), CPendingIntent.FLAG_UPDATE_CURRENT).intentSender)
            Log.d(TAG, "commitSession begin")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "commitSession: Exception ${e.message}")
        } finally {
            session?.close()
        }
    }

    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, apkFilePathWithName: String): Boolean =
        copyBaseApk(packageInstaller, sessionId, File(apkFilePathWithName))

    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, apkFile: File): Boolean {
        var fileInputStream: FileInputStream? = null
        var session: PackageInstaller.Session? = null
        var outputStream: OutputStream? = null
        try {
            session = packageInstaller.openSession(sessionId)
            outputStream = session.openWrite("base.apk", 0, apkFile.length())
            fileInputStream = FileInputStream(apkFile)

            fileInputStream.inputStream2outputStream(outputStream, 65536)
            session.fsync(outputStream)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            "copyApkFile: Exception ${e.message}".et(TAG)
        } finally {
            session?.close()
            outputStream?.flush()
            outputStream?.close()
            fileInputStream?.close()
        }
        return false
    }
}