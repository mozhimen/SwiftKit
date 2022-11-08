package com.mozhimen.abilityk.hotupdatek

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.abilityk.hotupdatek.commons.IHotupdateKListener
import com.mozhimen.basick.prefabk.receiver.PrefabKReceiverInstall
import com.mozhimen.basick.utilk.UtilKDate
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKPackage
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
        private val TAG = "HotUpdateK>>>>>"
    }

    private val _context = UtilKGlobal.instance.getApp()!!
    private val _installParentDirectory = _context.filesDir.absolutePath + "/hotupdatek"
    private val _installDirectory
        get() = _installParentDirectory + "/hotupdatek_${UtilKDate.getNowTime()}.apk"
    private val _netKFile by lazy { NetKFile(owner) }

    suspend fun updateApk(nowVersionCode: Int, apkUrl: String, receiver: Class<PrefabKReceiverInstall>) {
        withContext(Dispatchers.IO) {
            //check version
            if (!isNeedUpdate(nowVersionCode)) {
                _hotupdateKListener?.onComplete()
                return@withContext
            }
            //delete all cache
            if (!deleteAllOldPkgs()) {
                _hotupdateKListener?.onFail("delete all old pkgs fail")
                return@withContext
            }
            //download new apk
            if (!downloadApk(apkUrl)) {
                _hotupdateKListener?.onFail("download new pkg fail")
                return@withContext
            }
            //install new apk
            installApk(_installDirectory, receiver)
        }
    }

    /**
     * 是否需要更新
     * @param nowVersionCode Int
     * @return Boolean
     */
    fun isNeedUpdate(nowVersionCode: Int): Boolean =
        (UtilKPackage.getPkgVersionCode() < nowVersionCode).also {
            Log.d(TAG, "isNeedUpdate: $it")
        }

    /**
     * 删除所有的旧包
     */
    fun deleteAllOldPkgs(): Boolean {
        return try {
            val deleteRes = UtilKFile.deleteFolder(_installParentDirectory)
            Log.d(TAG, "deleteAllOldPkgs: deleteRes $deleteRes")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            LogK.et(TAG, "updateApk: Exception ${e.message}")
            false
        }
    }

    /**
     * 下载更新
     * @param url String
     */
    suspend fun downloadApk(url: String): Boolean = suspendCancellableCoroutine { coroutine ->
        _netKFile.download().singleFileTask().start(url, _installDirectory, object : IFileDownloadSingleListener {
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
     * @param receiver Class<PrefabKReceiverInstall>
     */
    fun installApk(apkPath: String, receiver: Class<PrefabKReceiverInstall>) {
        UtilKPackage.installSilence(apkPath, receiver)
    }
}