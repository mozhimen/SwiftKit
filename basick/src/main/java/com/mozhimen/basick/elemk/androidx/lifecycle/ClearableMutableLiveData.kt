package com.mozhimen.basick.elemk.androidx.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @ClassName SimpleLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/26 16:29
 * @Version 1.0
 */
class ClearableMutableLiveData<T> : MutableLiveData<T>() {
    private var _observers: MutableList<Observer<in T>>? = null

    override fun observeForever(observer: Observer<in T?>) {
        super.observeForever(observer)
        if (_observers == null) _observers = ArrayList()
        _observers!!.add(observer)
    }

    fun clearObservers() {
        if (!_observers.isNullOrEmpty()) {
            for (observer in _observers!!) removeObserver(observer)
            _observers!!.clear()
        }
        _observers = null
    }
}