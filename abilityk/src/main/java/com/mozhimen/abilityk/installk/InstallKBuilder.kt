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
import com.mozhimen.basick.utilk.file.UtilKFileNet
import java.io.*

/**
 * @ClassName InstallK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:04
 * @Version 1.0
 */
@APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
class InstallKBuilder() : Handler(Looper.getMainLooper()) {

    companion object {
        private const val TAG = "InstallKBuilder>>>>>"


        @JvmStatic
        val instance = InstallKBuilderProvider.holder

        class Builder() {
            private var _installMode: EInstallMode = EInstallMode.BOTH
            private var _installStateChangedListener: IInstallStateChangedListener? = null
            private var _apkCacheDirectory = Environment.getExternalStorageDirectory().absolutePath

            fun setInstallMode(mode: EInstallMode): Builder {
                _installMode = mode
                return this
            }

            fun setInstallStateChangedListener(listener: IInstallStateChangedListener?): Builder {
                _installStateChangedListener = listener
                return this
            }

            fun setApkCacheDirectory(directory: String): Builder {
                _apkCacheDirectory = directory
                return this
            }

            fun build(): InstallKBuilder {
                val installKBuilder = InstallKBuilder()
                installKBuilder._installMode = _installMode
                installKBuilder._installStateChangeListener = _installStateChangedListener
                installKBuilder._tempApkPathWithName = _apkCacheDirectory
                return installKBuilder
            }
        }
    }

    private val _context = UtilKApplication.instance.get()
    private val _handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                CCons.MSG_INSTALL_START -> _installStateChangeListener?.onInstallStart()
                CCons.MSG_INSTALL_FINISH -> _installStateChangeListener?.onInstallFinish()
                CCons.MSG_NEED_OPEN_ACCESSIBILITY_SETTING -> _installStateChangeListener?.onNeedOpenAccessibilitySetting()
                CCons.MSG_NEED_PERMISSIONS -> _installStateChangeListener?.onNeedPermissions()
            }
        }
    }
    private var _tempApkPathWithName = "${_context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/installk/update.apk"
    private var _installMode = EInstallMode.BOTH
    private var _installStateChangeListener: IInstallStateChangedListener? = null

    /**
     * 下载并安装
     * @param apkUrl String
     */
    @APermissionK(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
    fun downloadFromUrlAndInstall(apkUrl: String) {
        Thread {
            _handler.sendEmptyMessage(CCons.MSG_INSTALL_START)
            try {
                installByMode(UtilKFileNet.downLoadFile(apkUrl, _tempApkPathWithName))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "downloadFromUrlAndInstall: ${e.message}")
                _handler.sendMessage(_handler.obtainMessage(CCons.MSG_INSTALL_ERROR, e.message))
            } finally {
                _handler.sendEmptyMessage(CCons.MSG_INSTALL_FINISH)
            }
        }.start()
    }

    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun install(apkPathWithName: String) {
        Thread {
            _handler.sendEmptyMessage(CCons.MSG_INSTALL_START)
            try {
                installByMode(apkPathWithName)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "install: ${e.message}")
                _handler.sendMessage(_handler.obtainMessage(CCons.MSG_INSTALL_ERROR, e.message))
            } finally {
                _handler.sendEmptyMessage(CCons.MSG_INSTALL_FINISH)
            }
        }.start()
    }

    @Throws(Exception::class)
    private fun installByMode(apkPathWithName: String) {
        require(apkPathWithName.isNotEmpty() && apkPathWithName.endsWith(".apk")) { "$TAG not a correct apk file path" }
        if (!PermissionK.checkPermissions(CCons.PERMISSIONS)) {
            _handler.sendEmptyMessage(CCons.MSG_NEED_PERMISSIONS)
            return
        }
        when (_installMode) {
            EInstallMode.BOTH -> if (!UtilKAppRoot.isRoot() || !UtilKAppInstall.installRoot(apkPathWithName)) installByAutoMode(apkPathWithName)
            EInstallMode.ROOT_ONLY -> UtilKAppInstall.installRoot(apkPathWithName)
            EInstallMode.AUTO_ONLY -> installByAutoMode(apkPathWithName)
        }
    }

    private fun installByAutoMode(apkPathWithName: String) {

        // 允许安装应用
        if (Build.VERSION.SDK_INT >= VersionCode.V_26_8_O) {
            val enable: Boolean = _context.packageManager.canRequestPackageInstalls()
            if (!enable) {
                sendEmptyMessage(4)
                return
            }
        }
        val file = File(apkPathWithName)
        if (!file.exists()) {
            Log.e(TAG, "apk file not exists, path: $apkPathWithName")
            return
        }

        UtilKAppInstall.installAuto(apkPathWithName)
        if (!UtilKPermission.isSettingAccessibilityPermissionEnable(_context, InstallKSmartService::class.java)) {
            UtilKPermission.openSettingAccessibility(_context)
            sendEmptyMessage(3)
        }
    }


    private object InstallKBuilderProvider {
        val holder = InstallKBuilder()
    }
}