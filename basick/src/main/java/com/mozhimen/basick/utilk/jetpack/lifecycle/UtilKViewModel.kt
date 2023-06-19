package com.mozhimen.basick.utilk.jetpack.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mozhimen.basick.utilk.java.UtilKGeneric
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
    fun <VM : ViewModel> get(owner: ViewModelStoreOwner, factory: ViewModelProvider.Factory? = null, index: Int = 1): VM =
        UtilKGeneric.getParentGenericTypeClazz(owner::class.java, index)?.let { vmClazz ->
            factory?.let { fac ->
                ViewModelProvider(owner, fac).get(vmClazz as Class<VM>)
            } ?: run {
                ViewModelProvider(owner).get(vmClazz as Class<VM>)
            }
        } ?: throw Exception("inflate vm fail!")
}