package com.mozhimen.basicsmk.logmk.helpers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basicsmk.R
import com.mozhimen.basicsmk.logmk.commons.ILogMKPrinter
import com.mozhimen.basicsmk.logmk.mos.LogMKConfig
import com.mozhimen.basicsmk.logmk.mos.LogMKMo
import com.mozhimen.basicsmk.logmk.mos.LogMKType
import java.util.ArrayList

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 17:20
 * @Version 1.0
 */
class ViewPrinter(activity: Activity) : ILogMKPrinter {
    private var recyclerView: RecyclerView
    private var adapter: LogMKAdapter
    private var viewProvider: ViewPrinterProvider

    init {
        val rootView = activity.findViewById<FrameLayout>(android.R.id.content)
        recyclerView = RecyclerView(activity)
        adapter = LogMKAdapter(LayoutInflater.from(recyclerView.context))
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        viewProvider = ViewPrinterProvider(rootView, recyclerView)
    }

    override fun print(config: LogMKConfig, level: Int, tag: String, printString: String) {
        //将log展示添加到recyclerView
        adapter.addItem(LogMKMo(System.currentTimeMillis(), level, tag, printString))
        //滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }

    /**
     * 获取ViewProvider,通过ViewProvider可以控制log视图的展示和隐藏
     */
    fun getViewProvider(): ViewPrinterProvider {
        return viewProvider
    }

    private class LogMKAdapter(private val inflater: LayoutInflater) :
        RecyclerView.Adapter<LogMKViewHolder>() {

        private val logMKMos: MutableList<LogMKMo> = ArrayList<LogMKMo>()

        fun addItem(logMKItem: LogMKMo) {
            logMKMos.add(logMKItem)
            notifyItemInserted(logMKMos.size - 1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogMKViewHolder {
            val itemView: View = inflater.inflate(R.layout.item_logmk, parent, false)
            return LogMKViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: LogMKViewHolder, position: Int) {
            val logItem: LogMKMo = logMKMos[position]
            val color = getHeightLightColor(logItem.level)
            holder.tagView.setTextColor(color)
            holder.messageView.setTextColor(color)
            holder.tagView.text = logItem.getFlattened()
            holder.messageView.text = logItem.log
        }

        private fun getHeightLightColor(logLevel: Int): Int {
            return when (logLevel) {
                LogMKType.V -> -0x444445
                LogMKType.D -> -0x1
                LogMKType.I -> -0x9578a7
                LogMKType.W -> -0x444ad7
                LogMKType.E -> -0x9498
                else -> -0x100
            }
        }

        override fun getItemCount(): Int {
            return logMKMos.size
        }
    }

    private class LogMKViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagView: TextView = itemView.findViewById(R.id.logmk_tag)
        var messageView: TextView = itemView.findViewById(R.id.logmk_message)
    }
}