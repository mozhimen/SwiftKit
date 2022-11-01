package com.mozhimen.componentk.netk.file.commons

import com.mozhimen.componentk.netk.file.mos.MTaskInfo
import java.lang.Exception


/**
 * @ClassName INetKFileListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 14:14
 * @Version 1.0
 */
interface INetKFileListener {
    fun onStart(task: MTaskInfo)
    fun onProgress(task: MTaskInfo, progress: Float, currentIndex: Long, totalSize: Long) {}
    fun onPause(task: MTaskInfo) {}
    fun onCancel(task: MTaskInfo) {}
    fun onWait(task: MTaskInfo) {}
    fun onComplete(task: MTaskInfo, savePath: String)
    fun onFail(task: MTaskInfo, exception: Exception?)
}