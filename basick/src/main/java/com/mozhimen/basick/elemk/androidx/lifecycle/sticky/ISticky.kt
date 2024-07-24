package com.mozhimen.basick.elemk.androidx.lifecycle.sticky

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @ClassName IStickyLiveData
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/11 21:44
 * @Version 1.0
 */
interface ISticky<T> {
    fun getStickyVersion(): Int
    fun getStickyValue(): T?
    fun setStickyValue(stickyData: T)
    fun postStickyValue(stickyData: T)
    fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>)
    fun observe(owner: LifecycleOwner, observer: Observer<in T>, isSticky: Boolean)
}