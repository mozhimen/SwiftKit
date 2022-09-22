package com.mozhimen.underlayk.logk.temps

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.R
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.basick.utilk.UtilKGlobal

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 15:52
 * @Version 1.0
 */
class PrinterMonitor : IPrinter {

    private val _context = UtilKGlobal.instance.getApp()!!
    private var _params = WindowManager.LayoutParams()
    private var _isShow = false
    private var _printerView =
        LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView
    private var _windowManager: WindowManager = _context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {

    }
}