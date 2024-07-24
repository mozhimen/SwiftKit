package com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding
import com.mozhimen.basick.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

abstract class BaseActivityVDB<VDB : ViewDataBinding> : BaseActivity(), IActivity {

    protected val vdb: VDB by lazy_ofNone {
        UtilKViewDataBinding.get<VDB>(this::class.java, layoutInflater/*, 0*/).apply {
            lifecycleOwner = this@BaseActivityVDB
        }
    }

    ///////////////////////////////////////////////////////////////

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initFlag()
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @CallSuper
    override fun onDestroy() {
        vdb.unbind()
        super.onDestroy()
    }

    ///////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        setContentView(vdb.root)
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }
}