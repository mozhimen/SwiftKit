package com.mozhimen.basick.elemk.activity.commons

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.utilk.UtilKViewDataBinding

abstract class BaseActivityVB<VB : ViewDataBinding>(
    private val _factory: ViewModelProvider.Factory? = null
) : AppCompatActivity(), IActivity {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this::class.java, layoutInflater, 0).apply {
            lifecycleOwner = this@BaseActivityVB
        }
    }

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

    override fun initFlag() {

    }

    @CallSuper
    override fun initLayout() {
        setContentView(vb.root)
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}