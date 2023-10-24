package com.mozhimen.underlayk.logk.temps.printer

import com.mozhimen.basick.elemk.java.util.cons.EDateType
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.getFileCreateTime
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.createFolder
import com.mozhimen.basick.utilk.kotlin.getFolderFiles
import com.mozhimen.underlayk.logk.bases.BaseLogKRecord
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
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
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:33
 * @Version 1.0
 */
open class LogKPrinterFile() : ILogKPrinter, BaseUtilK() {

    var logPath: String? = null
        get() {
            if (field != null) return field
            val logFullPath = UtilKStrPath.Absolute.External.getCacheDir() + "/logk_printer_file"
            logFullPath.createFolder()
            return logFullPath.also { field = it }
        }

    private var _retentionTime: Long = -1
    private var _createLogFileDateType: EDateType = EDateType.DAY
    private val _executors by lazy { Executors.newSingleThreadExecutor() }
    protected var _printerWorker: PrinterWorker
    private var _printerWriter: PrinterWriter

    /**
     * retentionMillis log文件的有效时长，单位ms，<=0表示一直有效
     */
    constructor(retentionMillis: Long) : this() {
        _retentionTime = retentionMillis
    }

    /**
     * retentionDay log文件的有效时长，单位天，<=0表示一直有效
     */
    constructor(retentionDay: Int) : this(retentionDay * 1000L * 60L * 60L * 24L)

    init {
        _printerWriter = PrinterWriter()
        _printerWorker = PrinterWorker()
        cleanExpiredLog()
    }

    fun setCreateLogFileDateType(type: EDateType) {
        _createLogFileDateType = type
    }

    fun getLogFiles(): Array<File> =
            logPath!!.getFolderFiles()

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        val currentTimeMillis = System.currentTimeMillis()
        if (!_printerWorker.isRunning())
            _printerWorker.start()

        _printerWorker.put(BaseLogKRecord(currentTimeMillis, priority, tag, msg))
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    private fun getLogFileName(): String =
            when (_createLogFileDateType) {
                EDateType.HOUR -> "${UtilKFile.getStrFileNameForStrCurrentHour()}.txt"
                else -> "${UtilKFile.getStrFileNameForStrToday()}.txt"
            }

    /**
     * 清除过期log
     */
    private fun cleanExpiredLog() {
        if (_retentionTime <= 0)
            return

        val currentTimeMillis = System.currentTimeMillis()
        val files = getLogFiles()
        for (file in files) {
            if (currentTimeMillis - file.getFileCreateTime() > _retentionTime)
                file.delete()
        }
    }

    protected inner class PrinterWorker : Runnable {
        private val _logQueue: BlockingQueue<BaseLogKRecord> = LinkedBlockingQueue()

        @Volatile
        private var _isRunning = false

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: BaseLogKRecord) {
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
            var log: BaseLogKRecord?
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

        private fun doPrint(log: BaseLogKRecord) {
            val lastFileName: String? = _printerWriter.getPreFileName()
            if (lastFileName == null) {
                val newFileName: String = getLogFileName()
                if (_printerWriter.isRunning())
                    _printerWriter.close()

                if (!_printerWriter.ready(newFileName))
                    return
            }
            _printerWriter.append(log.flattenedLog())
        }
    }

    /**
     * 基于BufferedWriter将log写入文件
     */
    private inner class PrinterWriter {
        private var _preFileName: String? = null
        private var _logFile: File? = null
        private var _bufferedWriter: BufferedWriter? = null

        fun isRunning(): Boolean =
                _bufferedWriter != null

        fun getPreFileName(): String? =
                _preFileName

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