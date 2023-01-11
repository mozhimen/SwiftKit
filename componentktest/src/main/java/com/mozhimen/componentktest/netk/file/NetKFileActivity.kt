package com.mozhimen.componentktest.netk.file

import android.os.Bundle
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.componentk.audiok.AudioK
import com.mozhimen.componentk.audiok.mos.MAudioK
import com.mozhimen.componentk.netk.file.NetKFile
import com.mozhimen.componentk.netk.file.download.commons.IFileDownloadSingleListener
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionKCheck
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.componentktest.databinding.ActivityNetkFileBinding

@APermissionKRequire(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET
)
@APermissionKCheck(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET
)
class NetKFileActivity : BaseActivityVB<ActivityNetkFileBinding>() {
    private val _netKFile by lazy { NetKFile(this) }
    private val _musicUrl = "http://192.168.2.6/construction-sites-images/voice/20221102/176f9197f0694591b16ffd47a0f117fe.wav"
    private val _musicPath by lazy { this.filesDir.absolutePath + "/netkfile/" + "music.wav" }
    private val _fileDownloadSingleListener = object : IFileDownloadSingleListener {
        override fun onComplete(task: DownloadTask) {
            task.file?.let {
                AudioK.instance.addAudioToPlayList(MAudioK("0001", it.absolutePath, 0))
            }
        }

        override fun onFail(task: DownloadTask, e: Exception?) {

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                _netKFile.download().singleFileTask().start(_musicUrl, _musicPath, _fileDownloadSingleListener)
                super.initData(savedInstanceState)
            }
        }
    }
}