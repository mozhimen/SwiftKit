package com.mozhimen.debugk.temps

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.underlayk.logk.temps.LogKPrinterFile
import com.mozhimen.basick.utilk.file.UtilKFile
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.debugk.BR
import com.mozhimen.debugk.R
import com.mozhimen.debugk.databinding.DebugkActivityLogkBinding
import com.mozhimen.debugk.databinding.DebugkItemCrashkFileBinding
import com.mozhimen.debugk.mos.MDebugKCrashK
import com.mozhimen.uicorek.recyclerk.RecyclerKVBAdapter

/**

 * if build sdk > N you also add provider and @xml/file_paths

 * AndroidManifest.xml sdk>24
<provider
android:name="androidx.core.content.FileProvider"
android:authorities="包名.fileprovider"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
android:name="android.support.FILE_PROVIDER_PATHS"
android:resource="@xml/file_paths"  />
</provider>

 * file_paths.xml sdk>24
<paths>
<files-path
name="files-path"
path="." />
</paths>

 * @property _dataSets ArrayList<MDebugKCrashK>
 */
class DebugKLogKActivity : BaseActivityVB<DebugkActivityLogkBinding>() {

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
            RecyclerKVBAdapter<MDebugKCrashK, DebugkItemCrashkFileBinding>(
                _dataSets,
                R.layout.debugk_item_crashk_file,
                BR.itemDebugKCrashK
            ) { holder, itemData, _, _ ->
                holder.vb.debugkCrashkFileShare.setOnClickListener {
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