package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.basicktest.BR
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkAssetBinding
import com.mozhimen.basicktest.databinding.ItemUtilkFileLogBinding
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.kotlin.UtilKStrAssetFileName
import com.mozhimen.basicktest.utilk.java.UtilKFileActivity
import com.mozhimen.uicorek.adapterk.quick.AdapterKQuickRecyclerVB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE)
@APermissionCheck(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE)
class UtilKAssetActivity : BaseActivityVB<ActivityUtilkAssetBinding>() {
    private lateinit var _adapterKRecycler: AdapterKQuickRecyclerVB<UtilKFileActivity.UtilKFileLogBean, ItemUtilkFileLogBinding>
    private val _logs = arrayListOf(
        UtilKFileActivity.UtilKFileLogBean(0, "start asset file process >>>>>")
    )

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                vb.utilkAssetRecycler.layoutManager = LinearLayoutManager(this)
                _adapterKRecycler = AdapterKQuickRecyclerVB<UtilKFileActivity.UtilKFileLogBean, ItemUtilkFileLogBinding>(
                    _logs,
                    R.layout.item_utilk_file_log,
                    BR.item_utilk_file_log
                )
                vb.utilkAssetRecycler.adapter = _adapterKRecycler

                super.initData(savedInstanceState)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            addLog("isFileExists deviceInfo ${UtilKAsset.isAssetExists("deviceInfo")}")
            val file2StrTime = System.currentTimeMillis()
            val file2StrContent = UtilKStrAssetFileName.strAssetFileName2str("deviceInfo")
            addLog("file2Str1 deviceInfo $file2StrContent time ${System.currentTimeMillis() - file2StrTime}")
            val txt2StrTime = System.currentTimeMillis()
            val txt2StrContent = UtilKStrAssetFileName.strAssetFileName2str2("deviceInfo")
            addLog("file2Str2 deviceInfo $txt2StrContent time ${System.currentTimeMillis() - txt2StrTime}")
            val txt2Str2Time = System.currentTimeMillis()
            val txt2Str2Content = UtilKStrAssetFileName.strAssetFileName2str3("deviceInfo")
            addLog("file2Str3 deviceInfo $txt2Str2Content time ${System.currentTimeMillis() - txt2Str2Time}")
            addLog("start copy file")
            val assetCopyFileTime = System.currentTimeMillis()
            val assetCopyFile = UtilKStrAssetFileName.strAssetFileName2file("deviceInfo", this@UtilKAssetActivity.cacheDir.absolutePath + "/utilk_asset/")
            addLog("assetCopyFile deviceInfo path ${assetCopyFile?.absolutePath} time ${System.currentTimeMillis() - assetCopyFileTime}")
        }
    }

    private suspend fun addLog(log: String) {
        withContext(Dispatchers.Main) {
            _logs.add(UtilKFileActivity.UtilKFileLogBean(_logs.size, "$log..."))
            _adapterKRecycler.refreshDatas(_logs)
        }
    }
}