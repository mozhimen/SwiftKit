package com.mozhimen.underlayk.debugk.uis

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.logk.printers.PrinterFile
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.underlayk.BR
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.DebugkActivityLogkBinding
import com.mozhimen.underlayk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.underlayk.debugk.mos.DebugKCrashKMo

class DebugKLogKActivity : BaseKActivity<DebugkActivityLogkBinding, BaseKViewModel>(R.layout.debugk_activity_logk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    private val _dataSets = ArrayList<DebugKCrashKMo>()
    override fun initView(savedInstanceState: Bundle?) {
        val logFiles = PrinterFile.getInstance(0).getLogFiles()

        logFiles.forEach {
            _dataSets.add(DebugKCrashKMo(it.name, it))
        }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkLogkRecycler.addItemDecoration(decoration)

        vb.debugkLogkRecycler.layoutManager = LinearLayoutManager(this)
        val adapterKRecycler =
            AdapterKRecycler<DebugKCrashKMo, DebugkItemCrashkFileBinding>(
                _dataSets,
                R.layout.debugk_item_crashk_file,
                BR.itemDebugKCrashK
            ) { holder, itemData, _ ->
                holder.binding.debugkCrashkFileShare.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra("subject", "")
                    intent.putExtra("body", "")

                    val uri = UtilKFile.file2Uri(itemData.file)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)//????????????
                    if (itemData.file.name.endsWith(".txt")) {
                        intent.type = "text/plain"//?????????
                    } else {
                        intent.type = "application/octet-stream" //??????????????????
                    }
                    startActivity(Intent.createChooser(intent, "??????Crash ????????????"))
                }
            }
        vb.debugkLogkRecycler.adapter = adapterKRecycler
    }
}