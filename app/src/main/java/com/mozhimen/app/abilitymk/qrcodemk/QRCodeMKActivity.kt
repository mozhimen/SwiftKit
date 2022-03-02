package com.mozhimen.app.abilitymk.qrcodemk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mozhimen.abilitymk.qrcodemk.QRCodeMK
import com.mozhimen.app.databinding.ActivityQrcodemkBinding

class QRCodeMKActivity : AppCompatActivity() {
    private val vb: ActivityQrcodemkBinding by lazy { ActivityQrcodemkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.qrcodemkBtn.setOnClickListener {
            if (vb.qrcodemkEdit.text.isEmpty()) {
                Toast.makeText(this, "请输入code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            vb.qrcodemkImg.setImageBitmap(QRCodeMK.createQRCode(vb.qrcodemkEdit.text.toString(), vb.qrcodemkImg.width))
        }
    }
}