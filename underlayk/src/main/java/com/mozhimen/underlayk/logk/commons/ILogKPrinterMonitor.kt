package com.mozhimen.underlayk.logk.commons

/**
 * @ClassName ILogKPrinterMonitor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/27 15:57
 * @Version 1.0
 */
interface ILogKPrinterMonitor {
    fun isOpen(): Boolean
    fun open()
    fun open(isFold: Boolean)
    fun toggle()
    fun toggle(isFold: Boolean)
    /**
     * 关闭Monitor
     */
    fun close()
}