package com.mozhimen.basicktest.utilk

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.UtilKAsset
import com.mozhimen.basicktest.BR
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkAssetBinding
import com.mozhimen.basicktest.databinding.ItemUtilkFileLogBinding
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.uicorek.recyclerk.AdapterKRecycler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@APermissionK([Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
class UtilKAssetActivity : BaseActivityVB<ActivityUtilkAssetBinding>() {
    private lateinit var _adapterKRecycler: AdapterKRecycler<UtilKFileActivity.UtilKFileLogBean, ItemUtilkFileLogBinding>
    private val _logs = arrayListOf(
        UtilKFileActivity.UtilKFileLogBean(0, "start asset file process >>>>>")
    )

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                vb.utilkAssetRecycler.layoutManager = LinearLayoutManager(this)
                _adapterKRecycler = AdapterKRecycler(_logs, R.layout.item_utilk_file_log, BR.item_utilk_file_log)
                vb.utilkAssetRecycler.adapter = _adapterKRecycler

                super.initData(savedInstanceState)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            addLog("isFileExists deviceInfo ${UtilKAsset.isFileExists("deviceInfo")}")
            val file2StringTime = System.currentTimeMillis()
            val file2StringContent = UtilKAsset.file2String("deviceInfo")
            addLog("file2String1 deviceInfo $file2StringContent time ${System.currentTimeMillis() - file2StringTime}")
            val txt2StringTime = System.currentTimeMillis()
            val txt2StringContent = UtilKAsset.file2String2("deviceInfo")
            addLog("file2String2 deviceInfo $txt2StringContent time ${System.currentTimeMillis() - txt2StringTime}")
            val txt2String2Time = System.currentTimeMillis()
            val txt2String2Content = UtilKAsset.file2String3("deviceInfo")
            addLog("file2String3 deviceInfo $txt2String2Content time ${System.currentTimeMillis() - txt2String2Time}")
            addLog("start copy file")
            val assetCopyFileTime = System.currentTimeMillis()
            val assetCopyFilePath = UtilKAsset.asset2File("deviceInfo", this@UtilKAssetActivity.cacheDir.absolutePath + "/utilk_asset/")
            addLog("assetCopyFile deviceInfo path $assetCopyFilePath time ${System.currentTimeMillis() - assetCopyFileTime}")
        }
    }

    private suspend fun addLog(log: String) {
        withContext(Dispatchers.Main) {
            _logs.add(UtilKFileActivity.UtilKFileLogBean(_logs.size, "$log..."))
            _adapterKRecycler.onItemDataChanged(_logs)
        }
    }
}