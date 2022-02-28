package com.mozhimen.app.basicsmk.logmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityLogmkBinding
import com.mozhimen.basicsmk.logmk.LogMK
import com.mozhimen.basicsmk.logmk.LogMKManager
import com.mozhimen.basicsmk.logmk.helpers.ViewPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.logmk.mos.LogMKType

class LogMKActivity : AppCompatActivity() {
    private val vb: ActivityLogmkBinding by lazy { ActivityLogmkBinding.inflate(layoutInflater) }
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
        LogMKManager.getInstance().addPrinter(viewPrinter!!)
        LogMK.log(object : LogMKConfig() {
            override fun includeThread(): Boolean {
                return false
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, LogMKType.E, ">>>>>", "just a test!")

        LogMK.a("just a test!")
    }
}