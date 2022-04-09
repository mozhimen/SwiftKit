package com.mozhimen.app.basicsk.logk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityLogkBinding
import com.mozhimen.basicsk.logk.LogK
import com.mozhimen.basicsk.logk.LogKManager
import com.mozhimen.basicsk.logk.helpers.ViewPrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.logk.mos.LogKType

class LogKActivity : AppCompatActivity() {
    private val vb: ActivityLogkBinding by lazy { ActivityLogkBinding.inflate(layoutInflater) }
    var viewPrinter: ViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        viewPrinter = ViewPrinter(this)
        viewPrinter!!.getViewProvider().showFloatingView()
        vb.btnLog.setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        LogKManager.getInstance().addPrinter(viewPrinter!!)
        LogK.log(object : LogKConfig() {
            override fun includeThread(): Boolean {
                return false
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, LogKType.E, ">>>>>", "just a test!")

        LogK.a("just a test!")
    }
}