package com.mozhimen.abilityk.updatek

import android.net.Uri
import android.util.Log
import com.mozhimen.abilityk.updatek.commons.IUpdateKListener
import com.mozhimen.abilityk.updatek.commons.ISuspendUpdateKListener
import com.mozhimen.abilityk.updatek.cons.CUpdateKEvent
import com.mozhimen.basick.lintk.optin.OptInDeviceRoot
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.utilk.android.content.UtilKApk
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackageInfo
import com.mozhimen.basick.utilk.kotlin.getSplitLastIndexToEnd
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.getSplitLastIndexToEnd
import com.mozhimen.componentk.installk.InstallK
import com.mozhimen.componentk.netk.file.download.commons.IDownloadListener
import com.mozhimen.componentk.netk.file.download.DownloadRequest
import com.mozhimen.componentk.netk.file.download.annors.ADownloadEngine
import com.mozhimen.componentk.netk.file.download.annors.ANotificationVisibility
import com.mozhimen.underlayk.logk.LogK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.resume

/**
 * @ClassName HotUpdateK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/24 12:15
 * @Version 1.0
 */
@OptIn(OptInDeviceRoot::class)
@AManifestKRequire(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET,
    CPermission.REQUEST_INSTALL_PACKAGES,
    CPermission.INSTALL_PACKAGES,
    CPermission.READ_INSTALL_SESSIONS,
    CPermission.REPLACE_EXISTING_PACKAGE,
    CPermission.BIND_ACCESSIBILITY_SERVICE,
    CManifest.SERVICE_ACCESSIBILITY
)
class UpdateK : BaseUtilK() {

    private val _apkPath by lazy { UtilKStrPath.Absolute.Internal.getCache() + "/hotupdatek" }
    private val _installK by lazy { InstallK() }
    private var _downloadRequest: DownloadRequest? = null
    private var _hotupdateKListener: IUpdateKListener? = null
    private var _suspendHotupdateKListener: ISuspendUpdateKListener? = null
    fun getInstallK(): InstallK {
        return _installK
    }

    fun setHotupdateKListener(listener: IUpdateKListener) {
        _hotupdateKListener = listener
    }

    fun setSuspendHotupdateKListener(listener: ISuspendUpdateKListener) {
        _suspendHotupdateKListener = listener
    }

    /**
     * 更新Apk
     * @param remoteVersionCode Int
     * @param apkUrl String
     */
    suspend fun updateApk(remoteVersionCode: Int, apkUrl: String) {
        withContext(Dispatchers.IO) {
            //url valid
            if (!apkUrl.endsWith("apk")) {
                Log.e(TAG, "updateApk: url valid false")
                _hotupdateKListener?.onFail("isn't a valid apk file url")
                _suspendHotupdateKListener?.onFail("isn't a valid apk file url")
                return@withContext
            }
            //check version
            if (!isNeedUpdate(remoteVersionCode)) {
                Log.d(TAG, "updateApk: isNeedUpdate false")
                _hotupdateKListener?.onComplete()
                _suspendHotupdateKListener?.onComplete()
                return@withContext
            }
            val strPathNameApk = _apkPath + "/${getApkNameFromUrl(apkUrl)}"
            if (!isDownloadApk(strPathNameApk, remoteVersionCode)) {
                //delete all cache
                if (!deleteAllOldPkgs()) {
                    Log.e(TAG, "updateApk: deleteAllOldPkgs fail")
                    _hotupdateKListener?.onFail("delete all old apks fail")
                    _suspendHotupdateKListener?.onFail("delete all old apks fail")
                    return@withContext
                }
                //create apk file
                if (!UtilKStrFile.isFileExist(strPathNameApk)) {
                    Log.d(TAG, "updateApk: create apk file")
                    UtilKStrFile.createFile(strPathNameApk)
                }
                //download new apk
                if (!downloadApk(apkUrl, strPathNameApk)) {
                    Log.e(TAG, "updateApk: downloadApk fail")
                    deleteAllOldPkgs()
                    _hotupdateKListener?.onFail("download new apk fail")
                    _suspendHotupdateKListener?.onFail("download new apk fail")
                    return@withContext
                }
            }
            //install new apk
            Log.d(TAG, "updateApk: installApk start")
            installApk(strPathNameApk)
            _hotupdateKListener?.onComplete()
            _suspendHotupdateKListener?.onComplete()
        }
    }

    /**
     * 下载更新
     * @param url String
     */
    suspend fun downloadApk(url: String, strPathNameApk: String): Boolean = suspendCancellableCoroutine { coroutine ->
//        _netKFile.download().singleFileTask().start(url, strPathNameApk, object : IFileDownloadSingleListener {
//            override fun onComplete(task: DownloadTask) {
//                task.file?.let {
//                    coroutine.resume(true)
//                } ?: coroutine.resume(false)
//            }
//
//            override fun onProgress(task: DownloadTask, totalIndex: Int, totalBytes: Long) {
//                super.onProgress(task, totalIndex, totalBytes)
//                UtilKDataBus.with<String>(EVENT_HOTUPDATEK_PROGRESS).postValue("${totalBytes / 1024 / 1024}MB")
//            }
//
//            override fun onFail(task: DownloadTask, e: Exception?) {
//                e?.printStackTrace()
//                LogK.et(TAG, "downloadApk fail msg: ${e?.message}")
//                coroutine.resume(false)
//            }
//        })
        ///////////////////////////////////////////////////////////////////
        _downloadRequest = createCommonRequest(url, strPathNameApk)
        _downloadRequest?.registerListener(object : IDownloadListener {
            override fun onDownloadStart() {
                Log.d(TAG, "downloadApk onDownloadStart")
            }

            override fun onProgressUpdate(percent: Int) {
                Log.d(TAG, "downloadApk onProgressUpdate: percent $percent")
                PostKEventLiveData.instance.with<String>(CUpdateKEvent.HOTUPDATEK_PROGRESS).postValue("$percent")
            }

            override fun onDownloadComplete(uri: Uri) {
                Log.d(TAG, "downloadApk onDownloadComplete: path ${uri.path}")
                Log.d(TAG, "downloadApk onDownloadComplete: isFileExists ${uri.path?.let { UtilKStrFile.isFileExist(it) } ?: "null"}")
                coroutine.resume(true)
            }

            override fun onDownloadFailed(e: Throwable) {
                e.printStackTrace()
                LogK.etk(TAG, "downloadApk fail msg: ${e.message}")
                coroutine.resume(false)
            }
        })
        _downloadRequest?.startDownload()
    }

    /**
     * 安装更新
     * @param strPathNameApk String
     */
    suspend fun installApk(strPathNameApk: String) {
        withContext(Dispatchers.Main) {
            _installK.install(strPathNameApk)
            //AutoInstaller.getDefault(_context).install(strPathNameApk)
        }
    }

    /**
     * 从url获取Apk文件
     * @param apkUrl String
     * @return String
     */
    fun getApkNameFromUrl(apkUrl: String): String {
        return apkUrl.getSplitLastIndexToEnd("/").also { Log.d(TAG, "getApkNameFromUrl: $it") }
    }

    /**
     * 是否需要更新
     * @param remoteVersionCode Int
     * @return Boolean
     */
    fun isNeedUpdate(remoteVersionCode: Int): Boolean =
        (UtilKPackageInfo.getVersionCode(_context) < remoteVersionCode).also {
            Log.d(TAG, "isNeedUpdate: $it")
        }

    /**
     * 删除所有的旧包
     * @return Boolean
     */
    fun deleteAllOldPkgs(): Boolean {
        return try {
            val deleteRes = UtilKStrFile.deleteFolder(_apkPath)
            Log.d(TAG, "deleteAllOldPkgs: deleteRes $deleteRes")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            LogK.etk(TAG, "deleteAllOldPkgs: Exception ${e.message}")
            false
        }
    }

    private fun isDownloadApk(strPathNameApk: String, destVersionCode: Int): Boolean {
        return (UtilKStrFile.isFileExist(strPathNameApk) && UtilKApk.getVersionCode(strPathNameApk) != null && UtilKApk.getVersionCode(strPathNameApk)!! >= destVersionCode).also {
            Log.d(TAG, "isDownloadApk: $it")
        }
    }

    private fun createCommonRequest(url: String, strPathNameApk: String): DownloadRequest =
        DownloadRequest(_context, url, ADownloadEngine.EMBED)
            .setNotificationVisibility(ANotificationVisibility.HIDDEN)
            .setShowNotificationDisableTip(false)
            .setDestinationUri(Uri.fromFile(File(strPathNameApk)))

    //val strPathNameApk = _apkPath + "/hotupdatek_${UtilKDate.getNowLong()}.apk"
    //private val _netKFile by lazy { NetKFile(owner) }
}