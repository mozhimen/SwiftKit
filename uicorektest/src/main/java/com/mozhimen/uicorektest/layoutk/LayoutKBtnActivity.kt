package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorek.layoutk.btn.LayoutKBtnSwitch
import com.mozhimen.uicorektest.databinding.ActivityLayoutkBtnBinding

/**
 * @ClassName LayoutKBtnActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 22:52
 * @Version 1.0
 */
class LayoutKBtnActivity : BaseActivityVB<ActivityLayoutkBtnBinding>() {
    private lateinit var _layoutKBtnSwitch: LayoutKBtnSwitch

    override fun initView(savedInstanceState: Bundle?) {
        _layoutKBtnSwitch = vb.layoutkBtnSwitch
        _layoutKBtnSwitch.setDefaultStatus(false)
        _layoutKBtnSwitch.setOnSwitchListener { status ->
            Log.i(TAG, "btnk_switch status $status")
        }
    }
}
