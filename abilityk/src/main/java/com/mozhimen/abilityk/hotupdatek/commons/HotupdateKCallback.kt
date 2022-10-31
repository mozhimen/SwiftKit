package com.mozhimen.abilityk.hotupdatek.commons

import com.liulishuo.okdownload.DownloadTask

/**
 * @ClassName HotUpdateKCallback
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 15:37
 * @Version 1.0
 */
class HotupdateKCallback : IHotupdateKListener {

    override fun onStart(task: DownloadTask) {}

    override fun onProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {}

    override fun onFinish(task: DownloadTask, realCause: Exception?) {}
}