package com.mozhimen.basicktest.elemk.activity

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basicktest.elemk.viewmodel.ElemKViewModel
import com.mozhimen.basicktest.databinding.ActivityElemkVbvmBinding

class ElemKVBVMActivity :
    BaseActivityVBVM<ActivityElemkVbvmBinding, ElemKViewModel>() {

    override fun bindViewVM(vb: ActivityElemkVbvmBinding) {
        vb.elemkVbvm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.elemkVbvmBtn.setOnClickListener {
            vm.genNum()
        }
    }
}