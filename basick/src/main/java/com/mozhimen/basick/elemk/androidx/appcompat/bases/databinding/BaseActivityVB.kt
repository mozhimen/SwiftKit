package com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.official.BaseActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding

abstract class BaseActivityVB<VB : ViewDataBinding> : BaseActivity(), IActivity {

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this::class.java, layoutInflater/*, 0*/).apply {
            lifecycleOwner = this@BaseActivityVB
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
        vb.unbind()
        super.onDestroy()
    }

    ///////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        setContentView(vb.root)
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }
}