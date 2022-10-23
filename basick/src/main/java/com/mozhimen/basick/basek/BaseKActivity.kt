package com.mozhimen.basick.basek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.basek.commons.IBaseKActivity
import com.mozhimen.basick.basek.commons.IBaseKViewDataBinding
import com.mozhimen.basick.utilk.UtilKGeneric
import com.mozhimen.basick.utilk.UtilKViewDataBinding
import com.mozhimen.basick.utilk.UtilKViewModel

/**
 * @ClassName BaseKActivity
 * @Description class BaseKDemoActivity :
 * BaseKActivity<ActivityBaseKActivityBinding, BaseKDemoViewModel>(R.layout.activity_basek_activity) {
 * override fun assignVM() {vb.vm = vm}}
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 18:47
 * @Version 1.0
 */
abstract class BaseKActivity<VB : ViewDataBinding, VM : ViewModel>(private val _factory: ViewModelProvider.Factory? = null) :
    AppCompatActivity(), IBaseKActivity, IBaseKViewDataBinding<VB> {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this, layoutInflater, 0).apply {
            lifecycleOwner = this@BaseKActivity
        }
    }
    protected val vm: VM by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewModel.get<VM>(this, _factory, 1).apply {
            vb.bindViewVM()
        }
    }

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

    override fun initFlag() {}

    override fun initLayout() {
        setContentView(vb.root)
    }

    override fun initData(savedInstanceState: Bundle?) {}
}