package com.mozhimen.underlayk.logk

import com.mozhimen.basick.utilk.java.datatype.containsBy
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.commons.ILogKMgr
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterConsole

/**
 * @ClassName LogKMgr
 * @Description
 * Sample:
 * LogKManager.init(object : LogKConfig() {
 * override fun injectJsonParser(): JsonParser {
 * return JsonParser { src -> Gson().toJson(src) }
 * }
 * <p>
 * override fun getGlobalTag(): String {
 * return "MApplication"
 * }
 * <p>
 * override fun enable(): Boolean {
 * return true
 * }
 * }, ConsolePrinter())
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
class LogKMgr(/*private val config: LogKConfig, printers: Array<out IPrinter>*/) : ILogKMgr {
    private val _printers: MutableList<ILogKPrinter> = mutableListOf(LogKPrinterConsole())
    private var _config: BaseLogKConfig? = null

    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }


    override fun init(config: BaseLogKConfig, vararg printers: ILogKPrinter) {
        _config = config
        _printers.addAll(printers.filter { o -> !_printers.containsBy { it.getName() == o.getName() } })
    }

    override fun getConfig(): BaseLogKConfig =
        _config ?: BaseLogKConfig()

    override fun getPrinters(): List<ILogKPrinter> =
        _printers

    override fun addPrinter(printer: ILogKPrinter) {
        if (!_printers.containsBy { it.getName() == printer.getName() }) {
            _printers.add(printer)
        }
    }

    override fun removePrinter(printer: ILogKPrinter) {
        _printers.remove(printer)
    }

    private object INSTANCE {
        val holder = LogKMgr()
    }
}
