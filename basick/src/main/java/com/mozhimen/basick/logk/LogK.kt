package com.mozhimen.basick.logk

import com.mozhimen.basick.logk.commons.IPrinter
import com.mozhimen.basick.logk.mos.LogKConfig
import com.mozhimen.basick.logk.mos.LogKType
import com.mozhimen.basick.utilk.UtilKStackTrace
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

    /**
     * v
     * @param contents Array<out Any>
     */
    fun v(vararg contents: Any) {
        log(LogKType.V, *contents)
    }

    /**
     * vt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun vt(tag: String, vararg contents: Any) {
        log(LogKType.V, tag, *contents)
    }

    /**
     * d
     * @param contents Array<out Any>
     */
    fun d(vararg contents: Any) {
        log(LogKType.D, *contents)
    }

    /**
     * dt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun dt(tag: String, vararg contents: Any) {
        log(LogKType.D, tag, *contents)
    }

    /**
     * i
     * @param contents Array<out Any>
     */
    fun i(vararg contents: Any) {
        log(LogKType.I, *contents)
    }

    /**
     * it
     * @param tag String
     * @param contents Array<out Any>
     */
    fun it(tag: String, vararg contents: Any) {
        log(LogKType.I, tag, *contents)
    }

    /**
     * w
     * @param contents Array<out Any>
     */
    fun w(vararg contents: Any) {
        log(LogKType.W, *contents)
    }

    /**
     * wt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun wt(tag: String, vararg contents: Any) {
        log(LogKType.W, tag, *contents)
    }

    /**
     * e
     * @param contents Array<out Any>
     */
    fun e(vararg contents: Any) {
        log(LogKType.E, *contents)
    }

    /**
     * et
     * @param tag String
     * @param contents Array<out Any>
     */
    fun et(tag: String, vararg contents: Any) {
        log(LogKType.E, tag, *contents)
    }

    /**
     * a
     * @param contents Array<out Any>
     */
    fun a(vararg contents: Any) {
        log(LogKType.A, *contents)
    }

    /**
     * at
     * @param tag String
     * @param contents Array<out Any>
     */
    fun at(tag: String, vararg contents: Any) {
        log(LogKType.A, tag, *contents)
    }

    /**
     * log
     * @param type Int
     * @param contents Array<out Any>
     */
    fun log(@LogKType._TYPE type: Int, vararg contents: Any) {
        log(type, LogKMgr.instance.getConfig().getGlobalTag(), contents)
    }

    /**
     * log
     * @param type Int
     * @param tag String
     * @param contents Array<out Any>
     */
    fun log(@LogKType._TYPE type: Int, tag: String, vararg contents: Any) {
        log(LogKMgr.instance.getConfig(), type, tag, *contents)
    }

    /**
     * log实现
     * @param config LogKConfig
     * @param type Int
     * @param tag String
     * @param contents Array<out Any?>
     */
    fun log(config: LogKConfig, @LogKType._TYPE type: Int, tag: String, vararg contents: Any?) {
        if (!config.enable()) {
            return
        }
        val builder = StringBuilder()
        if (config.includeThread()) {
            val threadInfo: String = LogKConfig.formatterThread.format(Thread.currentThread())
            builder.append(threadInfo).append("\n")
        }
        if (config.stackTraceDepth() > 0 || (config.stackTraceDepth() <= 0 && type >= LogKType.E)) {
            val stackTrace: String? = LogKConfig.formatterStackTrace.format(
                UtilKStackTrace.getCroppedRealStackTrack(
                    Throwable().stackTrace,
                    LOGK_PACKAGE,
                    if (config.stackTraceDepth() <= 0) 5 else config.stackTraceDepth()
                )
            )
            builder.append(stackTrace).append("\n")
        }
        require(contents.isNotEmpty()) { "content's size must not be 0" }
        val body = parseBody(contents, config)
        builder.append(body)
        val printers: MutableList<IPrinter> = ArrayList()
        if (config.printers() != null)
            printers.addAll(config.printers()!!)
        else
            printers.addAll(LogKMgr.instance.getPrinters())
        if (printers.isEmpty()) return

        //打印log
        for (printer in printers) {
            printer.print(config, type, tag, builder.toString().replace("[", "").replace("]", ""))
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