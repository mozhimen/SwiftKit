package com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseBarActivity
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 15:26
 * @Version 1.0
 */
abstract class BaseBarActivityVDB<VDB : ViewDataBinding> : BaseBarActivity() {

    protected val vdb: VDB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VDB>(this::class.java, layoutInflater/*, 0*/).apply {
            lifecycleOwner = this@BaseBarActivityVDB
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

    override fun initLayout() {
        setContentView(vdb.root)
        super.initLayout()
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }
}