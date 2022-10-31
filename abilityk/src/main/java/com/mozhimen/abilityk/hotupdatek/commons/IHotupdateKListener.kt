package com.mozhimen.abilityk.hotupdatek.commons

import com.liulishuo.okdownload.DownloadTask

/**
 * @ClassName IHotUpdateKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 15:35
 * @Version 1.0
 */
interface IHotupdateKListener {
    fun onStart(task: DownloadTask)
    fun onProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long)
    fun onFinish(task: DownloadTask, realCause: Exception?)
}