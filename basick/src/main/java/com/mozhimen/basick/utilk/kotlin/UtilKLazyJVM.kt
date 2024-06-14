package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.I_AListener

/**
 * @ClassName UtilKLazyJVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/14
 * @Version 1.0
 */
object UtilKLazyJVM {
    @JvmStatic
    fun <T> lazy_ofNone(initializer: I_AListener<T>): Lazy<T> =
        lazy(UtilKLazyThreadSafetyMode.getNONE(), initializer)
}