package com.mozhimen.basick.elemk.androidx.fragment.bases.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IFragment
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseDialogFragment
import com.mozhimen.basick.utilk.androidx.databinding.UtilKViewDataBinding

open class BaseDialogFragmentVDB<VDB : ViewDataBinding>(
    /*protected open var _factory: ViewModelProvider.Factory? = null*/
) : BaseDialogFragment(), IActivity, IFragment {

    private var _vdb: VDB? = null
    protected val vdb get() = _vdb!!

    //////////////////////////////////////////////////////////////////////////////

    fun isAlive(): Boolean = !isRemoving && !isDetached && activity != null

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflateView(container)
        _vdb = UtilKViewDataBinding.get<VDB>(this::class.java, inflater, container/*, 0*/).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return vdb.root
    }

    /**
     * 及时释放vb避免内存泄漏
     */
    @CallSuper
    override fun onDestroyView() {
        vdb.unbind()
        _vdb = null
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