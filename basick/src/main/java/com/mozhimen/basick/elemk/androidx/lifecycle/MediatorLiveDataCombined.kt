package com.mozhimen.basick.elemk.androidx.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * @ClassName CombinedLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/10
 * @Version 1.0
 */
class MediatorLiveDataCombined<T, K, S>(
    source1: LiveData<T>,
    source2: LiveData<K>,
    private val _combine: (data1: T, data2: K) -> S
) : MediatorLiveData<S>() {

    private var data1: T? = null
    private var data2: K? = null

    /////////////////////////////////////////////////////////////////////

    init {
        super.addSource(source1) {
            data1 = it
            emitIfNecessary()
        }
        super.addSource(source2) {
            data2 = it
            emitIfNecessary()
        }
    }

    /////////////////////////////////////////////////////////////////////

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> removeSource(toRemote: LiveData<T>) {
        throw UnsupportedOperationException()
    }

    /////////////////////////////////////////////////////////////////////

    private fun emitIfNecessary() {
        val currentData1 = data1
        val currentData2 = data2
        if (currentData1 != null && currentData2 != null) {
            value = _combine(currentData1, currentData2)
        }
    }
}