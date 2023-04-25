package com.mozhimen.abilityktest.scank

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mozhimen.abilityk.scank.ScanKQR
import com.mozhimen.abilityktest.databinding.ActivityScankBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext

class ScanKActivity : BaseActivityVB<ActivityScankBinding>() {

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        VB.scankDemoBtnCreate.setOnClickListener {
            if (VB.scankDemoEdit.text.isEmpty()) {
                Toast.makeText(this, "请输入code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            VB.scankDemoImg.setImageBitmap(
                ScanKQR.createQRCodeBitmap(
                    VB.scankDemoEdit.text.toString(),
                    VB.scankDemoImg.width
                )
            )
        }
    }

    fun goScanKHSV(view: View) {
        startContext<ScanKHSVActivity>()
    }

    fun goScanKFace(view: View) {
        startContext<ScanKFaceActivity>()
    }
}
