package com.mozhimen.basick.utilk.android.content

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.file2fileInputStream
import com.mozhimen.basick.utilk.java.io.flushClose
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
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(context: Context): PackageInstaller =
        UtilKPackageManager.getPackageInstaller(context)

    /////////////////////////////////////////////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun createSession(packageInstaller: PackageInstaller, sessionParams: PackageInstaller.SessionParams): Int {
        return packageInstaller.createSession(sessionParams)
    }

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun openSession(packageInstaller: PackageInstaller, sessionId: Int):PackageInstaller.Session{
        return packageInstaller.openSession(sessionId)
    }

    /**
     * 命令安装
     */
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @AManifestKRequire(CPermission.INSTALL_PACKAGES)
    @RequiresApi(CVersCode.V_21_5_L)
    fun commitSession(packageInstaller: PackageInstaller, sessionId: Int, receiverClazz: Class<*>) {
        var session: PackageInstaller.Session? = null
        try {
            session = openSession(packageInstaller,sessionId)
            session.commit(PendingIntent.getBroadcast(_context, 1, Intent(_context, receiverClazz), CPendingIntent.FLAG_UPDATE_CURRENT).intentSender)
            Log.d(TAG, "commitSession begin")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "commitSession: Exception ${e.message}")
        } finally {
            session?.close()
        }
    }

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, strFilePathNameApk: String): Boolean =
        copyBaseApk(packageInstaller, sessionId, File(strFilePathNameApk))

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, fileApk: File): Boolean {
        var outputStream: OutputStream? = null
        var session: PackageInstaller.Session? = null
        try {
            session = openSession(packageInstaller,sessionId)
            outputStream = session.openWrite("base.apk", 0, fileApk.length())
            fileApk.file2fileInputStream().inputStream2outputStream(outputStream, 65536)
            session.fsync(outputStream)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            "copyBaseApk: Exception ${e.message}".et(TAG)
        } finally {
            session?.close()
            outputStream?.flushClose()
        }
        return false
    }
}