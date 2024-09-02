package com.mozhimen.mvvmk.bases.activity.databinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.bases.BaseBarActivity
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.mvvmk.utils.UtilKViewDataBinding
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseBarActivityVDB<VDB : ViewDataBinding> : BaseBarActivity() {
    protected val vdb: VDB by lazy_ofNone {
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
            UtilKLogWrapper.e(TAG, "onCreate: e ${e.message}")
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