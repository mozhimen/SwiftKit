package com.mozhimen.componentk.netk.file.mos

import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.componentk.netk.file.commons.INetKFileListener
import com.mozhimen.componentk.netk.file.cons.CDownloadCache
import com.mozhimen.componentk.netk.file.cons.CEvent
import com.mozhimen.componentk.netk.file.cons.CTaskStatus
import com.mozhimen.componentk.netk.file.helpers.NetKFileDownloadUtil
import com.mozhimen.componentk.netk.file.helpers.NetKFileHelper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel


/**
 * @ClassName DownloadTask
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 17:32
 * @Version 1.0
 */
class MDownloadTask(private val _taskInfo: MTaskInfo, listener: INetKFileListener) : Runnable {
    private var _tempFileTotalSize = 0//临时文件的总大小
    private val _eachTempSize = 16

    private var _childTaskCount = 0
    private var _filePathWithName = ""

    private var _callList: ArrayList<Call> = arrayListOf()
    private var _tempChildTaskCount = 0

    init {
        _childTaskCount = _taskInfo.childTaskCount
        _filePathWithName = _taskInfo.filePathWithName
    }

    override fun run() {
        val destFile = File(_filePathWithName)
        val tempFile = File("$_filePathWithName.temp")
        val lastTaskInfo = CDownloadCache.getLastTaskInfo(_taskInfo.url)
        if (UtilKFile.isFileExist(destFile) && UtilKFile.isFileExist(tempFile) && lastTaskInfo != null && _taskInfo.status != CTaskStatus.PROGRESSING) {
            val response = NetKFileHelper.instance.initRequest(_taskInfo.url, lastTaskInfo.lastModify)
            if (response != null && response.isSuccessful && response.code == 206) {
                _tempFileTotalSize = _eachTempSize * lastTaskInfo.childTaskCount
                postDownloadEventStart(
                    lastTaskInfo.totalSize, lastTaskInfo.currentSize, "", true
                )
            } else {
                prepareRangeFile(destFile, tempFile, response)
            }
            saveRangeFile(destFile, tempFile)
        }
    }

    private fun prepareRangeFile(destFile: File, tempFile: File, response: Response?) {
        if (response == null) return
        var saveRandomAccessFile: RandomAccessFile? = null
        var tempRandomAccessFile: RandomAccessFile? = null
        var tempChannel: FileChannel? = null
        try {
            val fileLength = response.body!!.contentLength()
            postDownloadEventStart(fileLength, 0, NetKFileDownloadUtil.getLastModify(response)!!, true)
            CDownloadCache.deleteLastTaskInfo(_taskInfo.url)
            UtilKFile.deleteFiles(destFile, tempFile)
            UtilKFile.createFile(destFile)
            UtilKFile.createFile(tempFile)

            saveRandomAccessFile = RandomAccessFile(destFile, "rws")
            saveRandomAccessFile.setLength(fileLength)
            tempRandomAccessFile = RandomAccessFile(tempFile, "rws")
            tempRandomAccessFile.setLength(_tempFileTotalSize.toLong())
            tempChannel = tempRandomAccessFile.channel

            val buffer = tempChannel.map(FileChannel.MapMode.READ_WRITE, 0, _tempFileTotalSize.toLong())
            var start: Long
            var end: Long
            val eachSize: Int = (fileLength / _childTaskCount).toInt()
            for (i in 0 until _childTaskCount) {
                if (i == _childTaskCount - 1) {
                    start = (i * eachSize).toLong()
                    end = fileLength - 1
                } else {
                    start = (i * eachSize).toLong()
                    end = ((i + 1) * eachSize - 1).toLong()
                }
                buffer.putLong(start)
                buffer.putLong(end)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            postDownloadEventFail(e)
        } finally {
            response.close()
            tempChannel?.close()
            saveRandomAccessFile?.close()
            tempRandomAccessFile?.close()
        }
    }

    private fun saveRangeFile(destFile: File, tempFile: File) {
        UtilKFile.createFile(destFile)
        UtilKFile.createFile(tempFile)
        val range: Ranges? = readDownloadRange(tempFile)
        _callList = ArrayList()
        Db.getInstance(context).updateProgress(0, 0, PROGRESS, url)
        for (i in 0 until _childTaskCount) {
            val call: Call = OkHttpManager.getInstance().initRequest(url, range.start.get(i), range.end.get(i), object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onError(e.toString())
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    startSaveRangeFile(response, i, range, saveFile, tempFile)
                }
            })
            _callList.add(call)
        }
        while (_tempChildTaskCount < _childTaskCount) {
            //由于每个文件采用多个异步操作进行，发起多个异步操作后该线程已经结束，但对应文件并未下载完成，
            //则会出现线程池中同时下载的文件数量超过设定的核心线程数，所以考虑只有当前线程的所有异步任务结束后，
            //才能使结束当前线程。
        }
    }

    private fun updateTaskInfoCache()

    /**
     * 读取保存的断点信息
     * @param tempFile File?
     * @return Ranges?
     */
    private fun readDownloadRange(tempFile: File): Ranges? {
        var record: RandomAccessFile? = null
        var channel: FileChannel? = null
        try {
            record = RandomAccessFile(tempFile, "rws")
            channel = record.channel
            val buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, _tempFileTotalSize.toLong())
            val startByteArray = LongArray(_childTaskCount)
            val endByteArray = LongArray(_childTaskCount)
            for (i in 0 until _childTaskCount) {
                startByteArray[i] = buffer.long
                endByteArray[i] = buffer.long
            }
            return Ranges(startByteArray, endByteArray)
        } catch (e: Exception) {
            e.printStackTrace()
            postDownloadEventFail(e)
        } finally {
            channel?.close()
            record?.close()
        }
        return null
    }

    private fun postDownloadEventStart(
        totalSize: Long,
        currentSize: Long,
        lastModify: String,
        isSupportRange: Boolean,
    ) {
        UtilKDataBus.with<MTaskInfo?>(CEvent.download_start).postValue(
            MTaskInfo(
                _taskInfo.url, _taskInfo.fileName, _filePathWithName,
                totalSize = totalSize, currentSize = currentSize,
                lastModify = lastModify, isSupportRange = isSupportRange
            )
        )
    }

    private fun postDownloadEventFail(
        e: Exception
    ) {
        UtilKDataBus.with<MTaskInfo?>(CEvent.download_start).postValue(
            MTaskInfo(
                _taskInfo.url, _taskInfo.fileName, _filePathWithName,
                totalSize = totalSize, currentSize = currentSize,
                lastModify = lastModify, isSupportRange = isSupportRange
            )
        )
    }
}