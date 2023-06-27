package com.mozhimen.underlayk.logk

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.basick.utilk.java.util.UtilKStackTrace
import com.mozhimen.underlayk.logk.annors.ALogKType
import com.mozhimen.basick.elemk.cons.CLogType
import com.mozhimen.underlayk.logk.cons.CLogKParameter
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
 * }, CLogKType.E, "-----", "5566")
 * <p></>
 * Sample2:
 * LogK.a("9900")
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:49
 * @Version 1.0
 */
fun String.kv() {
    LogK.v(this)
}

fun String.kvt(tag: String) {
    LogK.vt(tag, this)
}

fun String.kd() {
    LogK.d(this)
}

fun String.kdt(tag: String) {
    LogK.dt(tag, this)
}

fun String.ki() {
    LogK.i(this)
}

fun String.kit(tag: String) {
    LogK.it(tag, this)
}

fun String.kw() {
    LogK.w(this)
}

fun String.kwt(tag: String) {
    LogK.wt(tag, this)
}

fun String.ke() {
    LogK.e(this)
}

fun String.ket(tag: String) {
    LogK.et(tag, this)
}

fun String.ka() {
    LogK.a(this)
}

fun String.kat(tag: String) {
    LogK.at(tag, this)
}

object LogK : BaseUtilK() {
    private var _logKPackageName: String

    init {
        val className = LogK::class.java.name
        _logKPackageName = className.substring(0, className.lastIndexOf(".") + 1)
    }

    /**
     * v
     * @param contents Array<out Any>
     */
    fun v(vararg contents: Any) {
        log(CLogType.V, *contents)
    }

    /**
     * vt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun vt(tag: String, vararg contents: Any) {
        log(CLogType.V, tag, *contents)
    }

    /**
     * d
     * @param contents Array<out Any>
     */
    fun d(vararg contents: Any) {
        log(CLogType.D, *contents)
    }

    /**
     * dt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun dt(tag: String, vararg contents: Any) {
        log(CLogType.D, tag, *contents)
    }

    /**
     * i
     * @param contents Array<out Any>
     */
    fun i(vararg contents: Any) {
        log(CLogType.I, *contents)
    }

    /**
     * it
     * @param tag String
     * @param contents Array<out Any>
     */
    fun it(tag: String, vararg contents: Any) {
        log(CLogType.I, tag, *contents)
    }

    /**
     * w
     * @param contents Array<out Any>
     */
    fun w(vararg contents: Any) {
        log(CLogType.W, *contents)
    }

    /**
     * wt
     * @param tag String
     * @param contents Array<out Any>
     */
    fun wt(tag: String, vararg contents: Any) {
        log(CLogType.W, tag, *contents)
    }

    /**
     * e
     * @param contents Array<out Any>
     */
    fun e(vararg contents: Any) {
        log(CLogType.E, *contents)
    }

    /**
     * et
     * @param tag String
     * @param contents Array<out Any>
     */
    fun et(tag: String, vararg contents: Any) {
        log(CLogType.E, tag, *contents)
    }

    /**
     * a
     * @param contents Array<out Any>
     */
    fun a(vararg contents: Any) {
        log(CLogType.A, *contents)
    }

    /**
     * at
     * @param tag String
     * @param contents Array<out Any>
     */
    fun at(tag: String, vararg contents: Any) {
        log(CLogType.A, tag, *contents)
    }

    /**
     * log
     * @param type Int
     * @param contents Array<out Any>
     */
    fun log(@ALogKType type: Int, vararg contents: Any) {
        log(type, LogKMgr.instance.getConfig().getGlobalTag(), contents)
    }

    /**
     * log
     * @param type Int
     * @param tag String
     * @param contents Array<out Any>
     */
    fun log(@ALogKType type: Int, tag: String, vararg contents: Any) {
        log(LogKMgr.instance.getConfig(), type, tag, *contents)
    }

    /**
     * log实现
     * @param config LogKConfig
     * @param type Int
     * @param tag String
     * @param contents Array<out Any?>
     */
    fun log(config: BaseLogKConfig, @ALogKType type: Int, tag: String, vararg contents: Any?) {
        if (!config.enable()) {
            return
        }
        val builder = StringBuilder()
        if (config.includeThread()) {
            val threadInfo: String = CLogKParameter.FORMATTER_THREAD.format(Thread.currentThread())
            builder.append(threadInfo).append("\n")
        }
        if (config.stackTraceDepth() > 0 || (config.stackTraceDepth() <= 0 && type >= CLogType.E)) {
            val stackTrace: String? = CLogKParameter.FORMATTER_STACK_TRACE.format(
                UtilKStackTrace.getCroppedRealStackTrack(
                    Throwable().stackTrace,
                    _logKPackageName,
                    if (config.stackTraceDepth() <= 0) 5 else config.stackTraceDepth()
                )
            )
            builder.append(stackTrace).append("\n")
        }
        require(contents.isNotEmpty()) { "$TAG content's size must not be 0" }
        val body = parseBody(contents, config)
        builder.append(body)
        val printers: MutableList<ILogKPrinter> = ArrayList()
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

    private fun parseBody(contents: Array<out Any?>, config: BaseLogKConfig): String {
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