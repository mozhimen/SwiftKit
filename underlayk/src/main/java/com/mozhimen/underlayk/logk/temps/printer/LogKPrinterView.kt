package com.mozhimen.underlayk.logk.temps.printer

import android.R
import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseLifecycleObserver
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
class LogKPrinterView<T>(owner: T) : ILogKPrinter, BaseLifecycleObserver(owner) where T: Activity, T: LifecycleOwner {
    private var _viewProvider: LogKPrinterViewProvider = LogKPrinterViewProvider(owner, owner.findViewById(R.id.content))
    private var _isShow: Boolean = false

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

    override val TAG: String
        get() = "LogKPrinterView>>>>>"

    override fun print(config: BaseLogKConfig, level: Int, tag: String, printString: String) {
        _viewProvider.print(config, level, tag, printString)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _viewProvider.closeLogView()
        LogKMgr.instance.removePrinter(this)
        super.onDestroy(owner)
    }
}