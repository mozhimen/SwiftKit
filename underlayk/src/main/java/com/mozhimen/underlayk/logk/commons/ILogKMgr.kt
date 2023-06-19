package com.mozhimen.underlayk.logk.commons

import com.mozhimen.underlayk.logk.bases.BaseLogKConfig

/**
 * @ClassName ILogKMgr
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/4 14:19
 * @Version 1.0
 */
interface ILogKMgr {
    fun init(config: BaseLogKConfig, vararg printers: ILogKPrinter)
    /**
     * 获取配置
     * @return LogKConfig
     */
    fun getConfig(): BaseLogKConfig
    /**
     * 获取打印机列表
     * @return MutableList<IPrinter>
     */
    fun getPrinters(): List<ILogKPrinter>
    /**
     * 增加打印机
     * @param printer IPrinter
     */
    fun addPrinter(printer: ILogKPrinter)
    /**
     * 移除打印机
     * @param printer IPrinter
     */
    fun removePrinter(printer: ILogKPrinter)
}