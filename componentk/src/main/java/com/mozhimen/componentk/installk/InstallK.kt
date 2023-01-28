package com.mozhimen.componentk.installk

import android.os.*
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.app.UtilKApp
import com.mozhimen.basick.utilk.app.UtilKAppInstall
import com.mozhimen.basick.utilk.app.UtilKAppRoot
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.file.UtilKFile
import com.mozhimen.componentk.installk.commons.IInstallStateChangedListener
import com.mozhimen.componentk.installk.cons.CCons
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
class InstallK {

    companion object {
        private const val TAG = "InstallK>>>>>"
    }

    private val _context = UtilKApplication.instance.get()

    //private var _tempApkPathWithName = "${_context.filesDir.absolutePath}/installk/update.apk"
    private var _installMode = EInstallMode.AUTO
    private var _installStateChangeListener: IInstallStateChangedListener? = null
    private var _smartServiceClazz: Class<*>? = null
    private var _silenceReceiverClazz: Class<*>? = null
    private val _handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                CCons.MSG_DOWNLOAD_START -> {
                    _installStateChangeListener?.onDownloadStart()
                }
                CCons.MSG_INSTALL_START -> {
                    _installStateChangeListener?.onInstallStart()
                }
                CCons.MSG_INSTALL_FINISH -> {
                    _installStateChangeListener?.onInstallFinish()
                }
                CCons.MSG_INSTALL_FAIL -> {
                    _installStateChangeListener?.onInstallFail(msg.obj as String)
                }
                CCons.MSG_NEED_PERMISSION -> {
                    _installStateChangeListener?.onNeedPermissions(msg.obj as EPermissionType)
                }
            }
        }
    }

    /**
     * 设置安装
     * @param receiverClazz Class<*>
     * @return InstallK
     */
    fun setInstallSilenceReceiver(receiverClazz: Class<*>): InstallK {
        _silenceReceiverClazz = receiverClazz
        return this
    }

    /**
     * 设置监听器
     * @param listener IInstallStateChangedListener
     */
    fun setInstallStateChangeListener(listener: IInstallStateChangedListener): InstallK {
        _installStateChangeListener = listener
        return this
    }

    /**
     * 设置安装模式
     * @param mode EInstallMode
     * @return InstallK
     */
    fun setInstallMode(mode: EInstallMode): InstallK {
        _installMode = mode
        return this
    }

    /**
     * 设置智能安装服务
     * @param serviceClazz Class<*>
     * @return InstallK
     */
    fun setInstallSmartService(serviceClazz: Class<*>): InstallK {
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
                _handler.sendEmptyMessage(CCons.MSG_INSTALL_START)
                installByMode(apkPathWithName)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "install: ${e.message}")
                _handler.sendMessage(Message().apply {
                    what = CCons.MSG_INSTALL_FAIL
                    obj = e.message ?: ""
                })
            } finally {
                _handler.sendEmptyMessage(CCons.MSG_INSTALL_FINISH)
            }
        }
    }

    @Throws(Exception::class)
    private fun installByMode(apkPathWithName: String) {
        require(apkPathWithName.isNotEmpty() && apkPathWithName.endsWith(".apk")) { "$TAG $apkPathWithName not a correct apk file path" }
        require(UtilKFile.isFileExist(apkPathWithName)) { "$TAG $apkPathWithName is not exist" }
        if (!ManifestKPermission.checkPermissions(CCons.PERMISSIONS)) {
            Log.w(TAG, "installByMode: onNeedPermissions PERMISSIONS")
            _handler.sendMessage(Message().apply {
                what = CCons.MSG_NEED_PERMISSION
                obj = EPermissionType.COMMON
            })
            return
        }
        if (_context.applicationInfo.targetSdkVersion >= CVersionCode.V_26_8_O && Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O && !UtilKAppInstall.isAppInstallsPermissionEnable(_context)) {        // 允许安装应用
            Log.w(TAG, "installByMode: onNeedPermissions isAppInstallsPermissionEnable false")
            _handler.sendMessage(Message().apply {
                what = CCons.MSG_NEED_PERMISSION
                obj = EPermissionType.INSTALL
            })
            return
        }

        when (_installMode) {
            EInstallMode.AUTO -> {
                //try install root
                if (UtilKAppRoot.isRoot() && UtilKAppInstall.installRoot(apkPathWithName)) {
                    Log.d(TAG, "installByMode: AUTO as ROOT success")
                    return
                }
                //try install silence
                if (_silenceReceiverClazz != null && (UtilKAppRoot.isRoot() || !UtilKApp.isUserApp())) {
                    UtilKAppInstall.installSilence(apkPathWithName, _silenceReceiverClazz!!)
                    Log.d(TAG, "installByMode: AUTO as SILENCE success")
                    return
                }
                //try install smart
                if (_smartServiceClazz != null && UtilKPermission.isAccessibilityPermissionEnable(_context, _smartServiceClazz!!)) {
                    UtilKAppInstall.installHand(apkPathWithName)
                    Log.d(TAG, "installByMode: AUTO as SMART success")
                    return
                }
                //try install hand
                UtilKAppInstall.installHand(apkPathWithName)
            }
            EInstallMode.ROOT -> {
                require(UtilKAppRoot.isRoot()) { "$TAG this device has not root" }
                UtilKAppInstall.installRoot(apkPathWithName)
                Log.d(TAG, "installByMode: ROOT success")
            }
            EInstallMode.SILENCE -> {
                requireNotNull(_silenceReceiverClazz) { "$TAG silence receiver must not be null" }
                require(UtilKAppRoot.isRoot() || !UtilKApp.isUserApp()) { "$TAG this device has not root or its system app" }
                UtilKAppInstall.installSilence(apkPathWithName, _silenceReceiverClazz!!)
                Log.d(TAG, "installByMode: ROOT success")
            }
            EInstallMode.SMART -> {
                requireNotNull(_smartServiceClazz) { "$TAG smart service must not be null" }
                if (!UtilKPermission.isAccessibilityPermissionEnable(_context, _smartServiceClazz!!)) {
                    Log.w(TAG, "installByMode: SMART isAccessibilityPermissionEnable false")
                    _handler.sendMessage(Message().apply {
                        what = CCons.MSG_NEED_PERMISSION
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