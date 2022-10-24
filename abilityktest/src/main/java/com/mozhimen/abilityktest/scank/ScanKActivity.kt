package com.mozhimen.abilityktest.scank

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.huawei.hms.ml.scan.HmsScan
import com.mozhimen.abilityk.scank.ScanKHSV
import com.mozhimen.abilityk.scank.ScanKQR
import com.mozhimen.abilityk.scank.ScanKQR2Activity
import com.mozhimen.abilityk.scank.ScanKQRActivity
import com.mozhimen.abilityktest.databinding.ActivityScankBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.fromJson
import com.mozhimen.basick.extsk.start

class ScanKActivity : BaseKActivityVB<ActivityScankBinding>() {

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
                ScanKQR.createQRCodeBitmap(
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
                            ?.fromJson<HmsScan>() ?: kotlin.run {
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
                    val scanKQR2Result: ScanKQR2Activity.ScanK2Result? =
                        result.data?.getStringExtra(ScanKQR2Activity.SCANK2_ACTIVITY_RESULT_PARAM)
                            ?.fromJson<ScanKQR2Activity.ScanK2Result>() ?: kotlin.run {
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
            activityResultLauncher2.launch(Intent(this, ScanKQR2Activity::class.java))
        }
    }

    fun goScanKHSV(view: View) {
        start<ScanKHSVActivity>()
    }
}
