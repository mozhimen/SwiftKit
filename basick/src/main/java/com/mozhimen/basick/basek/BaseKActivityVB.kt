package com.mozhimen.basick.basek

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.basek.commons.IBaseKActivity
import com.mozhimen.basick.utilk.UtilKViewDataBinding

abstract class BaseKActivityVB<VB : ViewDataBinding>(
    private val _factory: ViewModelProvider.Factory? = null
) : AppCompatActivity(), IBaseKActivity {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this::class.java, layoutInflater, 0).apply {
            lifecycleOwner = this@BaseKActivityVB
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFlag()
        initLayout()
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        vb.unbind()
        super.onDestroy()
    }

    override fun initFlag() {

    }

    override fun initLayout() {
        setContentView(vb.root)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}