package com.mozhimen.basick.elemk.androidx.appcompat.bases.viewbinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseBarActivity
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding
import com.mozhimen.basick.utilk.androidx.viewbinding.UtilKViewBinding
import com.mozhimen.basick.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseBarActivityVB<VB : ViewBinding> : BaseBarActivity() {
    protected val vdb: VB by lazy_ofNone {
        UtilKViewBinding.get<VB>(this::class.java, layoutInflater/*, 0*/)
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