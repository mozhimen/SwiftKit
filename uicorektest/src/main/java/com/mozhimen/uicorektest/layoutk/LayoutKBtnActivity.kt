package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorek.layoutk.btn.LayoutKBtnSwitchApple
import com.mozhimen.uicorektest.databinding.ActivityLayoutkBtnBinding

/**
 * @ClassName LayoutKBtnActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 22:52
 * @Version 1.0
 */
class LayoutKBtnActivity : BaseKActivityVB<ActivityLayoutkBtnBinding>() {
    private lateinit var _layoutKBtnSwitchApple: LayoutKBtnSwitchApple

    override fun initData(savedInstanceState: Bundle?) {
        _layoutKBtnSwitchApple = vb.layoutkBtnSwitchApple
        _layoutKBtnSwitchApple.setDefaultStatus(false)
        _layoutKBtnSwitchApple.setOnSwitchListener(object :
            LayoutKBtnSwitchApple.ISwitchAppleListener {
            override fun switch(status: Boolean) {
                Log.i("BtnKActivity", "btnk_switchApple status $status")
            }
        })
    }
}