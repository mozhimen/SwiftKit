package com.mozhimen.basick.logk.printers

import com.mozhimen.basick.logk.commons.IPrinter
import com.mozhimen.basick.logk.mos.LogKConfig
import com.mozhimen.basick.logk.mos.LogKMo
import com.mozhimen.basick.utilk.UtilKDate
import com.mozhimen.basick.utilk.UtilKGlobal
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

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
class PrinterFile(
    private val _retentionTime: Long
) : IPrinter {

    private val _logPath: String = getLogDir().absolutePath
    private val _executors = Executors.newSingleThreadExecutor()
    private var _writer: PrinterWriter

    @Volatile
    private var _worker: PrinterWorker

    companion object {
        private var instance: PrinterFile? = null
        const val LOGK_DIR = "logk"

        /**
         * @param retentionDay Long? 单位天
         * @return PrinterFile
         */
        @Synchronized
        fun getInstance(
            retentionDay: Long = 0
        ): PrinterFile {
            if (instance == null) {
                instance = PrinterFile(retentionDay * 1000 * 60 * 60 * 24)
            }
            return instance!!
        }
    }

    init {
        _writer = PrinterWriter()
        _worker = PrinterWorker()
        cleanExpiredLog()
    }

    fun getLogDir(): File {
        val logFolder = File(UtilKGlobal.instance.getApp()!!.cacheDir, LOGK_DIR)
        if (!logFolder.exists()) {
            logFolder.mkdirs()
        }
        return logFolder
    }

    fun getLogFiles(): Array<File> {
        return File(_logPath).listFiles() ?: emptyArray()
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        val timeMillis = System.currentTimeMillis()
        if (!_worker.isRunning()) {
            _worker.start()
        }
        _worker.put(LogKMo(timeMillis, level, tag, printString))
    }

    override fun getName(): String = this.javaClass.simpleName

    private fun genFileName(): String {
        val sdf = UtilKDate.getSdf(UtilKDate.FORMAT_yyyyMMdd)
        sdf.timeZone = TimeZone.getDefault()
        return "logk-" + sdf.format(Date(System.currentTimeMillis())) + ".txt"
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
        private val _logQueue: BlockingQueue<LogKMo> = LinkedBlockingQueue()

        @Volatile
        private var _isRunning = false

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: LogKMo) {
            try {
                _logQueue.put(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
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
            var log: LogKMo?
            try {
                while (true) {
                    log = _logQueue.take()
                    doPrint(log)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) { _isRunning = false }
            }
        }

        private fun doPrint(logKMo: LogKMo) {
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
            _writer.append(logKMo.flattenedLog())
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
            _logFile = File(_logPath, newFileName)

            // 当log文件不存在时创建log文件
            if (!_logFile!!.exists()) {
                try {
                    val fileParent = _logFile!!.parentFile
                    if (!fileParent.exists()) {
                        fileParent.mkdirs()
                    }
                    _logFile!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    _preFileName = null
                    _logFile = null
                    return false
                }
            }

            try {
                _bufferedWriter = BufferedWriter(FileWriter(_logFile, true))
            } catch (e: Exception) {
                e.printStackTrace()
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
            }
        }
    }
}