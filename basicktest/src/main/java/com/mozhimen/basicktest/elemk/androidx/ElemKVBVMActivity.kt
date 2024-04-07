package com.mozhimen.basicktest.elemk.androidx

import androidx.lifecycle.SavedStateViewModelFactory
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDBVM
import com.mozhimen.basicktest.databinding.ActivityElemkVbvmBinding

class ElemKVBVMActivity : BaseActivityVDBVM<ActivityElemkVbvmBinding, ElemKViewModel>() {

    override fun bindViewVM(vb: ActivityElemkVbvmBinding) {
        vdb.elemkVbvm = vm
    }

    override fun getViewModelProviderFactory() =
    SavedStateViewModelFactory(application, this)
}