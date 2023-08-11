package com.mozhimen.underlayk.logk.temps.printer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.mozhimen.basick.elemk.android.util.annors.ALogPriority
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import com.mozhimen.underlayk.logk.bases.BaseLogKRecord

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:51
 * @Version 1.0
 */
class LogKPrinterItem<R : BaseLogKRecord>(private val _record: R) : BaseRecyclerKItem<VHKRecyclerVB<LogkPrinterViewItemBinding>>() {
    override fun onBindItem(holder: VHKRecyclerVB<LogkPrinterViewItemBinding>, position: Int) {
        super.onBindItem(holder, position)
        val colorInt = getColorIntFor(_record.priority)
        holder.vb.logkPrinterViewTag.text = _record.getFlattened()
        holder.vb.logkPrinterViewTag.setTextColor(colorInt)
        holder.vb.logkPrinterViewMsg.text = _record.msg.replaceRegexLineBreak().replace(UtilKPackage.getPackageName(), "")
        holder.vb.logkPrinterViewMsg.setTextColor(colorInt)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<LogkPrinterViewItemBinding> =
        VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))

    override fun getItemLayoutId(): Int =
        com.mozhimen.underlayk.R.layout.logk_printer_view_item

    @ColorInt
    fun getColorIntFor(@ALogPriority priority: Int): Int =
        UtilKRes.getColor(
            when (priority) {
                CLogPriority.V -> com.mozhimen.underlayk.R.color.logk_v
                CLogPriority.D -> com.mozhimen.underlayk.R.color.logk_d
                CLogPriority.I -> com.mozhimen.underlayk.R.color.logk_i
                CLogPriority.W -> com.mozhimen.underlayk.R.color.logk_w
                CLogPriority.E -> com.mozhimen.underlayk.R.color.logk_e
                else -> -0x100
            }
        )
}