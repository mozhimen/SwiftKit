package com.mozhimen.basick.utilk.android.content

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInstaller
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CPackageInstaller
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optin.OptInDeviceRoot
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.lang.UtilKRuntime
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
        UtilKPermission.hasPackageInstalls().also { "isAppInstallsPermissionEnable: $it".dt(TAG) }

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
    fun installRoot(apkPathWithName: String): Boolean =
        UtilKRuntime.execSuInstall(apkPathWithName)

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
        else UtilKRuntime.execInstallBefore28(apkPathWithName)
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
        "installSilenceAfter28 pathApk $apkPathWithName".dt(TAG)
        val apkFile = File(apkPathWithName)
        val packageInstaller = UtilKPackageInstaller.get(_context)
        val sessionParams = PackageInstaller.SessionParams(CPackageInstaller.SessionParams.MODE_FULL_INSTALL).apply {
            setSize(apkFile.length())
        }
        val sessionId: Int = UtilKPackageInstaller.createSession(packageInstaller, sessionParams)
        "installSilenceAfter28 sessionId $sessionId".dt(TAG)

        if (sessionId != -1) {
            val isCopySuccess = UtilKPackageInstaller.copyBaseApk(packageInstaller, sessionId, apkPathWithName)
            "installSilenceAfter28 isCopySuccess $isCopySuccess".dt(TAG)
            if (isCopySuccess)
                UtilKPackageInstaller.commitSession(packageInstaller, sessionId, receiver)
        }
    }
}