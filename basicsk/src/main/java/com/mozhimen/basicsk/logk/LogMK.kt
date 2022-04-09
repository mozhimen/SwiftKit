package com.mozhimen.basicsk.logk

import com.mozhimen.basicsk.logk.commons.ILogKPrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.logk.mos.LogKType
import com.mozhimen.basicsk.utilk.UtilKStackTrace
import java.lang.StringBuilder
import kotlin.collections.ArrayList

/**
 * @ClassName LogK
 * @Description * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 * <p>
 * Sample1:
 * LogK.log(object : LogKConfig() {
 * override fun includeThread(): Boolean {
 * return true
 * }
 * override fun stackTraceDepth(): Int {
 * return 0
 * }
 * }, LogKType.E, "-----", "5566")
 * <p></>
 * Sample2:
 * LogK.a("9900")
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:49
 * @Version 1.0
 */
object LogK {
    private var LOGK_PACKAGE: String

    init {
        val className = LogK::class.java.name
        LOGK_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1)
    }

    fun v(vararg contents: Any) {
        log(LogKType.V, *contents)
    }

    fun vt(tag: String, vararg contents: Any) {
        log(LogKType.V, tag, *contents)
    }

    fun d(vararg contents: Any) {
        log(LogKType.D, *contents)
    }

    fun dt(tag: String, vararg contents: Any) {
        log(LogKType.D, tag, *contents)
    }

    fun i(vararg contents: Any) {
        log(LogKType.I, *contents)
    }

    fun it(tag: String, vararg contents: Any) {
        log(LogKType.I, tag, *contents)
    }

    fun w(vararg contents: Any) {
        log(LogKType.W, *contents)
    }

    fun wt(tag: String, vararg contents: Any) {
        log(LogKType.W, tag, *contents)
    }

    fun e(vararg contents: Any) {
        log(LogKType.E, *contents)
    }

    fun et(tag: String, vararg contents: Any) {
        log(LogKType.E, tag, *contents)
    }

    fun a(vararg contents: Any) {
        log(LogKType.A, *contents)
    }

    fun at(tag: String, vararg contents: Any?) {
        log(LogKType.A, tag, *contents)
    }

    fun log(@LogKType.TYPE type: Int, vararg contents: Any?) {
        log(type, LogKManager.getInstance().getConfig().getGlobalTag(), contents)
    }

    fun log(@LogKType.TYPE type: Int, tag: String, vararg contents: Any?) {
        log(LogKManager.getInstance().getConfig(), type, tag, *contents)
    }

    fun log(config: LogKConfig, @LogKType.TYPE type: Int, tag: String, vararg contents: Any?) {
        if (!config.enable()) {
            return
        }
        val builder = StringBuilder()
        if (config.includeThread()) {
            val threadInfo: String = LogKConfig.THREAD_FORMATTER.format(Thread.currentThread())
            builder.append(threadInfo).append("\n")
        }
        if (config.stackTraceDepth() > 0) {
            val stackTrace: String? = LogKConfig.STACK_TRACE_FORMATTER.format(
                UtilKStackTrace.getCroppedRealStackTrack(Throwable().stackTrace, LOGK_PACKAGE, config.stackTraceDepth())
            )
            builder.append(stackTrace).append("\n")
        }
        require(contents.isNotEmpty()) { "content's size must not be 0" }
        val body = parseBody(contents, config)
        builder.append("\t").append(body)
        val printers: MutableList<ILogKPrinter> = ArrayList()
        if (config.printers() != null)
            printers.addAll(config.printers()!!)
        else
            printers.addAll(LogKManager.getInstance().getPrinters())
        if (printers.isNullOrEmpty()) return

        //打印log
        for (printer in printers) {
            printer.print(config, type, tag, builder.toString())
        }
    }

    private fun parseBody(contents: Array<out Any?>, config: LogKConfig): String {
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