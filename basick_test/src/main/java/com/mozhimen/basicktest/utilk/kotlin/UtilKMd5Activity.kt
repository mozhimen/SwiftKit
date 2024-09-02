package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.lifecycleScope
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.java.io.UtilKFileFormat
import com.mozhimen.kotlin.utilk.java.io.isFileExist
import com.mozhimen.kotlin.utilk.java.util.UtilKDate
import com.mozhimen.kotlin.utilk.java.util.UtilKDateWrapper
import com.mozhimen.basicktest.databinding.ActivityUtilkMd5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class UtilKMd5Activity : BaseActivityVDB<ActivityUtilkMd5Binding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkMd5Btn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                UtilKLogWrapper.d(TAG, "initView: ${UtilKDateWrapper.getNowDate()}")
                val time = System.currentTimeMillis()
                val file = File(cacheDir.absolutePath + "/lelejoy_Descenders_1.5_com.noodlecake.descenders_aligned_signed_isNeedObb_false.apk")
                if (file.isFileExist()) {
                    UtilKLogWrapper.d(TAG, "initView: ${UtilKFileFormat.file2strMd5Hex_use(file)}")
                }
                UtilKLogWrapper.d(TAG, "initView: ${UtilKDateWrapper.getNowDate()}")
                UtilKLogWrapper.d(TAG, "initView: cost ${(System.currentTimeMillis() - time).toFloat() / 60000f}m")
            }
        }
    }
}