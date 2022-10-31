package com.mozhimen.debugk.global.uis

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.underlayk.logk.temps.LogKPrinterFile
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityLogkBinding
import com.mozhimen.debugk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.debugk.global.mos.MDebugKCrashK

class DebugKLogKActivity : BaseKActivityVB<DebugkActivityLogkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    private val _dataSets = ArrayList<MDebugKCrashK>()
    override fun initView(savedInstanceState: Bundle?) {
        val logFiles = LogKPrinterFile.getInstance(0).getLogFiles()

        logFiles.forEach {
            _dataSets.add(MDebugKCrashK(it.name, it))
        }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkLogkRecycler.addItemDecoration(decoration)

        vb.debugkLogkRecycler.layoutManager = LinearLayoutManager(this)
        val adapterKRecycler =
            AdapterKRecycler<MDebugKCrashK, DebugkItemCrashkFileBinding>(
                _dataSets,
                R.layout.debugk_item_crashk_file,
                BR.itemDebugKCrashK
            ) { holder, itemData, _ ->
                holder.binding.debugkCrashkFileShare.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra("subject", "")
                    intent.putExtra("body", "")

                    val uri = UtilKFile.file2Uri(itemData.file)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)//添加文件
                    if (itemData.file.name.endsWith(".txt")) {
                        intent.type = "text/plain"//纯文本
                    } else {
                        intent.type = "application/octet-stream" //二进制文件流
                    }
                    startActivity(Intent.createChooser(intent, "分享Crash 日志文件"))
                }
            }
        vb.debugkLogkRecycler.adapter = adapterKRecycler
    }
}