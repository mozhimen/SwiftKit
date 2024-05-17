package com.mozhimen.basick.utilk.kotlin.properties

import com.mozhimen.basick.elemk.commons.I_Listener
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

/**
 * @ClassName UtilKReadWriteProperty
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKReadWriteProperty {
    @JvmStatic
    inline fun <T> onChangeObservable(
        initialValue: T,
        crossinline onChange: I_Listener
    ): ReadWriteProperty<Any?, T> =
        Delegates.observable(initialValue) { _, old, new -> if (old != new) onChange() }
}