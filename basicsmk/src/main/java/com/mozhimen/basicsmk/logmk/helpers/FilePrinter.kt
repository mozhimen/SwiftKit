package com.mozhimen.basicsmk.logmk.helpers

import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.logmk.mos.LogMKMo
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * @ClassName FilePrinter
 * @Description 创建FilePrinter
 * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
 * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:33
 * @Version 1.0
 */
class FilePrinter(private val logPath: String, private val retentionTime: Long) : ILogMKPrinter {

    private val EXECUTOR = Executors.newSingleThreadExecutor()
    private var writer: LogMKWriter

    @Volatile
    private var worker: PrintWorker

    companion object {
        private var instance: FilePrinter? = null

        @Synchronized
        fun getInstance(logPath: String, retentionTime: Long): FilePrinter {
            if (instance == null) {
                instance = FilePrinter(logPath, retentionTime)
            }
            return instance!!
        }
    }

    init {
        writer = LogMKWriter()
        worker = PrintWorker()
        cleanExpiredLog()
    }

    override fun print(config: LogMKConfig, level: Int, tag: String, printString: String) {
        val timeMillis = System.currentTimeMillis()
        if (!worker.isRunning()) {
            worker.start()
        }
        worker.put(LogMKMo(timeMillis, level, tag, printString))
    }

    /**
     * 清除过期log
     */
    private fun cleanExpiredLog() {
        if (retentionTime <= 0) {
            return
        }
        val currentTimeMillis = System.currentTimeMillis()
        val logDir = File(logPath)
        val files = logDir.listFiles() ?: return
        for (file in files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete()
            }
        }
    }

    private fun doPrint(logMKMo: LogMKMo) {
        val lastFileName: String? = writer.getPreFileName()
        if (lastFileName == null) {
            val newFileName: String = genFileName()
            if (writer.isReady()) {
                writer.close()
            }
            if (!writer.ready(newFileName)) {
                return
            }
        }
        writer.append(logMKMo.flattenedLog())
    }

    private fun genFileName(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(System.currentTimeMillis()))
    }

    private inner class PrintWorker : Runnable {
        private val logs: BlockingQueue<LogMKMo> = LinkedBlockingQueue()

        @Volatile
        private var running = false

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: LogMKMo) {
            try {
                logs.put(log)
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
            synchronized(this) { return running }
        }

        /**
         * 启动工作线程
         */
        fun start() {
            synchronized(this) {
                EXECUTOR.execute(this)
                running = true
            }
        }

        override fun run() {
            var log: LogMKMo?
            try {
                while (true) {
                    log = logs.take()
                    doPrint(log)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) { running = false }
            }
        }
    }

    /**
     * 基于BufferedWriter将log写入文件
     */
    private inner class LogMKWriter {
        private var preFileName: String? = null
        private var logFile: File? = null
        private var bufferedWriter: BufferedWriter? = null

        fun isReady(): Boolean {
            return bufferedWriter != null
        }

        fun getPreFileName(): String? {
            return preFileName
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        fun ready(newFileName: String): Boolean {
            preFileName = newFileName
            logFile = File(logPath, newFileName)

            // 当log文件不存在时创建log文件
            if (!logFile!!.exists()) {
                try {
                    val parent = logFile!!.parentFile
                    if (!parent.exists()) {
                        parent.mkdirs()
                    }
                    logFile!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    preFileName = null
                    logFile = null
                    return false
                }
            }

            try {
                bufferedWriter = BufferedWriter(FileWriter(logFile, true))
            } catch (e: Exception) {
                e.printStackTrace()
                preFileName = null
                logFile = null
                return false
            }
            return true
        }

        /**
         * 关闭bufferedWriter
         */
        fun close(): Boolean {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return false
                } finally {
                    bufferedWriter = null
                    preFileName = null
                    logFile = null
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
                bufferedWriter?.apply {
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