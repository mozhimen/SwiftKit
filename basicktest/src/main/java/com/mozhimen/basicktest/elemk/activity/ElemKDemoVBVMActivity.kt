package com.mozhimen.basicktest.elemk.activity

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVBVM
import com.mozhimen.basicktest.elemk.viewmodel.ElemKDemoViewModel
import com.mozhimen.basicktest.databinding.ActivityElemkDemoVbvmBinding

class ElemKDemoVBVMActivity :
    BaseActivityVBVM<ActivityElemkDemoVbvmBinding, ElemKDemoViewModel>() {

    override fun bindViewVM(vb: ActivityElemkDemoVbvmBinding) {
        vb.elemkVbvm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.elemkVbvmBtn.setOnClickListener {
            vm.genNum()
        }
    }
}