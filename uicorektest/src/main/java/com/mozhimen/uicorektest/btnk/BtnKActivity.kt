package com.mozhimen.uicorektest.btnk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorek.btnk.SwitchApple
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityBtnkBinding

class BtnKActivity : BaseKActivity<ActivityBtnkBinding, BaseKViewModel>(R.layout.activity_btnk) {
    private lateinit var btnKSwitchApple: SwitchApple

    override fun initData(savedInstanceState: Bundle?) {
        btnKSwitchApple = vb.btnkSwitchApple
        btnKSwitchApple.setDefaultStatus(false)
        btnKSwitchApple.setOnSwitchListener(object :
            SwitchApple.ISwitchAppleListener {
            override fun switch(status: Boolean) {
                Log.i("BtnKActivity", "btnk_switchApple status $status")
            }
        })
    }
}