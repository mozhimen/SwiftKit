package com.mozhimen.componentk.netk.app.download.helpers

import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnCache
import com.liulishuo.okdownload.core.listener.DownloadListener2
import java.util.Queue

/**
 * @ClassName AppDownloadParallelQueue
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/4 18:11
 * @Version 1.0
 */

class AppDownloadParallelQueue internal constructor(listener: DownloadListener?, taskList: ArrayList<DownloadTask>) : DownloadListener2(), Runnable {
    companion object {
        val ID_INVALID = BreakpointStoreOnCache.FIRST_ID - 1
    }

    //////////////////////////////////////////////////////////////////////////


    val waitQueue = Queue
}

