package com.mozhimen.underlayk.logk

import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.commons.LogKConfig
import com.mozhimen.underlayk.logk.temps.LogKPrinterConsole

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
    private val _printers: MutableList<ILogKPrinter> = ArrayList()
    private var _config: LogKConfig? = null

    companion object {
        @JvmStatic
        val instance = LogKMgrHolder.holder
    }

    private object LogKMgrHolder {
        val holder = LogKMgr()
    }

    fun init(config: LogKConfig, vararg printers: ILogKPrinter) {
        _config = config
        _printers.addAll(printers.filterNot { _printers.contains(it) })
    }

    /**
     * 获取配置
     * @return LogKConfig
     */
    fun getConfig(): LogKConfig = _config ?: LogKConfig()

    /**
     * 获取打印机列表
     * @return MutableList<IPrinter>
     */
    fun getPrinters(): MutableList<ILogKPrinter> {
        return if (_printers.isEmpty()) {
            mutableListOf<ILogKPrinter>(LogKPrinterConsole()).also { _printers.addAll(it) }
        } else {
            _printers
        }
    }

    /**
     * 增加打印机
     * @param printer IPrinter
     */
    fun addPrinter(printer: ILogKPrinter) {
        _printers.add(printer)
    }

    /**
     * 移除打印机
     * @param printer IPrinter
     */
    fun removePrinter(printer: ILogKPrinter) {
        _printers.remove(printer)
    }
}
