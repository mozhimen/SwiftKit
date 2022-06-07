package com.mozhimen.app.abilityk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.abilityk.cameraxk.CameraXKActivity
import com.mozhimen.app.abilityk.hotfixk.HotFixKActivity
import com.mozhimen.app.abilityk.netk.NetKActivity
import com.mozhimen.app.abilityk.scank.ScanKActivity
import com.mozhimen.app.abilityk.transk.TransKActivity
import com.mozhimen.app.databinding.ActivityAbilitykBinding
import com.mozhimen.basick.extsk.start

class AbilityKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityAbilitykBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun goCameraXK(view: View) {
        start<CameraXKActivity>()
    }

    fun goHotfixK(view: View) {
        start<HotFixKActivity>()
    }

    fun goScanK(view: View) {
        start<ScanKActivity>()
    }

    fun goNetK(view: View) {
        start<NetKActivity>()
    }

    fun goTransK(view: View) {
        start<TransKActivity>()
    }
}