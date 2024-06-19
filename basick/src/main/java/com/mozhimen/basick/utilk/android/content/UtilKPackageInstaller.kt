package com.mozhimen.basick.utilk.android.content

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.Session
import android.content.pm.PackageInstaller.SessionParams
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.elemk.android.content.cons.CPackageInstaller
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_INSTALL_PACKAGES
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKInputStream
import com.mozhimen.basick.utilk.java.io.file2fileInputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2bufferedInputStream
import com.mozhimen.basick.utilk.java.io.isFileExist
import com.mozhimen.basick.utilk.java.io.isFileNotExist
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import com.mozhimen.basick.utilk.kotlin.strFilePath2fileInputStream
import kotlinx.coroutines.flow.combineTransform
import java.io.File
import java.io.OutputStream

/**
 * @ClassName UtilKPackageInstaller
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/3 21:53
 * @Version 1.0
 */
object UtilKPackageInstaller : BaseUtilK() {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(context: Context): PackageInstaller =
        UtilKPackageManager.getPackageInstaller(context)

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getSession(packageInstaller: PackageInstaller): Session =
        getSession(packageInstaller, SessionParams(CPackageInstaller.SessionParams.MODE_FULL_INSTALL))

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getSession(packageInstaller: PackageInstaller, sessionParams: SessionParams): Session =
        getSession(packageInstaller, createSession(packageInstaller, sessionParams))

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getSession(packageInstaller: PackageInstaller, sessionId: Int): Session =
        openSession(packageInstaller, sessionId)

    /////////////////////////////////////////////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun createSession(packageInstaller: PackageInstaller, sessionParams: SessionParams): Int =
        packageInstaller.createSession(sessionParams)

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun openSession(packageInstaller: PackageInstaller, sessionId: Int): Session =
        packageInstaller.openSession(sessionId)

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, strFilePathNameApk: String): Boolean =
        copyBaseApk(packageInstaller, sessionId, strFilePathNameApk.strFilePath2file())

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun copyBaseApk(packageInstaller: PackageInstaller, sessionId: Int, fileApk: File): Boolean {
        if (fileApk.isFileNotExist())
            return false
        var outputStream: OutputStream? = null
        var session: Session? = null
        try {
            session = openSession(packageInstaller, sessionId)
            outputStream = session.openWrite("base.apk", 0, fileApk.length())
            UtilKInputStream.read_write_use(fileApk.file2fileInputStream()?:return false, outputStream, 65536)
            session.fsync(outputStream)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            "copyBaseApk: Exception ${e.message}".e(TAG)
        } finally {
            session?.close()
            outputStream?.flushClose()
        }
        return false
    }
}