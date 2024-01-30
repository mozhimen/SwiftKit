package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mozhimen.basick.elemk.commons.ISuspend_AListener
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @ClassName UtilKLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/7 15:09
 * @Version 1.0
 */
fun MutableLiveData<String>.liveValue2stringTrim() =
    UtilKLiveData.liveValue2stringTrim(this)

object UtilKLiveData {
    fun liveValue2stringTrim(mutableLiveData: MutableLiveData<String>): String =
        mutableLiveData.value?.obj2stringTrim() ?: ""


}