package com.mozhimen.uicorektest.btnk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorek.layoutk.btn.LayoutKBtnSwitchApple
import com.mozhimen.uicorektest.databinding.ActivityBtnkBinding

class BtnKActivity : BaseKActivityVB<ActivityBtnkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.btnkPwdEye.setEditText(vb.btnkPwdEyeEdit)
    }
}