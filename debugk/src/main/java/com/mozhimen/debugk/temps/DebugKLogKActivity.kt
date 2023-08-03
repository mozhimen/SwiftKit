package com.mozhimen.debugk.temps

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.content.applyCreateChooser
import com.mozhimen.basick.utilk.android.net.UtilKUri
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityLogkBinding
import com.mozhimen.debugk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.debugk.mos.MDebugKCrashK
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB
import com.mozhimen.underlayk.logk.temps.printer.LogKPrinterFile

/**
 * @property _dataSets ArrayList<MDebugKCrashK>
 */
class DebugKLogKActivity : BaseActivityVB<DebugkActivityLogkBinding>() {

    private val _dataSets = ArrayList<MDebugKCrashK>()
    override fun initView(savedInstanceState: Bundle?) {
        val logFiles = LogKPrinterFile.getInstance(0).getLogFiles()

        for (logFile in logFiles) _dataSets.add(MDebugKCrashK(logFile.name, logFile))

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkLogkRecycler.addItemDecoration(decoration)

        vb.debugkLogkRecycler.layoutManager = LinearLayoutManager(this)
        val adapterKRecycler =
                AdapterKRecyclerVB<MDebugKCrashK, DebugkItemCrashkFileBinding>(
                        _dataSets,
                        R.layout.debugk_item_crashk_file,
                        BR.itemDebugKCrashK
                ) { holder, itemData, _, _ ->
                    holder.vb.debugkCrashkFileShare.setOnClickListener {
                        val intent = Intent(CIntent.ACTION_SEND)
                        intent.putExtra("subject", "")
                        intent.putExtra("body", "")

                        val uri = UtilKUri.file2uri(itemData.file)
                        intent.putExtra(CIntent.EXTRA_STREAM, uri)//添加文件
                        if (itemData.file.name.endsWith(".txt")) {
                            intent.type = "text/plain"//纯文本
                        } else {
                            intent.type = "application/octet-stream" //二进制文件流
                        }
                        startActivity(intent.applyCreateChooser("分享Log 日志文件"))
                    }
                }
        vb.debugkLogkRecycler.adapter = adapterKRecycler
    }
}