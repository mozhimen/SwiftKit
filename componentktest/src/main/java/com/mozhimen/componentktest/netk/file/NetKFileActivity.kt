package com.mozhimen.componentktest.netk.file

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.componentk.netk.file.NetKFile
import com.mozhimen.componentk.netk.file.download_deprecated.commons.IFileDownloadSingleListener
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import com.mozhimen.basick.utilk.os.UtilKPath
import com.mozhimen.componentk.netk.file.download.DOWNLOAD_ENGINE_EMBED
import com.mozhimen.componentk.netk.file.download.DownloadListener
import com.mozhimen.componentk.netk.file.download.DownloadRequest
import com.mozhimen.componentk.netk.file.download.NOTIFIER_HIDDEN
import com.mozhimen.componentktest.databinding.ActivityNetkFileBinding
import java.io.File
import kotlin.coroutines.resume

@AManifestKRequire(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET
)
@APermissionCheck(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET
)
class NetKFileActivity : BaseActivityVB<ActivityNetkFileBinding>() {
    private val _netKFile by lazy { NetKFile(this) }
    private val _musicUrl = "http://192.168.2.6/construction-sites-images/voice/20221102/176f9197f0694591b16ffd47a0f117fe.wav"
    private val _musicPath by lazy { UtilKPath.Absolute.Internal.getFilesDir() + "/netkfile/music.wav" }
    private var _downloadRequest: DownloadRequest? = null

    private val _fileDownloadSingleListener = object : IFileDownloadSingleListener {
        override fun onComplete(task: DownloadTask) {
            Log.d(TAG, "onComplete: path ${task.uri?.path}")
            Log.d(TAG, "onComplete: isFileExists ${task.uri.path?.let { UtilKFile.isFileExist(it) } ?: "null"}")
            vb.netkFileBtn1.isClickable = true
        }

        override fun onFail(task: DownloadTask, e: Exception?) {
            e?.printStackTrace()
            Log.e(TAG, "onFail fail msg: ${e?.message}")
            vb.netkFileBtn1.isClickable = true
        }
    }

    private val _downloadListener = object : DownloadListener {
        override fun onDownloadStart() {
            Log.d(TAG, "onDownloadStart")
        }

        override fun onProgressUpdate(percent: Int) {
            Log.d(TAG, "onProgressUpdate: percent $percent")
        }

        override fun onDownloadComplete(uri: Uri) {
            Log.d(TAG, "onDownloadComplete: path ${uri.path}")
            Log.d(TAG, "onDownloadComplete: isFileExists ${uri.path?.let { UtilKFile.isFileExist(it) } ?: "null"}")
            vb.netkFileBtn2.isClickable = true
        }

        override fun onDownloadFailed(e: Throwable) {
            e.printStackTrace()
            Log.e(TAG, "onDownloadFailed fail msg: ${e.message}")
            vb.netkFileBtn2.isClickable = true
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.netkFileBtn1.setOnClickListener {
            vb.netkFileBtn1.isClickable = false
            _netKFile.download().singleFileTask().start(_musicUrl, _musicPath, _fileDownloadSingleListener)
        }

        vb.netkFileBtn2.setOnClickListener {
            vb.netkFileBtn2.isClickable = false
            _downloadRequest = createCommonRequest(_musicUrl, _musicPath)
            _downloadRequest!!.registerListener(_downloadListener)
            _downloadRequest!!.startDownload()
        }
    }

    private fun createCommonRequest(url: String, destApkPathWithName: String): DownloadRequest =
        DownloadRequest(this, url, DOWNLOAD_ENGINE_EMBED)
            .setNotificationVisibility(NOTIFIER_HIDDEN)
            .setShowNotificationDisableTip(false)
            .setDestinationUri(Uri.fromFile(File(destApkPathWithName)))
}