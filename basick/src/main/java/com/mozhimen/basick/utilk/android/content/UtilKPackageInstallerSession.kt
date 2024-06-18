package com.mozhimen.basick.utilk.android.content

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.Session
import android.content.pm.PackageInstaller.SessionParams
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKPackageInstaller.openSession
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.bases.BaseUtilK

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

    /////////////////////////////////////////////////////////////////

    ///命令安装
    @JvmStatic
    @RequiresPermission(CPermission.INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @RequiresApi(CVersCode.V_21_5_L)
    fun commitSession(packageInstaller: PackageInstaller, sessionId: Int, receiverClazz: Class<*>) {
        var session: Session? = null
        try {
            session = openSession(packageInstaller, sessionId)
            session.commit(PendingIntent.getBroadcast(_context, 1, Intent(_context, receiverClazz), CPendingIntent.FLAG_UPDATE_CURRENT).intentSender)
            UtilKLogWrapper.d(TAG, "commitSession begin")
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "commitSession: Exception ${e.message}")
        } finally {
            session?.close()
        }
    }
}