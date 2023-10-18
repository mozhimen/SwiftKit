package com.mozhimen.basick.utilk.androidx.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import kotlin.Exception

/**
 * @ClassName UtilKViewDataBinding
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
object UtilKViewDataBinding {
    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewDataBinding> get(clazz: Class<*>, inflater: LayoutInflater, index: Int = 0): VB =
        UtilKReflectGenericKotlin.getParentGenericTypeByTClazz/*<VB>*//*getParentGenericTypeClazz(clazz, index)*/(clazz, ViewDataBinding::class.java)?.run {
            getDeclaredMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VB
        } ?: throw Exception("inflate activity vb fail!")

    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VB : ViewDataBinding> get(clazz: Class<*>, inflater: LayoutInflater, container: ViewGroup?, index: Int = 0): VB =
        UtilKReflectGenericKotlin.getParentGenericTypeByTClazz/*<VB>*//*getParentGenericTypeClazz(clazz, index)*/(clazz, ViewDataBinding::class.java)?.run {
            getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).invoke(null, inflater, container, false) as VB
        } ?: throw Exception("inflate fragment vb fail!")

    ///////////////////////////////////////////////////////////////////////////////////////

//    fun <VM : ViewDataBinding> ComponentActivity.createViewModel(position: Int): VM {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//        val viewModel = vbClass[position] as Class<VM>
//        return ViewModelProvider(this).get(viewModel)
//    }

//    fun <VB : ViewDataBinding> Any.getViewBinding(inflater: LayoutInflater, position: Int = 0): VB {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//        val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
//        return inflate.invoke(null, inflater) as VB
//    }
//
//
//    fun <VB : ViewDataBinding> Any.getViewBinding(inflater: LayoutInflater, container: ViewGroup?, position: Int = 0): VB {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
//        val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
//        return inflate.invoke(null, inflater, container, false) as VB
//    }
//
//    fun <VM : ViewDataBinding> ComponentActivity.createViewModel(position: Int): VM {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//        val viewModel = vbClass[position] as Class<VM>
//        return ViewModelProvider(this).get(viewModel)
//    }
}