package com.mozhimen.basicktest.basek

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.basek.BaseKViewModel

/**
 * @ClassName BaseKDemoViewModel
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 14:46
 * @Version 1.0
 */
class BaseKDemoViewModel : BaseKViewModel() {
    val uiNum = MutableLiveData("0")

    fun genNum(){
        uiNum.value = (uiNum.value!!.toInt() + 1).toString()
    }
}