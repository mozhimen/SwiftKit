package com.mozhimen.abilityk.installk

import android.Manifest
import android.os.*
import android.util.Log
import com.mozhimen.abilityk.installk.commons.IInstallStateChangedListener
import com.mozhimen.abilityk.installk.cons.CCons
import com.mozhimen.abilityk.installk.cons.EInstallMode
import com.mozhimen.basick.elemk.cons.VersionCode
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.app.UtilKAppInstall
import com.mozhimen.basick.utilk.app.UtilKAppRoot
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.file.UtilKFile
import com.mozhimen.basick.utilk.file.UtilKFileNet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

/**
 * @ClassName InstallK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:04
 * @Version 1.0
 */
@APermissionK(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.INTERNET,
    Manifest.permission.REQUEST_INSTALL_PACKAGES
)
class InstallKBuilder {

    companion object {
        private const val TAG = "InstallKBuilder>>>>>"

        @JvmStatic
        val instance = InstallKBuilderProvider.holder

        class Builder {
            private var _installMode: EInstallMode = EInstallMode.BOTH
            private var _installStateChangedListener: IInstallStateChangedListener? = null
            private var _installCacheDirectory = Environment.getExternalStorageDirectory().absolutePath

            fun setInstallMode(mode: EInstallMode): Builder {
                _installMode = mode
                return this
            }

            fun setInstallStateChangedListener(listener: IInstallStateChangedListener?): Builder {
                _installStateChangedListener = listener
                return this
            }

            fun setInstallCacheDirectory(directory: String): Builder {
                _installCacheDirectory = directory
                return this
            }

            fun build(): InstallKBuilder {
                val installKBuilder = InstallKBuilder()
                installKBuilder._installMode = _installMode
                installKBuilder._installStateChangeListener = _installStateChangedListener
                installKBuilder._tempApkPathWithName = _installCacheDirectory
                return installKBuilder
            }
        }
    }

    private val _context = UtilKApplication.instance.get()
    private var _tempApkPathWithName = "${_context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/installk/update.apk"
    private var _installMode = EInstallMode.BOTH
    private var _installStateChangeListener: IInstallStateChangedListener? = null

    /**
     * 下载并安装
     * @param apkUrl String
     */
    @APermissionK(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.REQUEST_INSTALL_PACKAGES
    )
    suspend fun downloadFromUrlAndInstall(apkUrl: String) {
        try {
            _installStateChangeListener?.onDownloadStart()
            var apkPathWithName: String
            withContext(Dispatchers.IO) {
                apkPathWithName = UtilKFileNet.downLoadFile(apkUrl, _tempApkPathWithName)
            }
            installByMode(apkPathWithName)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "downloadFromUrlAndInstall: ${e.message}")
            _installStateChangeListener?.onInstallFail(e.message)
        } finally {
            _installStateChangeListener?.onInstallFinish()
        }
    }

    @APermissionK(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.REQUEST_INSTALL_PACKAGES
    )
    suspend fun install(apkPathWithName: String) {
        try {
            _installStateChangeListener?.onInstallStart()
            installByMode(apkPathWithName)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "install: ${e.message}")
            _installStateChangeListener?.onInstallFail(e.message)
        } finally {
            _installStateChangeListener?.onInstallFinish()
        }
    }

    @Throws(Exception::class)
    private suspend fun installByMode(apkPathWithName: String) {
        require(apkPathWithName.isNotEmpty() && apkPathWithName.endsWith(".apk")) { "$TAG not a correct apk file path" }
        if (!PermissionK.checkPermissions(CCons.PERMISSIONS)) {
            _installStateChangeListener?.onNeedPermissions()
            return
        }
        if (Build.VERSION.SDK_INT >= VersionCode.V_26_8_O && !UtilKPermission.isPackageInstallsPermissionEnable(_context)) {        // 允许安装应用
            _installStateChangeListener?.onNeedPermissions()
            return
        }

        when (_installMode) {
            EInstallMode.BOTH -> if (!UtilKAppRoot.isRoot() || !UtilKAppInstall.installRoot(apkPathWithName)) installByAutoMode(apkPathWithName)
            EInstallMode.ROOT_ONLY -> UtilKAppInstall.installRoot(apkPathWithName)
            EInstallMode.AUTO_ONLY -> installByAutoMode(apkPathWithName)
        }
    }

    private suspend fun installByAutoMode(apkPathWithName: String) {
        withContext(Dispatchers.Main) {
            if (!UtilKFile.isFileExist(apkPathWithName)) {
                Log.e(TAG, "installByAutoMode apk file not exists, path: $apkPathWithName")
                return@withContext
            }
            UtilKAppInstall.installAuto(apkPathWithName)
            if (!UtilKPermission.isAccessibilityPermissionEnable(_context, InstallKAutoService::class.java)) {
                UtilKPermission.openSettingAccessibility(_context)
            }
        }
    }

    private object InstallKBuilderProvider {
        val holder = InstallKBuilder()
    }
}