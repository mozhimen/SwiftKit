package com.mozhimen.basick.elemk.activity.bases

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.activity.commons.IActivity
import com.mozhimen.basick.utilk.jetpack.databinding.UtilKViewDataBinding

abstract class BaseActivityVB<VB : ViewDataBinding>(
    private val _factory: ViewModelProvider.Factory? = null
) : AppCompatActivity(), IActivity {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    protected val VB: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
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
        VB.unbind()
        super.onDestroy()
    }

    override fun initFlag() {

    }

    @CallSuper
    override fun initLayout() {
        setContentView(VB.root)
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}