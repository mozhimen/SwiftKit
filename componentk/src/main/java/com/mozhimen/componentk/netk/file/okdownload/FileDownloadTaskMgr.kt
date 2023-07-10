package com.mozhimen.componentk.netk.file.okdownload

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptInDeprecatedThirdParty
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
@AOptInDeprecatedThirdParty
@Deprecated("okdownload is deprecated")
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle().apply { bindLifecycle(owner) } }

    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}