package com.mozhimen.basicktest.utilk

import android.Manifest
import android.os.Bundle
import android.os.Environment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basicktest.BR
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkFileBinding
import com.mozhimen.basicktest.databinding.ItemUtilkFileLogBinding
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.uicorek.recyclerk.RecyclerKVBAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@APermissionK([Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
class UtilKFileActivity : BaseActivityVB<ActivityUtilkFileBinding>() {
    private lateinit var _adapterKRecycler: RecyclerKVBAdapter<UtilKFileLogBean, ItemUtilkFileLogBinding>
    private val _logs = arrayListOf(
        UtilKFileLogBean(0, "start file process >>>>>")
    )

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                vb.utilkFileRecycler.layoutManager = LinearLayoutManager(this)
                _adapterKRecycler = RecyclerKVBAdapter( _logs, R.layout.item_utilk_file_log, BR.item_utilk_file_log)
                vb.utilkFileRecycler.adapter = _adapterKRecycler

                super.initData(savedInstanceState)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            "section file".log()
            "filePath getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ${this@UtilKFileActivity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath}".log()
            val deviceInfoPath = this@UtilKFileActivity.filesDir.absolutePath + "/deviceInfo"
            "isFile deviceInfo ${UtilKFile.isFile(deviceInfoPath)}".log()
            val deviceInfo1Path = this@UtilKFileActivity.filesDir.absolutePath + "/deviceInfo1"
            "createFile deviceInfo1 ${UtilKFile.createFile(deviceInfo1Path).absolutePath}".log()
            "deleteFile deviceInfo1 ${UtilKFile.deleteFile(deviceInfo1Path)}".log()
            "getFileSize deviceInfo size ${UtilKFile.getFileSize(deviceInfoPath)}".log()

            val string2File1Path = this@UtilKFileActivity.filesDir.absolutePath + "/tmp1.txt"
            val string2File1Time = System.currentTimeMillis()
            "string2File1 tmp1 ${UtilKFile.string2File("第一行\n第二行", string2File1Path)} time ${System.currentTimeMillis() - string2File1Time}".log()
            val string2File2Path = this@UtilKFileActivity.filesDir.absolutePath + "/tmp2.txt"
            val string2File2Time = System.currentTimeMillis()
            "string2File2 tmp2 ${UtilKFile.string2File2("第一行\n第二行", string2File2Path)} time ${System.currentTimeMillis() - string2File2Time}".log()

            val file2StringTime = System.currentTimeMillis()
            "file2String tmp ${UtilKFile.file2String(string2File1Path)} time ${System.currentTimeMillis() - file2StringTime}".log()

            val copyFileTime = System.currentTimeMillis()
            val destTmpFilePath = this@UtilKFileActivity.filesDir.absolutePath + "/tmp3.txt"
            "copyFile tmp -> tmp3 ${UtilKFile.copyFile(string2File1Path, destTmpFilePath)} time ${System.currentTimeMillis() - copyFileTime}".log()

            "section folder".log()
            val deviceInfoFolder = this@UtilKFileActivity.filesDir.absolutePath
            "isFolder filesDir ${UtilKFile.isFolder(deviceInfoFolder)}".log()
            val createFolderPath = this@UtilKFileActivity.filesDir.absolutePath + "/folder/"
            "createFolder folder ${UtilKFile.createFolder(createFolderPath).absolutePath}".log()
            "deleteFolder folder ${UtilKFile.deleteFolder(createFolderPath)}".log()
        }
    }

    private suspend fun String.log() {
        addLog(this)
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