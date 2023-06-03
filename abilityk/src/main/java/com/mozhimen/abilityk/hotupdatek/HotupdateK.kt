package com.mozhimen.abilityk.hotupdatek

import android.net.Uri
import android.util.Log
import com.mozhimen.abilityk.hotupdatek.commons.IHotupdateKListener
import com.mozhimen.abilityk.hotupdatek.commons.ISuspendHotupdateKListener
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.utilk.app.UtilKApk
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKDataBus
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.pm.UtilKPackageInfo
import com.mozhimen.basick.utilk.java.datatype.getSplitLast
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import com.mozhimen.basick.utilk.os.UtilKPath
import com.mozhimen.componentk.installk.InstallK
import com.mozhimen.componentk.netk.file.download.DOWNLOAD_ENGINE_EMBED
import com.mozhimen.componentk.netk.file.download.DownloadListener
import com.mozhimen.componentk.netk.file.download.DownloadRequest
import com.mozhimen.componentk.netk.file.download.NOTIFIER_HIDDEN
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
class HotupdateK {
    companion object {
        private const val TAG = "HotUpdateK>>>>>"

        const val EVENT_HOTUPDATEK_PROGRESS = "hotupdatek_progress"
    }

    private val _context by lazy { UtilKApplication.instance.getContext() }
    private val _apkPath by lazy { UtilKPath.Absolute.Internal.getCacheDir() + "/hotupdatek" }
    private val _installK by lazy { InstallK() }
    private var _downloadRequest: DownloadRequest? = null
    private var _hotupdateKListener: IHotupdateKListener? = null
    private var _suspendHotupdateKListener: ISuspendHotupdateKListener? = null
    fun getInstallK(): InstallK {
        return _installK
    }

    fun setHotupdateKListener(listener: IHotupdateKListener) {
        _hotupdateKListener = listener
    }

    fun setSuspendHotupdateKListener(listener: ISuspendHotupdateKListener) {
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
            val apkPathWithName = _apkPath + "/${getApkNameFromUrl(apkUrl)}"
            if (!isDownloadApk(apkPathWithName, remoteVersionCode)) {
                //delete all cache
                if (!deleteAllOldPkgs()) {
                    Log.e(TAG, "updateApk: deleteAllOldPkgs fail")
                    _hotupdateKListener?.onFail("delete all old apks fail")
                    _suspendHotupdateKListener?.onFail("delete all old apks fail")
                    return@withContext
                }
                //create apk file
                if (!UtilKFile.isFileExist(apkPathWithName)) {
                    Log.d(TAG, "updateApk: create apk file")
                    UtilKFile.createFile(apkPathWithName)
                }
                //download new apk
                if (!downloadApk(apkUrl, apkPathWithName)) {
                    Log.e(TAG, "updateApk: downloadApk fail")
                    deleteAllOldPkgs()
                    _hotupdateKListener?.onFail("download new apk fail")
                    _suspendHotupdateKListener?.onFail("download new apk fail")
                    return@withContext
                }
            }
            //install new apk
            Log.d(TAG, "updateApk: installApk start")
            installApk(apkPathWithName)
            _hotupdateKListener?.onComplete()
            _suspendHotupdateKListener?.onComplete()
        }
    }

    /**
     * 下载更新
     * @param url String
     */
    suspend fun downloadApk(url: String, destApkPathWithName: String): Boolean = suspendCancellableCoroutine { coroutine ->
//        _netKFile.download().singleFileTask().start(url, destApkPathWithName, object : IFileDownloadSingleListener {
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
        _downloadRequest = createCommonRequest(url, destApkPathWithName)
        _downloadRequest?.registerListener(object : DownloadListener {
            override fun onDownloadStart() {
                Log.d(TAG, "downloadApk onDownloadStart")
            }

            override fun onProgressUpdate(percent: Int) {
                Log.d(TAG, "downloadApk onProgressUpdate: percent $percent")
                UtilKDataBus.with<String>(EVENT_HOTUPDATEK_PROGRESS).postValue("$percent")
            }

            override fun onDownloadComplete(uri: Uri) {
                Log.d(TAG, "downloadApk onDownloadComplete: path ${uri.path}")
                Log.d(TAG, "downloadApk onDownloadComplete: isFileExists ${uri.path?.let { UtilKFile.isFileExist(it) } ?: "null"}")
                coroutine.resume(true)
            }

            override fun onDownloadFailed(e: Throwable) {
                e.printStackTrace()
                LogK.et(TAG, "downloadApk fail msg: ${e.message}")
                coroutine.resume(false)
            }
        })
        _downloadRequest?.startDownload()
    }

    /**
     * 安装更新
     * @param apkPathWithName String
     */
    suspend fun installApk(apkPathWithName: String) {
        withContext(Dispatchers.Main) {
            _installK.install(apkPathWithName)
            //AutoInstaller.getDefault(_context).install(apkPathWithName)
        }
    }

    /**
     * 从url获取Apk文件
     * @param apkUrl String
     * @return String
     */
    fun getApkNameFromUrl(apkUrl: String): String {
        return apkUrl.getSplitLast("/").also { Log.d(TAG, "getApkNameFromUrl: $it") }
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
            val deleteRes = UtilKFile.deleteFolder(_apkPath)
            Log.d(TAG, "deleteAllOldPkgs: deleteRes $deleteRes")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            LogK.et(TAG, "deleteAllOldPkgs: Exception ${e.message}")
            false
        }
    }

    private fun isDownloadApk(apkPathWithName: String, destVersionCode: Int): Boolean {
        return (UtilKFile.isFileExist(apkPathWithName) && UtilKApk.getVersionCode(apkPathWithName) != null && UtilKApk.getVersionCode(apkPathWithName)!! >= destVersionCode).also {
            Log.d(TAG, "isDownloadApk: $it")
        }
    }

    private fun createCommonRequest(url: String, destApkPathWithName: String): DownloadRequest =
        DownloadRequest(_context, url, DOWNLOAD_ENGINE_EMBED)
            .setNotificationVisibility(NOTIFIER_HIDDEN)
            .setShowNotificationDisableTip(false)
            .setDestinationUri(Uri.fromFile(File(destApkPathWithName)))

    //val apkPathWithName = _apkPath + "/hotupdatek_${UtilKDate.getNowLong()}.apk"
    //private val _netKFile by lazy { NetKFile(owner) }
}