package com.mozhimen.underlayk.logk.temps

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.R
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.mos.LogKMo
import com.mozhimen.underlayk.logk.mos.LogKType
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.underlayk.databinding.LogkPrinterViewItemBinding
import java.util.*

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class PrinterView(activity: Activity) : IPrinter {
    private var _recyclerView: RecyclerView
    private var _adapter: LogKAdapter
    private var _viewProvider: PrinterViewProvider

    init {
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)

        _recyclerView = RecyclerView(activity)
        _adapter = LogKAdapter(LayoutInflater.from(_recyclerView.context))
        val layoutManager = LinearLayoutManager(_recyclerView.context)
        _recyclerView.layoutManager = layoutManager
        _recyclerView.adapter = _adapter

        _viewProvider = PrinterViewProvider(rootView, _recyclerView)
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        //将log展示添加到recyclerView
        _adapter.addItem(LogKMo(System.currentTimeMillis(), level, tag, printString))
        //滚动到对应的位置
        _recyclerView.smoothScrollToPosition(_adapter.itemCount - 1)
    }

    /**
     * 获取ViewProvider,通过ViewProvider可以控制log视图的展示和隐藏
     * @return PrinterViewProvider
     */
    fun getViewProvider(): PrinterViewProvider {
        return _viewProvider
    }

    private class LogKPrinterViewItem : DataKItem<Any, BindKViewHolder<LogkPrinterViewItemBinding>>() {
        override fun onBindData(holder: BindKViewHolder<LogkPrinterViewItemBinding>, position: Int) {

        }

        override fun onCreateViewHolder(parent: ViewGroup): BindKViewHolder<LogkPrinterViewItemBinding> {
            return BindKViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
        }

        override fun getItemLayoutRes(): Int = R.layout.logk_printer_view_item
    }

    private class LogKAdapter(private val _inflater: LayoutInflater) :
        RecyclerView.Adapter<LogKAdapter.LogKViewHolder>() {

        private val logKMos: MutableList<LogKMo> = ArrayList<LogKMo>()

        private class LogKViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tagView: TextView = itemView.findViewById(R.id.logk_tag)
            var magView: TextView = itemView.findViewById(R.id.logk_mag)
        }

        fun addItem(logKItem: LogKMo) {
            logKMos.add(logKItem)
            notifyItemInserted(logKMos.size - 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogKViewHolder {
            val itemView: View = _inflater.inflate(R.layout.logk_recycler_item, parent, false)
            return LogKViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: LogKViewHolder, position: Int) {
            val logItem: LogKMo = logKMos[position]
            val color = getHeightLightColor(logItem.level)
            holder.tagView.text = logItem.getFlattened()
            holder.tagView.setTextColor(color)
            holder.magView.text = logItem.log.replace("\\n", "\n")
                .replace(UtilKGlobal.instance.getApp()!!.packageName, "")
            holder.magView.setTextColor(color)
        }

        override fun getItemCount(): Int {
            return logKMos.size
        }
    }
}