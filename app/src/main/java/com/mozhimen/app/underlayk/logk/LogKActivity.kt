package com.mozhimen.app.underlayk.logk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityLogkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.temps.PrinterView
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.mos.LogKType

class LogKActivity : BaseKActivity<ActivityLogkBinding, BaseKViewModel>(R.layout.activity_logk) {
    private var _printerView: PrinterView? = null

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        _printerView = PrinterView(this)
        _printerView!!.toggleView(true)
        vb.logkBtnPrint.setOnClickListener {
            printLog()
        }
        vb.logkBtnPrinterList.setOnClickListener {
            printLog1()
        }
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
        LogKMgr.instance.addPrinter(_printerView!!)
    }

    override fun onPause() {
        super.onPause()
        LogKMgr.instance.removePrinter(_printerView!!)
    }
}