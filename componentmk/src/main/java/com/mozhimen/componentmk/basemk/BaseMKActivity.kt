package com.mozhimen.componentmk.basemk

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.componentmk.basemk.coms.BaseMKIAction
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName BaseMKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 18:47
 * @Version 1.0
 */
abstract class BaseMKActivity<VB : ViewDataBinding, VM : BaseMKViewModel>(private val layoutId: Int) :
    AppCompatActivity(), BaseMKIAction {

    companion object {
        private const val TAG = "BaseMKActivity"
    }

    lateinit var vb: VB
    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFlag()
        initial()
        initData(savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.unbind()
    }

    override fun initFlag() {}

    private fun initial() {
        val superClass: Type? = this::class.java.genericSuperclass
        if (superClass != null && superClass is ParameterizedType) {
            val arguments: Array<Type> = superClass.actualTypeArguments
            if (arguments.isNotEmpty()) {
                vb = DataBindingUtil.setContentView(this, layoutId)
                vb.lifecycleOwner = this
                vm = ViewModelProvider(this).get(arguments[1] as Class<VM>)
                assignVM()
            }
        }
    }

    override fun assignVM() {}

    override fun initData(savedInstanceState: Bundle?) {}

    override fun initView() {}


}