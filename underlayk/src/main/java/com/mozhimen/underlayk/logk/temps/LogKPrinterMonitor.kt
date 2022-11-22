package com.mozhimen.underlayk.logk.temps

import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.commons.LogKConfig

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 15:52
 * @Version 1.0
 */
class LogKPrinterMonitor() : ILogKPrinter {
    private val _printerMonitorProvider: LogKPrinterMonitorProvider = LogKPrinterMonitorProvider(UtilKApplication.instance.get())
    private var _isShow: Boolean = false

    init {
        StackK.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
                if (isFront) {
                    LogK.dt(TAG, "PrinterMonitor onChanged log start")
                    _printerMonitorProvider.openMonitor(true)
                } else {
                    LogK.wt(TAG, "PrinterMonitor onChanged log stop")
                    _printerMonitorProvider.closeMonitor()
                }
            }
        })
    }

    fun isShow(): Boolean = _isShow

    fun toggleMonitor(isFold: Boolean = true) {
        if (_isShow) {
            _isShow = false
            _printerMonitorProvider.closeMonitor()
        } else {
            _isShow = true
            _printerMonitorProvider.openMonitor(isFold)
        }
    }

    fun getPrinterMonitorProvider(): LogKPrinterMonitorProvider =
        _printerMonitorProvider

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        _printerMonitorProvider.print(config, level, tag, printString)
    }
}