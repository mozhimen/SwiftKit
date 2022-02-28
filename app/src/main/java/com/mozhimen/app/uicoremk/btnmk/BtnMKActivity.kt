package com.mozhimen.app.uicoremk.btnmk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.uicoremk.btnmk.SwitchApple

class BtnMKActivity : AppCompatActivity() {
    private lateinit var btnMKSwitchApple: SwitchApple
    private val vb by lazy { ActivityBtnmkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        btnMKSwitchApple = vb.btnmkSwitchApple
        btnMKSwitchApple.setDefaultStatus(false)
        btnMKSwitchApple.setOnSwitchListener(object :
            SwitchApple.SwitchAppleCallback {
            override fun switch(status: Boolean) {
                Log.i("BtnMKActivity", "btnmk_switchApple status $status")
            }
        })
    }
}