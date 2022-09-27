package com.mozhimen.underlayk.logk.temps

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class PrinterView(activity: Activity, lifecycleOwner: LifecycleOwner) : IPrinter, DefaultLifecycleObserver {
    private var _viewProvider: PrinterViewProvider = PrinterViewProvider(activity, activity.findViewById(android.R.id.content))
    private var _isShow: Boolean = false

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun toggleView(isFold: Boolean = true) {
        if (_isShow) {
            _isShow = false
            _viewProvider.closeLogView()
        } else {
            _isShow = true
            _viewProvider.showLogView(isFold)
        }
    }

    fun getViewProvider(): PrinterViewProvider =
        _viewProvider

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        _viewProvider.print(config, level, tag, printString)
    }

    override fun onPause(owner: LifecycleOwner) {
        LogKMgr.instance.removePrinter(this)
        _viewProvider.closeLogView()
        owner.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}