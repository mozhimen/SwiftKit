package com.mozhimen.basick.utilk.androidx.lifecycle

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin

/**
 * @ClassName UtilKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/23 0:32
 * @Version 1.0
 */
object UtilKViewModel: IUtilK {
    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VM : ViewModel> get(owner: ViewModelStoreOwner, factory: ViewModelProvider.Factory? = null/*, index: Int = 1*/): VM =
        (UtilKReflectGenericKotlin.getParentGenericType_ofClazz(owner::class.java, ViewModel::class.java)?.let { clazzVM ->
            factory?.let { fac ->
                ViewModelProvider(owner, fac)[clazzVM as Class<VM>]
            } ?: run {
                ViewModelProvider(owner)[clazzVM as Class<VM>]
            }
        } ?: throw Exception("inflate vm fail!")).also { UtilKLogWrapper.d(TAG, "get: $it") }

//    fun <VM : ViewDataBinding> ComponentActivity.createViewModel(position: Int): VM {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//        val viewModel = vbClass[position] as Class<VM>
//        return ViewModelProvider(this).get(viewModel)
//    }

//    fun <VM : ViewDataBinding> ComponentActivity.createViewModel(position: Int): VM {
//        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
//        val viewModel = vbClass[position] as Class<VM>
//        return ViewModelProvider(this).get(viewModel)
//    }
}