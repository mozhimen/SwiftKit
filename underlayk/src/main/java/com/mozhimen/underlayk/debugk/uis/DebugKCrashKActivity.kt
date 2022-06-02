package com.mozhimen.underlayk.debugk.uis

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.crashk.CrashKMgr
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.databinding.DebugkActivityCrashkBinding
import com.mozhimen.underlayk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.underlayk.debugk.mos.DebugKCrashKMo

/**
 * @ClassName DebugKCrashKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/25 23:00
 * @Version 1.0
 */
class DebugKCrashKActivity : BaseKActivity<DebugkActivityCrashkBinding, BaseKViewModel>(R.layout.debugk_activity_crashk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    private val _dataSets = ArrayList<DebugKCrashKMo>()
    override fun initView(savedInstanceState: Bundle?) {
        val crashFiles = CrashKMgr.instance.getCrashFiles()

        crashFiles.forEach {
            _dataSets.add(DebugKCrashKMo(it.name, it))
        }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkCrashkRecycler.addItemDecoration(decoration)

        vb.debugkCrashkRecycler.layoutManager = LinearLayoutManager(this)
        val adapterKRecycler =
            AdapterKRecycler<DebugKCrashKMo, DebugkItemCrashkFileBinding>(
                _dataSets,
                R.layout.debugk_item_crashk_file,
                com.mozhimen.underlayk.BR.itemDebugKCrashK
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
        vb.debugkCrashkRecycler.adapter = adapterKRecycler
    }
}
