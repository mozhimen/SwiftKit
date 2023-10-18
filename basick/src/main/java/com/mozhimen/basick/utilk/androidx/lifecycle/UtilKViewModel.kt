package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import java.lang.Exception

/**
 * @ClassName UtilKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/23 0:32
 * @Version 1.0
 */
object UtilKViewModel {
    @JvmStatic
    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <VM : ViewModel> get(owner: ViewModelStoreOwner, factory: ViewModelProvider.Factory? = null, index: Int = 1): VM =
        UtilKReflectGenericKotlin.getParentGenericTypeClazz(owner::class.java, index)?.let { vmClazz ->
            factory?.let { fac ->
                ViewModelProvider(owner, fac)[vmClazz as Class<VM>]
            } ?: run {
                ViewModelProvider(owner)[vmClazz as Class<VM>]
            }
        } ?: throw Exception("inflate vm fail!")
}