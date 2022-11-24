package com.mozhimen.basick.elemk.fragment.bases

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.elemk.activity.commons.IActivity
import com.mozhimen.basick.utilk.UtilKViewDataBinding

open class BaseFragmentVB<VB : ViewDataBinding>(
    private val _factory: ViewModelProvider.Factory? = null
) : Fragment(), IActivity {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    private var _vb: VB? = null
    protected val vb get() = _vb!!

    fun isAlive(): Boolean = !isRemoving && !isDetached && activity != null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _vb = UtilKViewDataBinding.get<VB>(this::class.java, inflater, container, 0).apply {
            lifecycleOwner = this@BaseFragmentVB
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
        initLayout()
        initData(savedInstanceState)
    }

    override fun initFlag() {

    }

    override fun initLayout() {

    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(TAG, "onAttach")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG, "onPause")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(TAG, "onDetach")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop")
    }
}