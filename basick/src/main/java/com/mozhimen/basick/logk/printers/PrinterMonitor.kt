package com.mozhimen.basick.logk.printers

import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.R
import com.mozhimen.basick.logk.commons.IPrinter
import com.mozhimen.basick.logk.mos.LogKConfig
import com.mozhimen.basick.utilk.UtilKGlobal

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 15:52
 * @Version 1.0
 */
class PrinterMonitor : IPrinter {
    private val TAG = "PrinterMonitor>>>>>"

    private val _context = UtilKGlobal.instance.getApp()!!
    private var _params = WindowManager.LayoutParams()
    private var _isShow = false
    private var _printerView =
        LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {

    }

    override fun getName(): String =
        TAG
}