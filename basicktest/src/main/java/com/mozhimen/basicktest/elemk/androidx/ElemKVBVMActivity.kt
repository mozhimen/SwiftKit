package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVBVM
import com.mozhimen.basicktest.databinding.ActivityElemkVbvmBinding

class ElemKVBVMActivity : BaseActivityVBVM<ActivityElemkVbvmBinding, ElemKViewModel>() {

    init {
        _factory = SavedStateViewModelFactory(application, this)
    }

    override fun bindViewVM(vb: ActivityElemkVbvmBinding) {
        vdb.elemkVbvm = vm
    }
}