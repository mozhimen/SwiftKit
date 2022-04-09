package com.mozhimen.app.abilityk.qrcodek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mozhimen.abilityk.qrcodek.QRCodeK
import com.mozhimen.app.databinding.ActivityQrcodekBinding

class QRCodeKActivity : AppCompatActivity() {
    private val vb: ActivityQrcodekBinding by lazy { ActivityQrcodekBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.qrcodekBtn.setOnClickListener {
            if (vb.qrcodekEdit.text.isEmpty()) {
                Toast.makeText(this, "请输入code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            vb.qrcodekImg.setImageBitmap(QRCodeK.createQRCode(vb.qrcodekEdit.text.toString(), vb.qrcodekImg.width))
        }
    }
}