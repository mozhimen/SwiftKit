package com.mozhimen.basicktest.elemk.androidx

import androidx.lifecycle.SavedStateViewModelFactory
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDBVM
import com.mozhimen.basicktest.databinding.ActivityElemkVbvmBinding

class ElemKVBVMActivity : BaseActivityVDBVM<ActivityElemkVbvmBinding, ElemKViewModel>() {

    init {
        _factory = SavedStateViewModelFactory(application, this)
    }

    override fun bindViewVM(vb: ActivityElemkVbvmBinding) {
        vdb.elemkVbvm = vm
    }
}