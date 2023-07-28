package com.mozhimen.componentk.netk.file.okdownload

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiDeprecated_ThirdParty
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle

/**
 * @ClassName NetKFileDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:52
 * @Version 1.0
 */
@OptInApiDeprecated_ThirdParty
@Deprecated("okdownload is deprecated")
class FileDownloadTaskMgr(owner: LifecycleOwner) {
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    private val _taskFileDownloadSingle by lazy { TaskFileDownloadSingle().apply { bindLifecycle(owner) } }

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    fun singleFileTask(): TaskFileDownloadSingle =
        _taskFileDownloadSingle
}