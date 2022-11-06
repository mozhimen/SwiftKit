package com.mozhimen.underlayktest.logk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.commons.LogKConfig
import com.mozhimen.underlayk.logk.mos.CLogKType
import com.mozhimen.underlayk.logk.temps.LogKPrinterMonitor
import com.mozhimen.underlayk.logk.temps.LogKPrinterView
import com.mozhimen.underlayktest.databinding.ActivityLogkBinding

class LogKActivity : BaseKActivityVB<ActivityLogkBinding>() {
    private val _printerView: LogKPrinterView by lazy { LogKPrinterView(this, this) }
    private val _printerMonitor: LogKPrinterMonitor by lazy {
        LogKMgr.instance.getPrinters().filterIsInstance<LogKPrinterMonitor>().getOrNull(0) ?: kotlin.run {
            Log.d(TAG, "_printerMonitor: init")
            LogKPrinterMonitor().also { LogKMgr.instance.addPrinter(it) }
        }
    }

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
        _printerMonitor.toggleMonitor()
    }

    private fun printLog() {
        //初级用法
        LogK.i("just a test1!")

        //中级用法
        LogK.log(CLogKType.W, TAG, "just a test2!")

        //高级用法
        LogK.log(object : LogKConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, CLogKType.E, TAG, "just a test3!")
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