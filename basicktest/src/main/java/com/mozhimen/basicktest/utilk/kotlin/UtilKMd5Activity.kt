package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.java.io.UtilKFileFormat
import com.mozhimen.basick.utilk.java.io.isFileExist
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basicktest.databinding.ActivityUtilkMd5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class UtilKMd5Activity : BaseActivityVDB<ActivityUtilkMd5Binding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkMd5Btn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d(TAG, "initView: ${UtilKDate.getNowDate()}")
                val time = System.currentTimeMillis()
                val file = File(cacheDir.absolutePath + "/lelejoy_Descenders_1.5_com.noodlecake.descenders_aligned_signed_isNeedObb_false.apk")
                if (file.isFileExist()) {
                    Log.d(TAG, "initView: ${UtilKFileFormat.file2strMd5(file)}")
                }
                Log.d(TAG, "initView: ${UtilKDate.getNowDate()}")
                Log.d(TAG, "initView: cost ${(System.currentTimeMillis() - time).toFloat() / 60000f}m")
            }
        }
    }
}