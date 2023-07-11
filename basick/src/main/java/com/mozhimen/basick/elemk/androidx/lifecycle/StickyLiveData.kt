package com.mozhimen.basick.elemk.androidx.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IStickyLiveData

/**
 * @ClassName StickyLiveData
 * @Description
 * 允许指定注册的观察者 是否需要关心黏性事件
 * sticky=true, 如果之前存在已经发送出去的数据, 那么这个observer会收到之前的黏性事件消息
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/12 0:30
 * @Version 1.0
 */
open class StickyLiveData<T> : LiveData<T>(), IStickyLiveData<T> {
    private var _stickyValue: T? = null
    private var _stickyVersion = 0

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

    public override fun setValue(value: T) {
        _stickyVersion++
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        _stickyVersion++
        super.postValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, StickyObserver(this, false, observer))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>, isSticky: Boolean) {
        super.observe(owner, StickyObserver(this, isSticky, observer))
    }

    override fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, StickyObserver(this, true, observer))
    }
}