package com.mozhimen.basick.impls

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName MutableLiveDataStrict
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/9
 * @Version 1.0
 */
class MutableLiveDataStrict<T> : MutableLiveData<T> {

    constructor() : super()

    constructor(value: T) : super(value)

    override fun postValue(value: T) {
        if (value == getValue()) return
        if (value == null) return
        super.postValue(value)
    }

    override fun setValue(value: T) {
        if (value == getValue()) return
        if (value == null) return
        super.setValue(value)
    }
}