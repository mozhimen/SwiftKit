package com.mozhimen.abilityk.hotupdatek

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.abilityk.hotupdatek.commons.IHotupdateKListener
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKDataBus
import com.mozhimen.basick.utilk.device.UtilKDate
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.pm.UtilKPackageInfo
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import com.mozhimen.componentk.installk.InstallK
import com.mozhimen.componentk.netk.file.NetKFile
import com.mozhimen.componentk.netk.file.download.commons.IFileDownloadSingleListener
import com.mozhimen.underlayk.logk.LogK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
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
class HotupdateK(owner: LifecycleOwner, private val _hotupdateKListener: IHotupdateKListener? = null) {
    companion object {
        private const val TAG = "HotUpdateK>>>>>"

        const val EVENT_HOTUPDATEK_PROGRESS = "hotupdatek_progress"
    }

    private val _context by lazy { UtilKApplication.instance.get() }
    private val _apkPath by lazy { UtilKContext.getFilesAbsolutePath(_context) + "/hotupdatek" }
    val apkPathWithName = _apkPath + "/hotupdatek_${UtilKDate.getNowLong()}.apk"
    private val _netKFile by lazy { NetKFile(owner) }
    private val _installK by lazy { InstallK() }

    suspend fun updateApk(remoteVersionCode: Int, apkUrl: String) {
        withContext(Dispatchers.IO) {
            //url valid
            if (!apkUrl.endsWith("apk")) {
                Log.e(TAG, "updateApk: url valid false")
                _hotupdateKListener?.onFail("isn't a valid apk file url")
                return@withContext
            }
            //check version
            if (!isNeedUpdate(remoteVersionCode)) {
                Log.d(TAG, "updateApk: isNeedUpdate false")
                _hotupdateKListener?.onComplete()
                return@withContext
            }
            //delete all cache
            if (!deleteAllOldPkgs()) {
                Log.e(TAG, "updateApk: deleteAllOldPkgs fail")
                _hotupdateKListener?.onFail("delete all old apks fail")
                return@withContext
            }
            //download new apk
            if (!downloadApk(apkUrl)) {
                Log.e(TAG, "updateApk: downloadApk fail")
                _hotupdateKListener?.onFail("download new apk fail")
                return@withContext
            }
            //install new apk
            Log.d(TAG, "updateApk: installApk start")
            installApk(apkPathWithName)
            _hotupdateKListener?.onComplete()
        }
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

    /**
     * 下载更新
     * @param url String
     */
    suspend fun downloadApk(url: String): Boolean = suspendCancellableCoroutine { coroutine ->
        _netKFile.download().singleFileTask().start(url, apkPathWithName, object : IFileDownloadSingleListener {
            override fun onComplete(task: DownloadTask) {
                task.file?.let {
                    coroutine.resume(true)
                } ?: coroutine.resume(false)
            }

            override fun onProgress(task: DownloadTask, totalIndex: Int, totalBytes: Long) {
                super.onProgress(task, totalIndex, totalBytes)
                UtilKDataBus.with<String>(EVENT_HOTUPDATEK_PROGRESS).postValue("${totalIndex}% ${totalBytes / 1024 / 1024}MB")
            }

            override fun onFail(task: DownloadTask, e: Exception?) {
                e?.printStackTrace()
                LogK.et(TAG, "downloadApk fail msg: ${e?.message}")
                coroutine.resume(false)
            }
        })
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
}