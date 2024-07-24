package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mozhimen.basick.elemk.androidx.lifecycle.MediatorLiveDataCombined
import com.mozhimen.basick.elemk.androidx.lifecycle.MediatorLiveDataThrottled
import com.mozhimen.basick.elemk.commons.IAB_CListener
import com.mozhimen.basick.elemk.commons.IA_BListener
import com.mozhimen.basick.utilk.kotlin.obj2str_trim

/**
 * @ClassName UtilKLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/7 15:09
 * @Version 1.0
 */
fun <T, K, S> LiveData<T>.liveData2combinedLiveData(other: LiveData<K>, combine: IAB_CListener<T, K, S>): LiveData<S> =
    UtilKLiveDataWrapper.liveData2combinedLiveData(this, other, combine)

fun <T> LiveData<T>.liveData2throttledLiveData(delayMillis: Long): LiveData<T> =
    UtilKLiveDataWrapper.liveData2throttledLiveData(this, delayMillis)

fun LiveData<String>.liveDataValue2stringTrim() =
    UtilKLiveDataWrapper.liveDataValue2stringTrim(this)

fun <T, K> LiveData<T>.map(mapper: (T) -> K): LiveData<K> =
    UtilKLiveDataWrapper.map(this, mapper)

//////////////////////////////////////////////////////////////////////////////////////////

object UtilKLiveDataWrapper {

    @JvmStatic
    fun <T, K> map(liveData: LiveData<T>, mapper: IA_BListener<T, K>): LiveData<K> =
        liveData.map(mapper)

    @JvmStatic
    fun <T, K, S> liveData2combinedLiveData(liveData: LiveData<T>, other: LiveData<K>, combine: IAB_CListener<T, K, S>): LiveData<S> =
        MediatorLiveDataCombined(liveData, other, combine)

    @JvmStatic
    fun <T> liveData2throttledLiveData(liveData: LiveData<T>, delayMillis: Long): LiveData<T> =
        MediatorLiveDataThrottled(liveData, delayMillis)

    @JvmStatic
    fun liveDataValue2stringTrim(liveData: LiveData<String>): String =
        liveData.value?.obj2str_trim() ?: ""
}