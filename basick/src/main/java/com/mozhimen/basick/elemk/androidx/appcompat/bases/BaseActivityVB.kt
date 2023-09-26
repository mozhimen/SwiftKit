package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding
import com.mozhimen.basick.utilk.bases.IUtilK

abstract class BaseActivityVB<VB : ViewDataBinding>(
    protected val _factory: ViewModelProvider.Factory? = null
) : AppCompatActivity(), IActivity, IUtilK {

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this::class.java, layoutInflater, 0).apply {
            lifecycleOwner = this@BaseActivityVB
        }
    }

    ///////////////////////////////////////////////////////////////

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFlag()
        initLayout()
        initData(savedInstanceState)
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