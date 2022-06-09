package com.mozhimen.app.abilityk.scank

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.huawei.hms.ml.scan.HmsScan
import com.mozhimen.abilityk.scank.*
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityScankBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.fromJson
import com.mozhimen.basick.extsk.start

class ScanKActivity : BaseKActivity<ActivityScankBinding, BaseKViewModel>(R.layout.activity_scank) {

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        vb.scankDemoBtnCreate.setOnClickListener {
            if (vb.scankDemoEdit.text.isEmpty()) {
                Toast.makeText(this, "请输入code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            vb.scankDemoImg.setImageBitmap(
                ScanKQR.createQRCode(
                    vb.scankDemoEdit.text.toString(),
                    vb.scankDemoImg.width
                )
            )
        }
        val activityResultLauncher1 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val hmsScan: HmsScan? =
                        result.data?.getStringExtra(ScanKQRActivity.SCANK_ACTIVITY_RESULT_PARAM)
                            ?.fromJson(HmsScan::class.java) ?: kotlin.run {
                            Log.e(TAG, "initView: loss scanActivity params")
                            null
                        }
                    hmsScan?.let {
                        vb.scankDemoEdit.setText(it.originalValue)
                        vb.scankDemoImg.setImageBitmap(it.originalBitmap)
                    }
                }
            }
        val activityResultLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val scanKQR2Result: ScanKQRActivity2.ScanK2Result? =
                        result.data?.getStringExtra(ScanKQRActivity2.SCANK2_ACTIVITY_RESULT_PARAM)
                            ?.fromJson(ScanKQRActivity2.ScanK2Result::class.java) ?: kotlin.run {
                            Log.e(TAG, "initView: loss scanActivity2 params")
                            null
                        }
                    scanKQR2Result?.let {
                        val colors = ScanKHSV.colorAnalyze(it.bitmap)
                        var maxColor = ""
                        colors?.let {
                            maxColor = colors.find { color -> color.second == colors.maxOf { p -> p.second } }?.first?.colorName ?: ""
                        }
                        vb.scankDemoEdit.setText("$maxColor ${it.hmsScan.originalValue}")
                        vb.scankDemoImg.setImageBitmap(it.bitmap)
                    }
                }
            }

        vb.scankDemoBtnScan.setOnClickListener {
            activityResultLauncher1.launch(Intent(this, ScanKQRActivity::class.java))
        }

        vb.scankDemoBtnScan2.setOnClickListener {
            activityResultLauncher2.launch(Intent(this, ScanKQRActivity2::class.java))
        }
    }
}
