package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basicktest.databinding.ActivityElemkLifecycleBinding
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDBVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ElemKLifecycleActivity2 : BaseActivityVDBVM<ActivityElemkLifecycleBinding, ElemkLifecycleViewModel2>(), Starter.IStarterListener {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        Starter.addListener(this)
    }


    override fun onDestroy() {
        Starter.removeListener(this)
        super.onDestroy()
    }

    override fun onChange(num: Int) {
        vm.setNum(num)
    }


    override fun initView(savedInstanceState: Bundle?) {
        vm.flowState.flowWithLifecycle(this.lifecycle, Lifecycle.State.CREATED).onEach {
            vdb.elemkLifecycleTxt1.text = it.toString()
        }.launchIn(this.lifecycleScope)
        vm.liveDataMutable.observe(this) {
            vdb.elemkLifecycleTxt2.text = it.toString()
        }
        vm.liveDataSticky.observeSticky(this) {
            vdb.elemkLifecycleTxt3.text = it.toString()
        }
        vdb.elemkLifecycleBtn.setOnClickListener {
            startContext<ElemKLifecycleActivity3>()
        }
    }

    override fun bindViewVM(vdb: ActivityElemkLifecycleBinding) {

    }
}

