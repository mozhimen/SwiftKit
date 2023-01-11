package com.mozhimen.underlayk.logk.temps

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.underlayk.logk.LogKMgr
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.bases.BaseLogKConfig

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class LogKPrinterView(activity: Activity, owner: LifecycleOwner) : ILogKPrinter, DefaultLifecycleObserver {
    private var _viewProvider: LogKPrinterViewProvider = LogKPrinterViewProvider(activity, activity.findViewById(android.R.id.content))
    private var _isShow: Boolean = false

    init {
        owner.lifecycle.addObserver(this)
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

    fun getViewProvider(): LogKPrinterViewProvider =
        _viewProvider

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        _viewProvider.print(config, level, tag, printString)
    }

    override fun onPause(owner: LifecycleOwner) {
        LogKMgr.instance.removePrinter(this)
        _viewProvider.closeLogView()
        owner.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}