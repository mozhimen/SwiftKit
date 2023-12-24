package com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseSaveStateActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding

/**
 * @ClassName BaseSaveStateActivityVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/26 15:00
 * @Version 1.0
 */
abstract class BaseSaveStateActivityVB<VB : ViewDataBinding> : BaseSaveStateActivity(), IActivity {

    protected val vb: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        UtilKViewDataBinding.get<VB>(this::class.java, layoutInflater/*, 0*/).apply {
            lifecycleOwner = this@BaseSaveStateActivityVB
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