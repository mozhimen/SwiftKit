package com.mozhimen.underlayk.logk.temps

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.helpers.LogKHelper
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.mos.LogKMo

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class PrinterView(activity: Activity) : IPrinter {
    private var _viewProvider: PrinterViewProvider = PrinterViewProvider(activity, activity.findViewById(android.R.id.content))
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

    fun getViewProvider(): PrinterViewProvider =
        _viewProvider

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        _viewProvider.print(config, level, tag, printString)
    }
}