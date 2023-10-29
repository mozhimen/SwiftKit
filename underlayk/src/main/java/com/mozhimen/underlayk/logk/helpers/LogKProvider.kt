package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.basick.elemk.android.util.annors.ALogPriority
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKStackTraceElement
import com.mozhimen.basick.utilk.kotlin.getStrPackage
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.commons.ILogK
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterStackTraces
import com.mozhimen.underlayk.logk.temps.formatter.LogKFormatterThread
import java.lang.StringBuilder


/**
 * @ClassName LogKProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 18:06
 * @Version 1.0
 */
@OptIn(OptInApiInit_InApplication::class)
class LogKProvider : ILogK, IUtilK {
    private val _logKPackageName: String by lazy { LogK::class.java.getStrPackage() }
    private val _logKFormatterThread: LogKFormatterThread by lazy { LogKFormatterThread() }
    private val _logKFormatterStackTraces: LogKFormatterStackTraces by lazy { LogKFormatterStackTraces() }

    override fun vk(vararg contents: Any) {
        logk(CLogPriority.V, *contents)
    }

    override fun vtk(tag: String, vararg contents: Any) {
        logk(CLogPriority.V, tag, *contents)
    }

    override fun dk(vararg contents: Any) {
        logk(CLogPriority.D, *contents)
    }

    override fun dtk(tag: String, vararg contents: Any) {
        logk(CLogPriority.D, tag, *contents)
    }

    override fun ik(vararg contents: Any) {
        logk(CLogPriority.I, *contents)
    }

    override fun itk(tag: String, vararg contents: Any) {
        logk(CLogPriority.I, tag, *contents)
    }

    override fun wk(vararg contents: Any) {
        logk(CLogPriority.W, *contents)
    }

    override fun wtk(tag: String, vararg contents: Any) {
        logk(CLogPriority.W, tag, *contents)
    }

    override fun ek(vararg contents: Any) {
        logk(CLogPriority.E, *contents)
    }

    override fun etk(tag: String, vararg contents: Any) {
        logk(CLogPriority.E, tag, *contents)
    }

    override fun ak(vararg contents: Any) {
        logk(CLogPriority.A, *contents)
    }

    override fun atk(tag: String, vararg contents: Any) {
        logk(CLogPriority.A, tag, *contents)
    }

    override fun logk(@ALogPriority priority: Int, vararg contents: Any) {
        logk(priority, LogKMgr.instance.getConfig().getGlobalTag(), contents)
    }

    override fun logk(@ALogPriority priority: Int, tag: String, vararg contents: Any) {
        logk(LogKMgr.instance.getConfig(), priority, tag, *contents)
    }

    override fun logk(config: BaseLogKConfig, @ALogPriority priority: Int, tag: String, vararg contents: Any?) {
        if (!config.isEnable()) return

        val stringBuilder = StringBuilder()
        if (config.isIncludeThread()) {
            val threadInfo: String = _logKFormatterThread.format(Thread.currentThread())
            stringBuilder.append(threadInfo).append("\n")
        }
        if (config.getStackTraceDepth() > 0 || (config.getStackTraceDepth() <= 0 && priority >= CLogPriority.E)) {
            val stackTrace: String? = _logKFormatterStackTraces.format(
                UtilKStackTraceElement.getCroppedRealStackTracks(
                    Throwable().stackTrace, _logKPackageName,
                    if (config.getStackTraceDepth() <= 0) 5
                    else config.getStackTraceDepth()
                )
            )
            stringBuilder.append(stackTrace).append("\n")
        }

        require(contents.isNotEmpty()) { "$TAG content's size must not be 0" }

        val body = parseBody(contents, config)
        stringBuilder.append(body)
        val printers: MutableList<ILogKPrinter> = ArrayList()
        if (config.getPrinters() != null)
            printers.addAll(config.getPrinters()!!)
        else
            printers.addAll(LogKMgr.instance.getPrinters())

        if (printers.isEmpty()) return

        //打印log
        for (printer in printers)
            printer.print(config, priority, tag, stringBuilder.toString().replace("[", "").replace("]", ""))
    }

    private fun parseBody(contents: Array<out Any?>, config: BaseLogKConfig): String {
        if (config.injectJsonParser() != null)
            return config.injectJsonParser()!!.toJson(contents)

        val stringBuilder = StringBuilder()
        for (content in contents)
            stringBuilder.append(content.toString()).append(";")

        if (stringBuilder.isNotEmpty())
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        return stringBuilder.toString()
    }
}