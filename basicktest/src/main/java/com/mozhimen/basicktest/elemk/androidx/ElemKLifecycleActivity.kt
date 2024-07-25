package com.mozhimen.basicktest.elemk.androidx

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDBVM
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basicktest.databinding.ActivityElemkLifecycleBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.CopyOnWriteArrayList

class ElemKLifecycleActivity : BaseActivityVDBVM<ActivityElemkLifecycleBinding, ElemkLifecycleViewModel>(), Starter.IStarterListener {
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
            startContext<ElemKLifecycleActivity2>()
        }
        Starter.startTask()
    }

    override fun bindViewVM(vdb: ActivityElemkLifecycleBinding) {

    }
}

object Starter : IUtilK {
    interface IStarterListener {
        fun onChange(num: Int)
    }

    ////////////////////////////////////////////////////////////

    private var _listeners = CopyOnWriteArrayList<IStarterListener>()

    @Volatile
    private var _num = 0

    ////////////////////////////////////////////////////////////

    fun getNum(): Int =
        _num

    @Synchronized
    fun addNum() {
        _num++
        onChange(_num)
    }

    fun addListener(listener: IStarterListener) {
        if (!_listeners.contains(listener))
            _listeners.add(listener)
    }

    fun removeListener(listener: IStarterListener) {
        if (_listeners.contains(listener))
            _listeners.remove(listener)
    }

    fun onChange(num: Int) {
        _listeners.forEach {
            it.onChange(num)
        }
    }

    fun startTask() {
        TaskKExecutor.execute(TAG) {
            while (_num < 100) {
                addNum()
                Thread.sleep(100)
            }
        }
    }
}