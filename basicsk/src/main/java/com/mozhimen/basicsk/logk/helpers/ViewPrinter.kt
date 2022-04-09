package com.mozhimen.basicsk.logk.helpers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basicsk.R
import com.mozhimen.basicsk.logk.commons.ILogKPrinter
import com.mozhimen.basicsk.logk.mos.LogKConfig
import com.mozhimen.basicsk.logk.mos.LogKMo
import com.mozhimen.basicsk.logk.mos.LogKType
import com.mozhimen.basicsk.logk.views.ViewPrinterProvider
import java.util.ArrayList

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class ViewPrinter(activity: Activity) : ILogKPrinter {
    private var recyclerView: RecyclerView
    private var adapter: LogKAdapter
    private var viewProvider: ViewPrinterProvider

    init {
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        recyclerView = RecyclerView(activity)
        adapter = LogKAdapter(LayoutInflater.from(recyclerView.context))
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        viewProvider = ViewPrinterProvider(rootView, recyclerView)
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        //将log展示添加到recyclerView
        adapter.addItem(LogKMo(System.currentTimeMillis(), level, tag, printString))
        //滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }

    /**
     * 获取ViewProvider,通过ViewProvider可以控制log视图的展示和隐藏
     */
    fun getViewProvider(): ViewPrinterProvider {
        return viewProvider
    }

    private class LogKAdapter(private val inflater: LayoutInflater) :
        RecyclerView.Adapter<LogKViewHolder>() {

        private val logKMos: MutableList<LogKMo> = ArrayList<LogKMo>()

        fun addItem(logKItem: LogKMo) {
            logKMos.add(logKItem)
            notifyItemInserted(logKMos.size - 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogKViewHolder {
            val itemView: View = inflater.inflate(R.layout.item_logk, parent, false)
            return LogKViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: LogKViewHolder, position: Int) {
            val logItem: LogKMo = logKMos[position]
            val color = getHeightLightColor(logItem.level)
            holder.tagView.setTextColor(color)
            holder.messageView.setTextColor(color)
            holder.tagView.text = logItem.getFlattened()
            holder.messageView.text = logItem.log
        }

        private fun getHeightLightColor(logLevel: Int): Int {
            return when (logLevel) {
                LogKType.V -> -0x444445
                LogKType.D -> -0x1
                LogKType.I -> -0x9578a7
                LogKType.W -> -0x444ad7
                LogKType.E -> -0x9498
                else -> -0x100
            }
        }

        override fun getItemCount(): Int {
            return logKMos.size
        }
    }

    private class LogKViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagView: TextView = itemView.findViewById(R.id.logk_tag)
        var messageView: TextView = itemView.findViewById(R.id.logk_message)
    }
}