package com.mozhimen.componentk.netk.file

import com.mozhimen.componentk.netk.file.helpers.NetKFileDownload
import java.io.File


/**
 * @ClassName NetKFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 14:12
 * @Version 1.0
 */
class NetKFile {
    private val TAG = "NetKFile>>>>>"

    companion object {
        @JvmStatic
        val instance = NetKFileDownloadProvider.holder
    }

    private object NetKFileDownloadProvider {
        val holder = NetKFileDownload()
    }

    private val _netKFileDownload: NetKFileDownload by lazy { NetKFileDownload() }

    fun download(): NetKFileDownload {
        return _netKFileDownload
    }

//    object Download {
//        @JvmStatic
//        fun start(url: String, filePath: String, fileName: String, threadCount: Int): DownloadManger {
//            val downloadManger: DownloadManger = DownloadManger.getInstance(context)
//            downloadManger.init(url, path, name, childTaskCount)
//            return downloadManger
//        }
//
//        @JvmStatic
//        fun start(url: String, file: File, threadCount: Int): DownloadManger {
//            val downloadManger: DownloadManger = DownloadManger.getInstance(context)
//            downloadManger.init(url, path, name, childTaskCount)
//            return downloadManger
//        }
//    }
    /*private var _downloadTasks = ConcurrentHashMap<Int, DownloadTask>()
    private var _netKFileListeners = ConcurrentHashMap<Int, INetKFileListener>()
    private val _downloadListener = object : DownloadListener2() {
        override fun taskStart(task: DownloadTask) {
            Log.d(TAG, "taskStart...")
            Log.d(TAG, "taskStart: task Info ${getTaskInfo(task)}")
            task.file?.let {
                _netKFileListeners[(task.url + it.absolutePath).hashCode()]?.onStart(task)
            }
        }

        override fun fetchProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {
            super.fetchProgress(task, blockIndex, increaseBytes)
            Log.d(TAG, "fetchProgress: blockIndex $blockIndex")
            task.file?.let {
                _netKFileListeners[(task.url + it.absolutePath).hashCode()]?.onProgress(task, blockIndex, increaseBytes)
            }
        }

        override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
            Log.d(TAG, "taskEnd...")
            when (cause) {
                EndCause.COMPLETED -> {
                    val savePath: String? = task.file?.absolutePath
                    savePath?.let {
                        Log.d(TAG, "taskEnd: success")
                        task.file?.let {
                            _netKFileListeners[(task.url + it.absolutePath).hashCode()]?.onSuccess(task, savePath)
                        }
                    } ?: kotlin.run {
                        LogK.et(TAG, "taskEnd: fail get file path fail")
                        task.file?.let {
                            _netKFileListeners[(task.url + it.absolutePath).hashCode()]?.onFail(task, Exception("get file path fail"))
                        }
                    }
                }
                else -> {
                    LogK.et(TAG, "taskEnd: error ${cause.name} realCause ${realCause?.message}")
                    realCause?.printStackTrace()
                    task.file?.let {
                        _netKFileListeners[(task.url + it.absolutePath).hashCode()]?.onFail(task, realCause)
                    }
                }
            }
        }
    }

    fun download(url: String, file: File, listener: INetKFileListener? = null) {
        if (!UtilKNet.isUrlAvailable(url)) {
            LogK.et(TAG, "download: unuseful url")
            return
        }
        val id = (url + file.absolutePath).hashCode()
        if (!_downloadTasks.containsKey(id)) {
            _downloadTasks[id] = DownloadTask.Builder(url, file).build()
            listener?.let { _netKFileListeners[id] = it }
        }
        _downloadTasks[id]!!.enqueue(_downloadListener)
    }

    private fun getTaskInfo(task: DownloadTask): String {
        val stringBuilder = StringBuilder()
        stringBuilder.apply {
            append("priority ${task.priority}").append(" ")
            append("info?.filename ${task.info?.filename}").append(" ")
            append("info?.url ${task.info?.url}").append(" ")
            append("info?.file?.absolutePath ${task.info?.file?.absolutePath}")
        }
        return stringBuilder.toString()
    }*/
}