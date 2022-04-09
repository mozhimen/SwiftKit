package com.mozhimen.basicsk.databusk

import android.os.Looper
import androidx.annotation.MainThread
import androidx.annotation.NonNull
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
class ObservableLiveDataField<T>() : ObservableField<T>() {
    private var nMutableLiveData: MutableLiveData<T>? = null

    companion object {
        const val START_VERSION = -1
    }

    private var mVersion = START_VERSION

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    constructor(value: T) : this() {
        super.set(value)
        mVersion++
    }

    fun setValue(value: T) {
        set(value)
    }

    /**
     * 可在子线程调用
     */
    fun postValue(value: T) {
        super.set(value)
        mVersion++
        nMutableLiveData?.postValue(value)
    }

    override fun set(value: T) {
        super.set(value)
        mVersion++
        nMutableLiveData?.let {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                it.value = value
            } else it.postValue(value)
        }
    }

    fun getValue() = get()

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<T>) {
        activeMutableLiveData()
        nMutableLiveData?.observe(owner, observer)
    }

    @MainThread
    fun observeForever(@NonNull observer: Observer<T>) {
        activeMutableLiveData()
        nMutableLiveData?.observeForever(observer)
    }

    fun hasActiveObservers() = nMutableLiveData?.hasActiveObservers() ?: false

    fun hasObservers() = nMutableLiveData?.hasObservers() ?: false

    private fun activeMutableLiveData() {
        if (nMutableLiveData == null) {
            nMutableLiveData = MutableLiveData()
            if (mVersion > START_VERSION) {
                nMutableLiveData!!.value = get()
            }
        }
    }
}