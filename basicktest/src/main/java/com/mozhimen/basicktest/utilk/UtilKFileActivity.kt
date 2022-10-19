package com.mozhimen.basicktest.utilk

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basicktest.BR
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKAssets
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkFileBinding
import com.mozhimen.basicktest.databinding.ItemUtilkFileLogBinding
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@PermissionKAnnor([Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
class UtilKFileActivity : BaseKActivity<ActivityUtilkFileBinding, BaseKViewModel>(R.layout.activity_utilk_file) {
    private lateinit var _adapterKRecycler: AdapterKRecycler<UtilKFileLogBean, ItemUtilkFileLogBinding>
    private val _logs = arrayListOf(
        UtilKFileLogBean(0, "start file process >>>>>")
    )

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                vb.utilkFileRecycler.layoutManager = LinearLayoutManager(this)
                _adapterKRecycler = AdapterKRecycler(_logs, R.layout.item_utilk_file_log, BR.item_utilk_file_log)
                vb.utilkFileRecycler.adapter = _adapterKRecycler

                initView(savedInstanceState)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkFileBtnCreateFolder.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val createPath = this@UtilKFileActivity.cacheDir.absolutePath
                addLog("createPath: $createPath")
                UtilKFile.createFolder("$createPath/newFolder")
                addLog("newFolder is exists: " + UtilKFile.isFolderExist("$createPath/newFolder"))
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val file = File(this@UtilKFileActivity.filesDir.absolutePath + "/deviceInfo")
                addLog("destination : ${this@UtilKFileActivity.filesDir.absolutePath}")
                addLog("start copy asset file to cdcard destination")
                UtilKAssets.assetCopyFile("deviceInfo", file.absolutePath)
                addLog("end")
                addLog("after copy path: ${file.absolutePath}, isExist ${UtilKFile.isFileExist(file.absolutePath)}")
                addLog("start read file content")
                val content = UtilKFile.file2String(file.absolutePath)
                addLog("read end")
                addLog("read content: $content")
                val contentArray = content.split("\n")
                addLog("lines: $contentArray")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun addLog(log: String) {
        withContext(Dispatchers.Main) {
            _logs.add(UtilKFileLogBean(_logs.size, "$log..."))
            _adapterKRecycler.onItemDataChanged(_logs)
        }
    }

    data class UtilKFileLogBean(
        val num: Int,
        val log: String
    ) {
        fun getJoinContent(): String = "$num:$log"
    }
}