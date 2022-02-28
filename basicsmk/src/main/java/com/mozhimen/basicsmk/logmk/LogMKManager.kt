package com.mozhimen.basicsmk.logmk

import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig

/**
 * @ClassName LogMKManager
 * @Description
 * Sample:
 * LogMKManager.init(object : LogMKConfig() {
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
class LogMKManager(private val config: LogMKConfig, printers: Array<out ILogMKPrinter>) {
    private val printers: MutableList<ILogMKPrinter> = ArrayList()

    init {
        this.printers.addAll(printers)
    }

    companion object{
        private lateinit var instance: LogMKManager

        fun init(config: LogMKConfig, vararg printers: ILogMKPrinter) {
            instance = LogMKManager(config, printers)
        }

        fun getInstance(): LogMKManager {
            return instance
        }
    }

    fun getConfig(): LogMKConfig {
        return config
    }

    fun getPrinters(): MutableList<ILogMKPrinter> {
        return printers
    }

    fun addPrinter(printer: ILogMKPrinter) {
        printers.add(printer)
    }

    fun removePrinter(printer: ILogMKPrinter) {
        printers.remove(printer)
    }
}
