package com.mozhimen.underlayk.logk.temps.printer

import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import com.mozhimen.basick.utilk.log.et
import com.mozhimen.basick.utilk.os.UtilKPath
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import com.mozhimen.underlayk.logk.mos.MLogK

/**
 * @ClassName FilePrinter
 * @Description
 * 1、BlockingQueue的使用，防止频繁的创建线程；
 * 2、线程同步；
 * 3、文件操作，BufferedWriter的应用；
 * 如果是外部路径需要确保已经有外部存储的读写权限
 * @param _retentionTime log文件的有效时长，单位ms，<=0表示一直有效
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:33
 * @Version 1.0
 */
class LogKPrinterFile(
    private val _retentionTime: Long
) : ILogKPrinter {

    var logPath: String? = null
        get() {
            if (field != null) return field
            val logFullPath = UtilKPath.Absolute.External.getCacheDir() + "/logk_printer_file"
            UtilKFile.createFolder(logFullPath)
            return logFullPath.also { field = it }
        }

    private val _executors = Executors.newSingleThreadExecutor()
    private var _writer: PrinterWriter

    @Volatile
    private var _worker: PrinterWorker

    companion object {
        private var instance: LogKPrinterFile? = null

        /**
         * @param retentionDay Long? 单位天
         * @return PrinterFile
         */
        @Synchronized
        fun getInstance(
            retentionDay: Long = 0
        ): LogKPrinterFile {
            if (instance == null) {
                instance = LogKPrinterFile(retentionDay * 1000 * 60 * 60 * 24)
            }
            return instance!!
        }
    }

    init {
        _writer = PrinterWriter()
        _worker = PrinterWorker()
        cleanExpiredLog()
    }

    fun getLogFiles(): Array<File> {
        return File(logPath!!).listFiles() ?: emptyArray()
    }

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        val timeMillis = System.currentTimeMillis()
        if (!_worker.isRunning()) {
            _worker.start()
        }
        _worker.put(MLogK(timeMillis, level, tag, printString))
    }

    private fun genFileName(): String {
        return "${UtilKFile.currentHourStr2FileName()}.txt"
    }

    /**
     * 清除过期log
     */
    private fun cleanExpiredLog() {
        if (_retentionTime <= 0) {
            return
        }
        val currentTimeMillis = System.currentTimeMillis()
        val files = getLogFiles()
        for (file in files) {
            if (currentTimeMillis - file.lastModified() > _retentionTime) {
                file.delete()
            }
        }
    }

    private inner class PrinterWorker : Runnable {
        private val _logQueue: BlockingQueue<MLogK> = LinkedBlockingQueue()

        @Volatile
        private var _isRunning = false

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: MLogK) {
            try {
                _logQueue.put(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        fun isRunning(): Boolean {
            synchronized(this) {
                return _isRunning
            }
        }

        /**
         * 启动工作线程
         */
        fun start() {
            synchronized(this) {
                _executors.execute(this)
                _isRunning = true
            }
        }

        override fun run() {
            var log: MLogK?
            try {
                while (true) {
                    log = _logQueue.take()
                    doPrint(log)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                e.message?.et(TAG)
                synchronized(this) { _isRunning = false }
            }
        }

        private fun doPrint(log: MLogK) {
            val lastFileName: String? = _writer.getPreFileName()
            if (lastFileName == null) {
                val newFileName: String = genFileName()
                if (_writer.isRunning()) {
                    _writer.close()
                }
                if (!_writer.ready(newFileName)) {
                    return
                }
            }
            _writer.append(log.flattenedLog())
        }
    }

    /**
     * 基于BufferedWriter将log写入文件
     */
    private inner class PrinterWriter {
        private var _preFileName: String? = null
        private var _logFile: File? = null
        private var _bufferedWriter: BufferedWriter? = null

        fun isRunning(): Boolean {
            return _bufferedWriter != null
        }

        fun getPreFileName(): String? {
            return _preFileName
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        fun ready(newFileName: String): Boolean {
            _preFileName = newFileName
            _logFile = File(logPath, newFileName)

            // 当log文件不存在时创建log文件
            try {
                UtilKFile.createFile(_logFile!!)
                _bufferedWriter = BufferedWriter(FileWriter(_logFile, true))
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
                _preFileName = null
                _logFile = null
                return false
            }
            return true
        }

        /**
         * 关闭bufferedWriter
         */
        fun close(): Boolean {
            if (_bufferedWriter != null) {
                try {
                    _bufferedWriter!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                    return false
                } finally {
                    _bufferedWriter = null
                    _preFileName = null
                    _logFile = null
                }
            }
            return true
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        fun append(flattenedLog: String) {
            try {
                _bufferedWriter?.apply {
                    write(flattenedLog)
                    newLine()
                    flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
    }
}