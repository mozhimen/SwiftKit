package com.mozhimen.componentk.netk.file.helpers

import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKNet
import com.mozhimen.componentk.netk.file.commons.INetKFileListener
import com.mozhimen.componentk.netk.file.mos.MDownloadTask
import com.mozhimen.componentk.netk.file.mos.MTaskInfo
import com.mozhimen.underlayk.logk.LogK
import java.io.File
import java.util.concurrent.ConcurrentHashMap


/**
 * @ClassName NetKFileProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 16:19
 * @Version 1.0
 */
class NetKFileDownload {
    private val TAG = "NetKFileDownload>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!

//    private val _progressHandlerMap: Map<String, DownloadProgressHandler> = HashMap<String, DownloadProgressHandler>() //保存任务的进度处理对象
//    private val _downloadDataMap: Map<String, DownloadData> = HashMap<String, DownloadData>() //保存任务数据
//    private val _callbackMap: Map<String, DownloadCallback> = HashMap<String, DownloadCallback>() //保存任务回调
//    private val _fileTaskMap: Map<String, FileTask> = HashMap<String, FileTask>() //保存下载线程

    private val _netKFileListeners = ConcurrentHashMap<Int, INetKFileListener>()

    fun start(url: String, filePathWithName: String, listener: INetKFileListener) {
        start(url, File(filePathWithName), listener)
    }

    fun start(url: String, file: File, listener: INetKFileListener) {
        if (!UtilKNet.isUrlAvailable(url)) {
            LogK.et(TAG, "download: unuseful url")
            return
        }
        executeDownloadTask(MTaskInfo(url, file), listener)
    }

    private fun executeDownloadTask(taskInfo: MTaskInfo, listener: INetKFileListener) {
        val taskId = taskInfo.url.hashCode()
        if (_netKFileListeners.containsKey(taskId)) return


        _netKFileListeners[taskId] = listener
    }
}