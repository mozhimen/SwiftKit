package com.mozhimen.componentktest.navigatek

import androidx.lifecycle.MutableLiveData
import com.mozhimen.basick.basek.BaseKViewModel

class NavigateKViewModel : BaseKViewModel() {
    val liveFragmentId = MutableLiveData<Int?>(null)
}