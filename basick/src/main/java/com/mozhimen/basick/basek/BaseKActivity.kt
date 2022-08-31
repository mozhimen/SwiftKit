package com.mozhimen.basick.basek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.mozhimen.basick.basek.commons.IBaseKActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName BaseKActivity
 * @Description class BaseKDemoActivity :
 * BaseKActivity<ActivityBasekActivityBinding, BaseKDemoViewModel>(R.layout.activity_basek_activity) {
 * override fun assignVM() {vb.vm = vm}}
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 18:47
 * @Version 1.0
 */
open class BaseKActivity<VB : ViewDataBinding, VM : BaseKViewModel>(private val layoutId: Int) :
    AppCompatActivity(), IBaseKActivity {

    val TAG = "${this.javaClass.simpleName}>>>>>"
    lateinit var vb: VB
    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFlag()
        initial()
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.unbind()
    }

    override fun initFlag() {}

    private fun initial() {
        vb = DataBindingUtil.setContentView(this, layoutId)
        vb.lifecycleOwner = this

        val superClass: Type? = this.javaClass.genericSuperclass
        if (superClass != null && superClass is ParameterizedType) {
            val arguments: Array<Type> = superClass.actualTypeArguments
            if (arguments.isNotEmpty()) {
                vm = ViewModelProvider(this).get(arguments[1] as Class<VM>)
                injectVM()
            }
        }
    }

    override fun injectVM() {}

    override fun initData(savedInstanceState: Bundle?) {}

    override fun initView(savedInstanceState: Bundle?) {}
}