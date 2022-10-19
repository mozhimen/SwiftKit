package com.mozhimen.underlayk.logk.temps

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import com.mozhimen.underlayk.logk.helpers.LogKHelper
import com.mozhimen.underlayk.logk.mos.LogKMo

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:51
 * @Version 1.0
 */
class PrinterViewItem(private val logKMo: LogKMo) : DataKItem<Any, BindKViewHolder<LogkPrinterViewItemBinding>>() {
    override fun onBindData(holder: BindKViewHolder<LogkPrinterViewItemBinding>, position: Int) {
        val color = LogKHelper.getLevelColor(logKMo.level)
        holder.binding.logkPrinterViewTag.text = logKMo.getFlattened()
        holder.binding.logkPrinterViewTag.setTextColor(color)
        holder.binding.logkPrinterViewMsg.text = logKMo.log.replace("\\n".toRegex(), "\n").replace(UtilKGlobal.instance.getApp()!!.packageName, "")
        holder.binding.logkPrinterViewMsg.setTextColor(color)
    }

    override fun onCreateViewHolder(parent: ViewGroup): BindKViewHolder<LogkPrinterViewItemBinding> {
        return BindKViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
    }

    override fun getItemLayoutRes(): Int = R.layout.logk_printer_view_item
}