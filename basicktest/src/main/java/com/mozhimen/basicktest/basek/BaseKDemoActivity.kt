package com.mozhimen.basicktest.basek

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basicktest.databinding.ActivityBasekDemoBinding

class BaseKDemoActivity :
    BaseKActivityVBVM<ActivityBasekDemoBinding, BaseKDemoViewModel>() {

    override fun bindViewVM(vb: ActivityBasekDemoBinding) {
        vb.basekDemoVm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        vb.basekDemoBtn.setOnClickListener {
            vm.genNum()
        }
    }
}