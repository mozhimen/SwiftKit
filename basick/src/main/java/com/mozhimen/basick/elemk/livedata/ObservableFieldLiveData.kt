package com.mozhimen.basick.elemk.livedata

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
class ObservableFieldLiveData<T>() : ObservableField<T>() {
    private var _mutableLiveData: MutableLiveData<T>? = null

    companion object {
        const val START_VERSION = -1
    }

    private var _version = START_VERSION

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    constructor(value: T) : this() {
        super.set(value)
        _version++
    }

    fun setValue(value: T) {
        set(value)
    }

    /**
     * 可在子线程调用
     */
    fun postValue(value: T) {
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

    fun getValue() = get()

    fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        activeMutableLiveData()
        _mutableLiveData?.observe(owner, observer)
    }

    @MainThread
    fun observeForever(observer: Observer<T>) {
        activeMutableLiveData()
        _mutableLiveData?.observeForever(observer)
    }

    fun hasActiveObservers() = _mutableLiveData?.hasActiveObservers() ?: false

    fun hasObservers() = _mutableLiveData?.hasObservers() ?: false

    private fun activeMutableLiveData() {
        if (_mutableLiveData == null) {
            _mutableLiveData = MutableLiveData()
            if (_version > START_VERSION) {
                _mutableLiveData!!.value = get()
            }
        }
    }
}