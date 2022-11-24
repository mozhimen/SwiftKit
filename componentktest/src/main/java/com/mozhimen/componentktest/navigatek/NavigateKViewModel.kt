package com.mozhimen.componentktest.navigatek

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.elemk.viewmodel.bases.BaseViewModel

class NavigateKViewModel : BaseViewModel() {
    val liveFragmentId = MutableLiveData<Int?>(null)
}