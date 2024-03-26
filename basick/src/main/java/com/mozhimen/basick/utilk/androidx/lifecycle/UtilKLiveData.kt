package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LiveData
import com.mozhimen.basick.utilk.kotlin.obj2stringTrim

/**
 * @ClassName UtilKLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/7 15:09
 * @Version 1.0
 */
fun LiveData<String>.liveValue2stringTrim() =
    UtilKLiveData.liveValue2stringTrim(this)

object UtilKLiveData {
    @JvmStatic
    fun liveValue2stringTrim(liveData: LiveData<String>): String =
        liveData.value?.obj2stringTrim() ?: ""
}