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
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import com.mozhimen.underlayk.logk.bases.BaseLogKRecord

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:51
 * @Version 1.0
 */
class LogKPrinterItem(private val _log: BaseLogKRecord) : BaseRecyclerKItem<VHKRecyclerVB<LogkPrinterViewItemBinding>>() {
    override fun onBindItem(holder: VHKRecyclerVB<LogkPrinterViewItemBinding>, position: Int) {
        super.onBindItem(holder, position)
        val colorInt = getColorIntFor(_log.priority)
        holder.vb.logkPrinterViewTag.text = _log.getFlattened()
        holder.vb.logkPrinterViewTag.setTextColor(colorInt)
        holder.vb.logkPrinterViewMsg.text = _log.msg.replaceRegexLineBreak().replace(UtilKPackage.getPackageName(), "")
        holder.vb.logkPrinterViewMsg.setTextColor(colorInt)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<LogkPrinterViewItemBinding> {
        return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
    }

    override fun getItemLayoutId(): Int = R.layout.logk_printer_view_item

    @ColorInt
    fun getColorIntFor(@ALogPriority priority: Int): Int =
        UtilKRes.getColor(
            when (priority) {
                CLogPriority.V -> R.color.logk_v
                CLogPriority.D -> R.color.logk_d
                CLogPriority.I -> R.color.logk_i
                CLogPriority.W -> R.color.logk_w
                CLogPriority.E -> R.color.logk_e
                else -> -0x100
            }
        )
}