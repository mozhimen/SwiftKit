package com.mozhimen.componentk.netk.file.okdownload

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptDeprecatedThirdParty
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
@AOptDeprecatedThirdParty
@Deprecated("okdownload is deprecated")
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    @OptIn(AOptLazyInit::class)
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle().apply { bindLifecycle(owner) } }

    @OptIn(AOptLazyInit::class)
    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}