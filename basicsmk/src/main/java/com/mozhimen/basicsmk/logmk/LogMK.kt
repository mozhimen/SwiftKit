package com.mozhimen.basicsmk.logmk

import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.logmk.mos.LogMKType
import com.mozhimen.basicsmk.utilmk.UtilMKStackTrace
import java.lang.StringBuilder
import kotlin.collections.ArrayList

/**
 * @ClassName LogMK
 * @Description * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 * <p>
 * Sample1:
 * LogMK.log(object : LogMKConfig() {
 * override fun includeThread(): Boolean {
 * return true
 * }
 * override fun stackTraceDepth(): Int {
 * return 0
 * }
 * }, LogMKType.E, "-----", "5566")
 * <p></>
 * Sample2:
 * LogMK.a("9900")
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:49
 * @Version 1.0
 */
object LogMK {
    private var LOG_MK_PACKAGE: String

    init {
        val className = LogMK::class.java.name
        LOG_MK_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1)
    }

    fun v(vararg contents: Any) {
        log(LogMKType.V, *contents)
    }

    fun vt(tag: String, vararg contents: Any) {
        log(LogMKType.V, tag, *contents)
    }

    fun d(vararg contents: Any) {
        log(LogMKType.D, *contents)
    }

    fun dt(tag: String, vararg contents: Any) {
        log(LogMKType.D, tag, *contents)
    }

    fun i(vararg contents: Any) {
        log(LogMKType.I, *contents)
    }

    fun it(tag: String, vararg contents: Any) {
        log(LogMKType.I, tag, *contents)
    }

    fun w(vararg contents: Any) {
        log(LogMKType.W, *contents)
    }

    fun wt(tag: String, vararg contents: Any) {
        log(LogMKType.W, tag, *contents)
    }

    fun e(vararg contents: Any) {
        log(LogMKType.E, *contents)
    }

    fun et(tag: String, vararg contents: Any) {
        log(LogMKType.E, tag, *contents)
    }

    fun a(vararg contents: Any) {
        log(LogMKType.A, *contents)
    }

    fun at(tag: String, vararg contents: Any?) {
        log(LogMKType.A, tag, *contents)
    }

    fun log(@LogMKType.TYPE type: Int, vararg contents: Any?) {
        log(type, LogMKManager.getInstance().getConfig().getGlobalTag(), contents)
    }

    fun log(@LogMKType.TYPE type: Int, tag: String, vararg contents: Any?) {
        log(LogMKManager.getInstance().getConfig(), type, tag, *contents)
    }

    fun log(config: LogMKConfig, @LogMKType.TYPE type: Int, tag: String, vararg contents: Any?) {
        if (!config.enable()) {
            return
        }
        val builder = StringBuilder()
        if (config.includeThread()) {
            val threadInfo: String = LogMKConfig.THREAD_FORMATTER.format(Thread.currentThread())
            builder.append(threadInfo).append("\n")
        }
        if (config.stackTraceDepth() > 0) {
            val stackTrace: String? = LogMKConfig.STACK_TRACE_FORMATTER.format(
                UtilMKStackTrace.getCroppedRealStackTrack(Throwable().stackTrace, LOG_MK_PACKAGE, config.stackTraceDepth())
            )
            builder.append(stackTrace).append("\n")
        }
        require(contents.isNotEmpty()) { "content's size must not be 0" }
        val body = parseBody(contents, config)
        builder.append("\t").append(body)
        val printers: MutableList<ILogMKPrinter> = ArrayList()
        if (config.printers() != null)
            printers.addAll(config.printers()!!)
        else
            printers.addAll(LogMKManager.getInstance().getPrinters())
        if (printers.isNullOrEmpty()) return

        //打印log
        for (printer in printers) {
            printer.print(config, type, tag, builder.toString())
        }
    }

    private fun parseBody(contents: Array<out Any?>, config: LogMKConfig): String {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser()!!.toJson(contents)
        }
        val builder = StringBuilder()
        for (o in contents) {
            builder.append(o.toString()).append(";")
        }
        if (builder.isNotEmpty()) {
            builder.deleteCharAt(builder.length - 1)
        }
        return builder.toString()
    }
}