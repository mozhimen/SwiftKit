package com.mozhimen.underlayk.logk

import com.mozhimen.basick.utilk.java.datatype.containsBy
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
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
class LogKMgr(/*private val config: LogKConfig, printers: Array<out IPrinter>*/) {
    private val _printers: MutableList<ILogKPrinter> = mutableListOf(LogKPrinterConsole())
    private var _config: BaseLogKConfig? = null

    companion object {
        @JvmStatic
        val instance = LogKMgrProvider.holder
    }

    private object LogKMgrProvider {
        val holder = LogKMgr()
    }

    fun init(config: BaseLogKConfig, vararg printers: ILogKPrinter) {
        _config = config
        _printers.addAll(printers.filter { o -> !_printers.containsBy { it.getName() == o.getName() } })
    }

    /**
     * 获取配置
     * @return LogKConfig
     */
    fun getConfig(): BaseLogKConfig = _config ?: BaseLogKConfig()

    /**
     * 获取打印机列表
     * @return MutableList<IPrinter>
     */
    fun getPrinters(): List<ILogKPrinter> {
        return _printers
    }

    /**
     * 增加打印机
     * @param printer IPrinter
     */
    fun addPrinter(printer: ILogKPrinter) {
        if (!_printers.containsBy { it.getName() == printer.getName() }) {
            _printers.add(printer)
        }
    }

    /**
     * 移除打印机
     * @param printer IPrinter
     */
    fun removePrinter(printer: ILogKPrinter) {
        _printers.remove(printer)
    }
}
