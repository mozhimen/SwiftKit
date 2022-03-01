package com.mozhimen.app.abilitymk.qrcodemk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mozhimen.abilitymk.qrcodemk.QRCodeMK

class QRCodeMKActivity : AppCompatActivity() {
    private lateinit var vb: ActivityQrcodemkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityQrcodemkBinding.inflate(layoutInflater)
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