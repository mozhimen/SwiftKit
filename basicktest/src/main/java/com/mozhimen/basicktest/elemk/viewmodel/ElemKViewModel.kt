package com.mozhimen.basicktest.elemk.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.elemk.viewmodel.bases.BaseViewModel

/**
 * @ClassName ElemKDemoViewModel
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 14:46
 * @Version 1.0
 */
class ElemKViewModel : BaseViewModel() {
    val uiNum = MutableLiveData("0")

    fun genNum(){
        uiNum.value = (uiNum.value!!.toInt() + 1).toString()
    }
}