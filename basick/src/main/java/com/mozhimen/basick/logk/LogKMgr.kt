package com.mozhimen.basick.logk

import com.mozhimen.basick.logk.commons.IPrinter
import com.mozhimen.basick.logk.mos.LogKConfig

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
class LogKMgr(private val config: LogKConfig, printers: Array<out IPrinter>) {
    private val _printers: MutableList<IPrinter> = ArrayList()

    init {
        this._printers.addAll(printers)
    }

    companion object {
        private lateinit var instance: LogKMgr

        fun init(config: LogKConfig, vararg printers: IPrinter) {
            instance = LogKMgr(config, printers)
        }

        fun getInstance(): LogKMgr {
            return instance
        }
    }

    /**
     * 获取配置
     * @return LogKConfig
     */
    fun getConfig(): LogKConfig {
        return config
    }

    /**
     * 获取打印机列表
     * @return MutableList<IPrinter>
     */
    fun getPrinters(): MutableList<IPrinter> {
        return _printers
    }

    /**
     * 增加打印机
     * @param printer IPrinter
     */
    fun addPrinter(printer: IPrinter) {
        _printers.add(printer)
    }

    /**
     * 移除打印机
     * @param printer IPrinter
     */
    fun removePrinter(printer: IPrinter) {
        _printers.remove(printer)
    }
}
