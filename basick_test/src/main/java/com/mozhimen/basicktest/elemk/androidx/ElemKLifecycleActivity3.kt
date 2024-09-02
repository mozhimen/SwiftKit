package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDBVM
import com.mozhimen.taskk.executor.TaskKExecutor
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.basicktest.databinding.ActivityElemkLifecycleBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.CopyOnWriteArrayList

class ElemKLifecycleActivity3 : BaseActivityVDBVM<ActivityElemkLifecycleBinding, ElemkLifecycleViewModel3>(), Starter.IStarterListener {
    override fun initData(savedInstanceState: Bundle?) {
        Starter.addListener(this)
        super.initData(savedInstanceState)
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
            Starter.addNum()
        }
    }

    override fun bindViewVM(vdb: ActivityElemkLifecycleBinding) {

    }
}

