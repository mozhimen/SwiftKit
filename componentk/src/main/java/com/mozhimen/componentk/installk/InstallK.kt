package com.mozhimen.componentk.installk

import android.os.*
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.android.content.UtilKApp
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKApplicationInfo
import com.mozhimen.basick.utilk.android.os.UtilKOSRoot
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.componentk.installk.commons.IInstallK
import com.mozhimen.componentk.installk.commons.IInstallStateChangedListener
import com.mozhimen.componentk.installk.cons.CInstallKCons
import com.mozhimen.componentk.installk.cons.EInstallMode
import com.mozhimen.componentk.installk.cons.EPermissionType
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
    CPermission.REQUEST_INSTALL_PACKAGES,
    CPermission.INSTALL_PACKAGES,
    CPermission.READ_INSTALL_SESSIONS,
    CPermission.REPLACE_EXISTING_PACKAGE,
    CPermission.BIND_ACCESSIBILITY_SERVICE,
    CManifest.SERVICE_ACCESSIBILITY
)
class InstallK : IInstallK, BaseUtilK() {

    private var _installMode = EInstallMode.AUTO
    private var _installStateChangeListener: IInstallStateChangedListener? = null
    private var _smartServiceClazz: Class<*>? = null
    private var _silenceReceiverClazz: Class<*>? = null
    private val _handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                CInstallKCons.MSG_DOWNLOAD_START -> _installStateChangeListener?.onDownloadStart()
                CInstallKCons.MSG_INSTALL_START -> _installStateChangeListener?.onInstallStart()
                CInstallKCons.MSG_INSTALL_FINISH -> _installStateChangeListener?.onInstallFinish()
                CInstallKCons.MSG_INSTALL_FAIL -> _installStateChangeListener?.onInstallFail(msg.obj as String)
                CInstallKCons.MSG_NEED_PERMISSION -> _installStateChangeListener?.onNeedPermissions(msg.obj as EPermissionType)
            }
        }
    }

    /**
     * 设置安装
     * @param receiverClazz Class<*>
     * @return InstallK
     */
    override fun setInstallSilenceReceiver(receiverClazz: Class<*>): InstallK {
        _silenceReceiverClazz = receiverClazz
        return this
    }

    /**
     * 设置监听器
     * @param listener IInstallStateChangedListener
     */
    override fun setInstallStateChangeListener(listener: IInstallStateChangedListener): InstallK {
        _installStateChangeListener = listener
        return this
    }

    /**
     * 设置安装模式
     * @param mode EInstallMode
     * @return InstallK
     */
    override fun setInstallMode(mode: EInstallMode): InstallK {
        _installMode = mode
        return this
    }

    /**
     * 设置智能安装服务
     * @param serviceClazz Class<*>
     * @return InstallK
     */
    override fun setInstallSmartService(serviceClazz: Class<*>): InstallK {
        _smartServiceClazz = serviceClazz
        return this
    }

    /**
     * 安装
     * @param apkPathWithName String
     */
    suspend fun install(apkPathWithName: String) {
        withContext(Dispatchers.Main) {
            try {
                _handler.sendEmptyMessage(CInstallKCons.MSG_INSTALL_START)
                installByMode(apkPathWithName)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "install: ${e.message}")
                e.message?.et(TAG)
                _handler.sendMessage(Message().apply {
                    what = CInstallKCons.MSG_INSTALL_FAIL
                    obj = e.message ?: ""
                })
            } finally {
                _handler.sendEmptyMessage(CInstallKCons.MSG_INSTALL_FINISH)
            }
        }
    }

    @Throws(Exception::class)
    private fun installByMode(apkPathWithName: String) {
        require(apkPathWithName.isNotEmpty() && apkPathWithName.endsWith(".apk")) { "$TAG $apkPathWithName not a correct apk file path" }
        require(UtilKFile.isFileExist(apkPathWithName)) { "$TAG $apkPathWithName is not exist" }
        if (!ManifestKPermission.checkPermissions(CInstallKCons.PERMISSIONS)) {
            Log.w(TAG, "installByMode: onNeedPermissions PERMISSIONS")
            _handler.sendMessage(Message().apply {
                what = CInstallKCons.MSG_NEED_PERMISSION
                obj = EPermissionType.COMMON
            })
            return
        }
        val targetSdkVersion = UtilKApplicationInfo.getTargetSdkVersion(_context)
        requireNotNull(targetSdkVersion)
        if (targetSdkVersion >= CVersCode.V_26_8_O && UtilKBuildVers.isAfterV_26_8_O() && !UtilKAppInstall.isAppInstallsPermissionEnable()) {        // 允许安装应用
            Log.w(TAG, "installByMode: onNeedPermissions isAppInstallsPermissionEnable false")
            _handler.sendMessage(Message().apply {
                what = CInstallKCons.MSG_NEED_PERMISSION
                obj = EPermissionType.INSTALL
            })
            return
        }

        when (_installMode) {
            EInstallMode.AUTO -> {
                //try install root
                if (UtilKOSRoot.isRoot() && UtilKAppInstall.installRoot(apkPathWithName)) {
                    Log.d(TAG, "installByMode: AUTO as ROOT success")
                    return
                }
                //try install silence
                if (_silenceReceiverClazz != null && (UtilKOSRoot.isRoot() || !UtilKApp.isUserApp(_context))) {
                    UtilKAppInstall.installSilence(apkPathWithName, _silenceReceiverClazz!!)
                    Log.d(TAG, "installByMode: AUTO as SILENCE success")
                    return
                }
                //try install smart
                if (_smartServiceClazz != null && UtilKPermission.hasAccessibility(_smartServiceClazz!!)) {
                    UtilKAppInstall.installHand(apkPathWithName)
                    Log.d(TAG, "installByMode: AUTO as SMART success")
                    return
                }
                //try install hand
                UtilKAppInstall.installHand(apkPathWithName)
            }

            EInstallMode.ROOT -> {
                require(UtilKOSRoot.isRoot()) { "$TAG this device has not root" }
                UtilKAppInstall.installRoot(apkPathWithName)
                Log.d(TAG, "installByMode: ROOT success")
            }

            EInstallMode.SILENCE -> {
                requireNotNull(_silenceReceiverClazz) { "$TAG silence receiver must not be null" }
                require(UtilKOSRoot.isRoot() || !UtilKApp.isUserApp(_context)) { "$TAG this device has not root or its system app" }
                UtilKAppInstall.installSilence(apkPathWithName, _silenceReceiverClazz!!)
                Log.d(TAG, "installByMode: SILENCE success")
            }

            EInstallMode.SMART -> {
                requireNotNull(_smartServiceClazz) { "$TAG smart service must not be null" }
                if (!UtilKPermission.hasAccessibility(_smartServiceClazz!!)) {
                    Log.w(TAG, "installByMode: SMART isAccessibilityPermissionEnable false")
                    _handler.sendMessage(Message().apply {
                        what = CInstallKCons.MSG_NEED_PERMISSION
                        obj = EPermissionType.ACCESSIBILITY
                    })
                    return
                }
                UtilKAppInstall.installHand(apkPathWithName)
                Log.d(TAG, "installByMode: SMART success")
            }

            EInstallMode.HAND -> {
                UtilKAppInstall.installHand(apkPathWithName)
                Log.d(TAG, "installByMode: HAND success")
            }
        }
    }

//    /**
//     * 下载并安装
//     * @param apkUrl String
//     */
//    suspend fun downloadFromUrlAndInstall(apkUrl: String) {
//        try {
//            _handler.sendEmptyMessage(CCons.MSG_DOWNLOAD_START)
//            var apkPathWithName: String
//            withContext(Dispatchers.IO) {
//                apkPathWithName = UtilKFileNet.downLoadFile(apkUrl, _tempApkPathWithName)
//            }
//            installByMode(apkPathWithName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e(TAG, "downloadFromUrlAndInstall: ${e.message}")
//            _handler.sendMessage(Message().apply {
//                what = CCons.MSG_INSTALL_FAIL
//                obj = e.message ?: ""
//            })
//        } finally {
//            _handler.sendEmptyMessage(CCons.MSG_INSTALL_FINISH)
//        }
//    }
}