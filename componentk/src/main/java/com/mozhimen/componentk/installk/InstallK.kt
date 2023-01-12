package com.mozhimen.componentk.installk

import android.os.*
import android.util.Log
import com.mozhimen.componentk.installk.commons.IInstallStateChangedListener
import com.mozhimen.componentk.installk.cons.CCons
import com.mozhimen.componentk.installk.cons.EInstallMode
import com.mozhimen.componentk.installk.cons.EPermissionType
import com.mozhimen.componentk.installk.helpers.InstallKAutoService
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
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
 * @ClassName InstallKBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:04
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET,
    CPermission.REQUEST_INSTALL_PACKAGES,
    CPermission.INSTALL_PACKAGES,
    CPermission.READ_INSTALL_SESSIONS,
    CPermission.REPLACE_EXISTING_PACKAGE,
    CPermission.BIND_ACCESSIBILITY_SERVICE
)
class InstallK {

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

            fun build(): InstallK {
                val installKBuilder = InstallK()
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
     * 设置监听器
     * @param listener IInstallStateChangedListener
     */
    fun setInstallStateChangeListener(listener: IInstallStateChangedListener): InstallK {
        _installStateChangeListener = listener
        return this
    }

    /**
     * 下载并安装
     * @param apkUrl String
     */
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
        if (!ManifestKPermission.checkPermissions(CCons.PERMISSIONS)) {
            Log.w(TAG, "installByMode: onNeedPermissions PERMISSIONS")
            _installStateChangeListener?.onNeedPermissions(EPermissionType.COMMON)
            return
        }
        if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O && !UtilKAppInstall.isAppInstallsPermissionEnable(_context)) {        // 允许安装应用
            Log.w(TAG, "installByMode: onNeedPermissions isAppInstallsPermissionEnable false")
            _installStateChangeListener?.onNeedPermissions(EPermissionType.INSTALL)
            return
        }

        when (_installMode) {
            EInstallMode.BOTH -> if (!UtilKAppRoot.isRoot() || !UtilKAppInstall.installRoot(apkPathWithName)) installByAutoMode(apkPathWithName).also {
                Log.d(TAG, "installByMode: start installByAutoMode")
            }
            EInstallMode.ROOT_ONLY -> UtilKAppInstall.installRoot(apkPathWithName)
            EInstallMode.AUTO_ONLY -> installByAutoMode(apkPathWithName)
        }
    }

    private suspend fun installByAutoMode(apkPathWithName: String) {
        withContext(Dispatchers.Main) {
            if (!UtilKFile.isFileExist(apkPathWithName)) {
                Log.e(TAG, "installByAutoMode apk file not exists, path: $apkPathWithName")
                _installStateChangeListener?.onInstallFail("apk file not exists")
                return@withContext
            }
            if (!UtilKPermission.isAccessibilityPermissionEnable(_context, InstallKAutoService::class.java)) {
                Log.w(TAG, "installByMode: onNeedPermissions isAccessibilityPermissionEnable false")
                _installStateChangeListener?.onNeedPermissions(EPermissionType.ACCESSIBILITY)
            }
            UtilKAppInstall.installAuto(apkPathWithName, "com.mozhimen.componentk")
        }
    }

    private object InstallKBuilderProvider {
        val holder = InstallK()
    }
}