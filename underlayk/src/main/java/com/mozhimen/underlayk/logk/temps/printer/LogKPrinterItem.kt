package com.mozhimen.underlayk.logk.temps.printer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import com.mozhimen.underlayk.logk.helpers.LogKHelper
import com.mozhimen.underlayk.logk.mos.MLogK

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:51
 * @Version 1.0
 */
class LogKPrinterItem(private val _logK: MLogK) : BaseRecyclerKItem<VHKRecyclerVB<LogkPrinterViewItemBinding>>() {
    override fun onBindItem(holder: VHKRecyclerVB<LogkPrinterViewItemBinding>, position: Int) {
        super.onBindItem(holder, position)
        val color = LogKHelper.getLevelColor(_logK.level)
        holder.vb.logkPrinterViewTag.text = _logK.getFlattened()
        holder.vb.logkPrinterViewTag.setTextColor(color)
        holder.vb.logkPrinterViewMsg.text = _logK.log.replace("\\n".toRegex(), "\n").replace(UtilKContext.getPackageName(UtilKApplication.instance.get()), "")
        holder.vb.logkPrinterViewMsg.setTextColor(color)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<LogkPrinterViewItemBinding> {
        return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
    }

    override fun getItemLayoutId(): Int = R.layout.logk_printer_view_item
}