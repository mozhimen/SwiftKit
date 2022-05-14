package com.mozhimen.app.abilityk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.abilityk.cameraxk.CameraXKActivity
import com.mozhimen.app.abilityk.hotfixk.HotFixKActivity
import com.mozhimen.app.abilityk.netk.NetKActivity
import com.mozhimen.app.abilityk.scank.ScanKActivity
import com.mozhimen.app.databinding.ActivityAbilitykBinding

class AbilityKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityAbilitykBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun goCameraXK(view: View) {
        startActivity(Intent(this, CameraXKActivity::class.java))
    }

    fun goHotfixK(view: View) {
        startActivity(Intent(this, HotFixKActivity::class.java))
    }

    fun goScanK(view: View) {
        startActivity(Intent(this, ScanKActivity::class.java))
    }

    fun goNetK(view: View) {
        startActivity(Intent(this, NetKActivity::class.java))
    }
}