package com.mozhimen.basick.elemk.androidx.lifecycle.sticky

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @ClassName StickyLiveData
 * @Description
 * 允许指定注册的观察者 是否需要关心黏性事件
 * sticky=true, 如果之前存在已经发送出去的数据, 那么这个observer会收到之前的黏性事件消息
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/12 0:30
 * @Version 1.0
 */
open class MutableLiveDataSticky<T> : MutableLiveData<T>, ISticky<T> {
    @Volatile
    private var _stickyValue: T? = null

    @Volatile
    private var _stickyVersion = 0

    //////////////////////////////////////////////////////////////////////

    constructor(value: T) : super(value) {
        _stickyValue = value
    }

    constructor() : super()

    //////////////////////////////////////////////////////////////////////

    override fun getStickyVersion(): Int =
        _stickyVersion

    override fun getStickyValue(): T? =
        _stickyValue

    override fun setStickyValue(stickyData: T) {
        _stickyValue = stickyData
        setValue(stickyData)//主线程去发送数据
    }

    override fun postStickyValue(stickyData: T) {
        _stickyValue = stickyData
        postValue(stickyData)//不受线程的限制
    }

    override fun setValue(value: T) {
        _stickyVersion++
        super.setValue(value)
    }

    override fun postValue(value: T) {
        _stickyVersion++
        super.postValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverSticky(this, false, observer))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>, isSticky: Boolean) {
        super.observe(owner, ObserverSticky(this, isSticky, observer))
    }

    override fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverSticky(this, true, observer))
    }
}