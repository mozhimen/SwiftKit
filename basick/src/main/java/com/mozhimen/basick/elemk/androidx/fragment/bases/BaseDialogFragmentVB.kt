package com.mozhimen.basick.elemk.androidx.fragment.bases

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IFragment
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding
import com.mozhimen.basick.utilk.bases.IUtilK

open class BaseDialogFragmentVB<VB : ViewDataBinding>(
    /*protected open var _factory: ViewModelProvider.Factory? = null*/
) : BaseDialogFragment(), IActivity, IFragment {

    private var _vb: VB? = null
    protected val vb get() = _vb!!

    //////////////////////////////////////////////////////////////////////////////

    fun isAlive(): Boolean = !isRemoving && !isDetached && activity != null

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflateView(container)
        _vb = UtilKViewDataBinding.get<VB>(this::class.java, inflater, container/*, 0*/).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return vb.root
    }

    /**
     * 及时释放vb避免内存泄漏
     */
    @CallSuper
    override fun onDestroyView() {
        vb.unbind()
        _vb = null
        super.onDestroyView()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun inflateView(viewGroup: ViewGroup?) {

    }

    override fun initFlag() {

    }

    override fun initLayout() {

    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initObserver() {

    }
}