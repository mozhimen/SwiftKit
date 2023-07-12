package com.mozhimen.componentk.netk.file.okdownload

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeprecated_ThirdParty
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiCall_BindLifecycle

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
@ALintKOptIn_ApiDeprecated_ThirdParty
@Deprecated("okdownload is deprecated")
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    @OptIn(ALintKOptIn_ApiCall_BindLifecycle::class, ALintKOptIn_ApiInit_ByLazy::class)
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle().apply { bindLifecycle(owner) } }

    @OptIn(ALintKOptIn_ApiCall_BindLifecycle::class, ALintKOptIn_ApiInit_ByLazy::class)
    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}