package com.mozhimen.underlayk.logk.temps.printer

import android.app.Activity
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.commons.ILogKPrinterMonitor
import java.lang.ref.WeakReference

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 15:52
 * @Version 1.0
 */
@OptInApiInit_InApplication
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class LogKPrinterMonitor : ILogKPrinter, ILogKPrinterMonitor, IUtilK {

    private val _logKPrinterMonitorDelegate: LogKPrinterMonitorDelegate = LogKPrinterMonitorDelegate()

    /////////////////////////////////////////////////////////////////////////////////////////

    init {
        StackKCb.instance.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean, activityRef: WeakReference<Activity>) {
                if (!isFront && isOpen()) {
                    LogK.wtk(TAG, "PrinterMonitor onChanged log stop")
                    _logKPrinterMonitorDelegate.close()
                }
//                if (isFront) {
//                    if (_isShow) {
//                        LogK.dt(TAG, "PrinterMonitor onChanged log start")
//                        _printerMonitorProvider.openMonitor(true)
//                    }
//                }
            }
        })
    }

    override fun isOpen(): Boolean =
        _logKPrinterMonitorDelegate.isOpen()

    override fun open() {
        _logKPrinterMonitorDelegate.open()
    }

    override fun open(isFold: Boolean) {
        _logKPrinterMonitorDelegate.open(isFold)
    }

    override fun toggle() {
        _logKPrinterMonitorDelegate.toggle()
    }

    override fun toggle(isFold: Boolean) {
        _logKPrinterMonitorDelegate.toggle(isFold)
    }

    override fun close() {
        _logKPrinterMonitorDelegate.close()
    }

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        _logKPrinterMonitorDelegate.print(config, priority, tag, msg)
    }
}