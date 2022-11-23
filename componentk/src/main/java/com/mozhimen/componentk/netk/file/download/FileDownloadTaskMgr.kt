package com.mozhimen.componentk.netk.file.download

import androidx.lifecycle.LifecycleOwner

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle(owner) }

    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}