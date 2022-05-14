package com.mozhimen.app.basicsk.utilk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityUtilkBinding
import com.mozhimen.basicsk.utilk.UtilKBitmap
import com.mozhimen.basicsk.utilk.UtilKVerifyUrl
import com.mozhimen.basicsk.utilk.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UtilKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityUtilkBinding.inflate(layoutInflater) }
    private val url =
        "http://img.crcz.com/allimg/202003/25/1585100748975745.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        lifecycleScope.launch {
            vb.utilKImg.setImageBitmap(UtilKBitmap.url2Bitmap2(url))
        }

        vb.utilkBtnPortVerify.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.isPortValid(vb.utilkTxtPortVerify.text.toString())}".showToast()
        }
    }
}