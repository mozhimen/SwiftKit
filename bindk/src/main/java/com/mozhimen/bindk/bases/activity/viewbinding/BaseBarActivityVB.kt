package com.mozhimen.bindk.bases.activity.viewbinding

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.bases.BaseBarActivity
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone
import com.mozhimen.bindk.utils.UtilKViewBinding

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