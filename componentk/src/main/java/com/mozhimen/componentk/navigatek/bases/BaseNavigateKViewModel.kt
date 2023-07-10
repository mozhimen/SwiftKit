package com.mozhimen.componentk.navigatek.bases

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel

open class BaseNavigateKViewModel : BaseViewModel() {
    val liveFragmentId = MutableLiveData<Int?>(null)
    val liveSetPopupFlag = MutableLiveData<Boolean?>(null)
}