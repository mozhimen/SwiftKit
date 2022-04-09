package com.mozhimen.app.uicorek.btnk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityBtnkBinding
import com.mozhimen.uicorek.btnk.SwitchApple

class BtnKActivity : AppCompatActivity() {
    private lateinit var btnKSwitchApple: SwitchApple
    private val vb by lazy { ActivityBtnkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        btnKSwitchApple = vb.btnkSwitchApple
        btnKSwitchApple.setDefaultStatus(false)
        btnKSwitchApple.setOnSwitchListener(object :
            SwitchApple.SwitchAppleCallback {
            override fun switch(status: Boolean) {
                Log.i("BtnKActivity", "btnk_switchApple status $status")
            }
        })
    }
}