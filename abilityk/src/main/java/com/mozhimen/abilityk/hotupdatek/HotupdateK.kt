package com.mozhimen.abilityk.hotupdatek

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.abilityk.hotupdatek.commons.IHotupdateKListener
import com.mozhimen.basick.elemk.receiver.bases.BaseInstallReceiver
import com.mozhimen.basick.utilk.*
import com.mozhimen.basick.utilk.context.UtilKApplication
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
class HotupdateK(owner: LifecycleOwner, private val _hotupdateKListener: IHotupdateKListener? = null) {
    companion object {
        private const val TAG = "HotUpdateK>>>>>"
    }

    private val _context = UtilKApplication.instance.get()
    private val _apkPath = _context.filesDir.absolutePath + "/hotupdatek"
    private val _apkPathWithName
        get() = _apkPath + "/hotupdatek_${UtilKDate.getNowLong()}.apk"
    private val _netKFile by lazy { NetKFile(owner) }

    suspend fun updateApk(remoteVersionCode: Int, apkUrl: String, receiver: Class<*>) {
        withContext(Dispatchers.IO) {
            //check version
            if (!isNeedUpdate(remoteVersionCode)) {
                Log.d(TAG, "updateApk: isNeedUpdate false")
                _hotupdateKListener?.onComplete()
                return@withContext
            }
            //delete all cache
            if (!deleteAllOldPkgs()) {
                Log.d(TAG, "updateApk: deleteAllOldPkgs fail")
                _hotupdateKListener?.onFail("delete all old apks fail")
                return@withContext
            }
            //download new apk
            if (!downloadApk(apkUrl)) {
                Log.d(TAG, "updateApk: downloadApk fail")
                _hotupdateKListener?.onFail("download new apk fail")
                return@withContext
            }
            //install new apk
            Log.d(TAG, "updateApk: installApk start")
            installApk(_apkPathWithName, receiver)
            _hotupdateKListener?.onComplete()
        }
    }

    /**
     * 是否需要更新
     * @param remoteVersionCode Int
     * @return Boolean
     */
    fun isNeedUpdate(remoteVersionCode: Int): Boolean =
        (UtilKPackage.getPkgVersionCode() < remoteVersionCode).also {
            Log.d(TAG, "isNeedUpdate: $it")
        }

    /**
     * 删除所有的旧包
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
        _netKFile.download().singleFileTask().start(url, _apkPathWithName, object : IFileDownloadSingleListener {
            override fun onComplete(task: DownloadTask) {
                task.file?.let {
                    coroutine.resume(true)
                } ?: coroutine.resume(false)
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
     * @param apkPath String
     */
    fun installApk(apkPath: String, receiver: Class<*>) {
        UtilKPackage.installSilence(apkPath, receiver)
    }
}