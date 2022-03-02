package com.mozhimen.app.abilitymk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.abilitymk.cameraxmk.CameraXMKActivity
import com.mozhimen.app.abilitymk.hotfixmk.HotFixMKActivity
import com.mozhimen.app.abilitymk.qrcodemk.QRCodeMKActivity
import com.mozhimen.app.abilitymk.restfulmk.RESTfulMKActivity
import com.mozhimen.app.databinding.ActivityAbilitymkBinding

class AbilityMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityAbilitymkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun goCameraXMK(view: View) {
        startActivity(Intent(this, CameraXMKActivity::class.java))
    }

    fun goHotfixMK(view: View) {
        startActivity(Intent(this, HotFixMKActivity::class.java))
    }

    fun goQrCodeMK(view: View) {
        startActivity(Intent(this, QRCodeMKActivity::class.java))
    }

    fun goRESTfulMK(view: View) {
        startActivity(Intent(this, RESTfulMKActivity::class.java))
    }
}