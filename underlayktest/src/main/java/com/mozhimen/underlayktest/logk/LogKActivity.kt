package com.mozhimen.underlayktest.logk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.mos.LogKType
import com.mozhimen.underlayk.logk.temps.PrinterMonitor
import com.mozhimen.underlayk.logk.temps.PrinterView
import com.mozhimen.underlayktest.databinding.ActivityLogkBinding

class LogKActivity : BaseKActivityVB<ActivityLogkBinding>() {
    private val _printerView: PrinterView by lazy { PrinterView(this, this) }
    private val _printerMonitor: PrinterMonitor by lazy { PrinterMonitor() }

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initPrinterView()
        initPrinterMonitor()

        vb.logkBtnPrint.setOnClickListener {
            printLog()
        }
        vb.logkBtnPrinterList.setOnClickListener {
            printLog1()
        }
    }

    private fun initPrinterView() {
        _printerView.toggleView()

    }

    private fun initPrinterMonitor() {
        //添加_printerMonitor的时候一定Application要继承BaseKApplication, 因为其中实现了前后台切换的监听
        LogKMgr.instance.addPrinter(_printerMonitor)
        _printerMonitor.toggleMonitor()
    }

    private fun printLog() {
        //初级用法
        LogK.i("just a test1!")

        //中级用法
        LogK.log(LogKType.W, TAG, "just a test2!")

        //高级用法
        LogK.log(object : LogKConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, LogKType.E, TAG, "just a test3!")
    }

    private fun printLog1() {
        val printers: String = LogKMgr.instance.getPrinters().joinToString { it.getName().replace(">>>>>", "") }
        LogK.dt(TAG, printers)
    }

    override fun onResume() {
        super.onResume()
        LogKMgr.instance.addPrinter(_printerView)
    }
}