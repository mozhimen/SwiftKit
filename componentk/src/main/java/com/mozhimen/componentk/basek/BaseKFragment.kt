package com.mozhimen.componentk.basek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.componentk.basek.commons.IBaseKAction
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName BaseKFragment
 * @Description class BaseKDemoFragment : BaseKFragment<FragmentBasekFragmentBinding, BaseKDemoViewModel>(R.layout.fragment_basek_fragment) {
 * override fun assignVM() {vb.vm = vm}
 * override fun initView() {}}
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:02
 * @Version 1.0
 */
open class BaseKFragment<VB : ViewDataBinding, VM : BaseKViewModel>(private val layoutId: Int) :
    Fragment(),
    IBaseKAction {
    val TAG = "${this.javaClass.simpleName}>>>>>"

    lateinit var vm: VM
    var _vb: VB? = null
    val vb get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFlag()
        initial()
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.unbind()
        _vb = null
    }

    override fun initFlag() {}

    private fun initial() {
        vb.lifecycleOwner = this

        val superClass: Type? = this.javaClass.genericSuperclass
        if (superClass != null && superClass is ParameterizedType) {
            val arguments: Array<Type> = superClass.actualTypeArguments
            if (arguments.isNotEmpty()) {
                vm = ViewModelProvider(this.requireActivity()).get((arguments[1]) as Class<VM>)
                assignVM()
            }
        }
    }

    override fun assignVM() {}

    override fun initData(savedInstanceState: Bundle?) {}

    override fun initView() {}
}