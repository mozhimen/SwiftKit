package com.mozhimen.componentk.netk.file.download_deprecated

import androidx.lifecycle.LifecycleOwner

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
@Deprecated("okdownload is deprecated")
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle().apply { bindLifecycle(owner) } }

    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}