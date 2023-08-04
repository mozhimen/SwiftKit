package com.mozhimen.debugk.temps

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.content.createChooser
import com.mozhimen.basick.utilk.android.net.UtilKUri
import com.mozhimen.debugk.R
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.databinding.DebugkActivityCrashkBinding
import com.mozhimen.debugk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.debugk.mos.MDebugKCrashK
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB
import com.mozhimen.underlayk.crashk.CrashKMgr

/**
 * @ClassName DebugKCrashKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/25 23:00
 * @Version 1.0
 */
@AManifestKRequire(CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE)
class DebugKCrashKActivity : BaseActivityVB<DebugkActivityCrashkBinding>() {
    private val _dataSets = ArrayList<MDebugKCrashK>()

    @OptIn(OptInApiInit_InApplication::class)
    override fun initView(savedInstanceState: Bundle?) {
        val crashFiles = CrashKMgr.instance.getCrashFiles()

        for (file in crashFiles) _dataSets.add(MDebugKCrashK(file.name, file))

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(UtilKRes.getDrawable(R.drawable.debugk_crashk_divider)!!)
        vb.debugkCrashkRecycler.addItemDecoration(decoration)

        vb.debugkCrashkRecycler.layoutManager = LinearLayoutManager(this)
        val adapterKRecycler = AdapterKRecyclerVB<MDebugKCrashK, DebugkItemCrashkFileBinding>(
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
                startActivity(intent.createChooser("分享Crash 日志文件"))
            }
        }
        vb.debugkCrashkRecycler.adapter = adapterKRecycler
    }
}
