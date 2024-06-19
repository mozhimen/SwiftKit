package com.mozhimen.basick.utilk.android.content

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.Session
import android.content.pm.PackageInstaller.SessionParams
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPendingIntentWrapper
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKInputStream
import com.mozhimen.basick.utilk.java.io.inputStream2bufferedInputStream
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import com.mozhimen.basick.utilk.kotlin.strFilePath2fileInputStream

/**
 * @ClassName UtilKPackageInstallerSession
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/18
 * @Version 1.0
 */
object UtilKPackageInstallerSession : BaseUtilK() {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(packageInstaller: PackageInstaller): Session =
        UtilKPackageInstaller.getSession(packageInstaller)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(packageInstaller: PackageInstaller, sessionParams: SessionParams): Session =
        UtilKPackageInstaller.getSession(packageInstaller, sessionParams)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(packageInstaller: PackageInstaller, sessionId: Int): Session =
        UtilKPackageInstaller.getSession(packageInstaller, sessionId)

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun abandon_close(session: Session) {
        session.apply {
            abandon()
            close()
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun commit_use(session: Session, intentSender: IntentSender) {
        try {
            UtilKLogWrapper.d(TAG, "commitSession commit")
            session.commit(intentSender)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "commitSession: Exception ${e.message}")
        } finally {
            session.close()
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun commit_use_ofBroadCast(packageInstaller: PackageInstaller, sessionId: Int, intent: Intent, requestCode: Int, flags: Int) {
        commit_use_ofBroadCast(get(packageInstaller, sessionId), intent, requestCode, flags)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun commit_use_ofBroadCast(session: Session, intent: Intent, requestCode: Int, flags: Int) {
        val intentSender = UtilKPendingIntentWrapper.get_ofBroadCast(requestCode/*1*/, intent, flags).intentSender
        commit_use(session, intentSender)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun commit_use_ofActivity(packageInstaller: PackageInstaller, sessionId: Int, intent: Intent, requestCode: Int, flags: Int) {
        val intentSender = UtilKPendingIntentWrapper.get_ofActivity(requestCode, intent, flags).intentSender
        commit_use(get(packageInstaller, sessionId), intentSender)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    fun commit_use_ofActivity(session: Session, intent: Intent, requestCode: Int, flags: Int) {
        val intentSender = UtilKPendingIntentWrapper.get_ofActivity(requestCode, intent, flags).intentSender
        commit_use(session, intentSender)
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun addStrApkPathNameToSession(strApkPathName: String, session: Session) {
        // It's recommended to pass the file size to openWrite(). Otherwise installation may fail
        // if the disk is almost full.
        val strFileName = UtilKStrFile.getStrFileName(strApkPathName) ?: return
        val outputStream = session.openWrite(strFileName,  /*getFileName()*/0, strApkPathName.strFilePath2file().length())
        val bufferedInputStream = strApkPathName.strFilePath2fileInputStream().inputStream2bufferedInputStream()
        UtilKInputStream.read_write_use(bufferedInputStream, outputStream, 16384)
        Log.d(TAG, "addStrApkPathNameToSession: strApkPathName $strApkPathName")
    }
}