package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorek.layoutk.btn.LayoutKBtnSwitchApple
import com.mozhimen.uicorektest.databinding.ActivityLayoutkBtnBinding

/**
 * @ClassName LayoutKBtnActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 22:52
 * @Version 1.0
 */
class LayoutKBtnActivity : BaseActivityVB<ActivityLayoutkBtnBinding>() {
    private lateinit var _layoutKBtnSwitchApple: LayoutKBtnSwitchApple

    override fun initView(savedInstanceState: Bundle?) {
        _layoutKBtnSwitchApple = vb.layoutkBtnSwitchApple
        _layoutKBtnSwitchApple.setSwitchStatus(false)
        _layoutKBtnSwitchApple.setOnSwitchListener(object :
            LayoutKBtnSwitchApple.ISwitchAppleListener {
            override fun onSwitch(status: Boolean) {
                Log.i(TAG, "btnk_switchApple status $status")
            }
        })
    }
}