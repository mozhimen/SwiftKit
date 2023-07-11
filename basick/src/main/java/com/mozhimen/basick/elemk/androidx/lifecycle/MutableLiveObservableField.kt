package com.mozhimen.basick.elemk.androidx.lifecycle

import android.os.Looper
import androidx.annotation.MainThread
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @ClassName ObservableLiveDataField
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/29 18:23
 * @Version 1.0
 */
interface IMutableLiveObservableField<T> {
    fun setValue(value: T)

    /**
     * 可在子线程调用
     */
    fun postValue(value: T)
    fun getValue(): T?
    fun observe(owner: LifecycleOwner, observer: Observer<T>)
    fun observeForever(observer: Observer<T>)
    fun hasObservers(): Boolean
    fun hasActiveObservers(): Boolean
}

class MutableLiveObservableField<T>() : ObservableField<T>(), IMutableLiveObservableField<T> {
    private var _mutableLiveData: MutableLiveData<T>? = null
    private var _version = -1

    /**
     * Wraps the given object and creates an observable object
     * 包装给定对象并创建一个可观察对象
     * @param value The value to be wrapped as an observable.
     */
    constructor(value: T) : this() {
        super.set(value)
        _version++
    }

    override fun setValue(value: T) {
        set(value)
    }

    override fun postValue(value: T) {
        super.set(value)
        _version++
        _mutableLiveData?.postValue(value!!)
    }

    override fun set(value: T) {
        super.set(value)
        _version++
        _mutableLiveData?.let {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                it.value = value
            } else it.postValue(value)
        }
    }

    override fun getValue(): T? =
        get()

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        activeMutableLiveData()
        _mutableLiveData?.observe(owner, observer)
    }

    @MainThread
    override fun observeForever(observer: Observer<T>) {
        activeMutableLiveData()
        _mutableLiveData?.observeForever(observer)
    }

    override fun hasObservers(): Boolean =
        _mutableLiveData?.hasObservers() ?: false

    override fun hasActiveObservers(): Boolean =
        _mutableLiveData?.hasActiveObservers() ?: false

    private fun activeMutableLiveData() {
        if (_mutableLiveData == null) {
            _mutableLiveData = MutableLiveData()
            if (_version > -1) _mutableLiveData!!.value = get()
        }
    }
}