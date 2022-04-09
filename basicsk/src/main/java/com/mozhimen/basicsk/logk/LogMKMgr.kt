package com.mozhimen.basicsk.logk

import com.mozhimen.basicsk.logk.commons.ILogKPrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig

/**
 * @ClassName LogKManager
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
class LogKManager(private val config: LogKConfig, printers: Array<out ILogKPrinter>) {
    private val printers: MutableList<ILogKPrinter> = ArrayList()

    init {
        this.printers.addAll(printers)
    }

    companion object{
        private lateinit var instance: LogKManager

        fun init(config: LogKConfig, vararg printers: ILogKPrinter) {
            instance = LogKManager(config, printers)
        }

        fun getInstance(): LogKManager {
            return instance
        }
    }

    fun getConfig(): LogKConfig {
        return config
    }

    fun getPrinters(): MutableList<ILogKPrinter> {
        return printers
    }

    fun addPrinter(printer: ILogKPrinter) {
        printers.add(printer)
    }

    fun removePrinter(printer: ILogKPrinter) {
        printers.remove(printer)
    }
}
