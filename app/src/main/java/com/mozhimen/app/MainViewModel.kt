package com.mozhimen.app

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mozhimen.swiftmk.base.BaseViewModel
import com.mozhimen.swiftmk.helper.mvvm.ObservableLiveDataField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

/**
 * @ClassName MainViewModel
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/27 11:35
 * @Version 1.0
 */
class MainViewModel : BaseViewModel() {
    val uiSelectedTab = ObservableLiveDataField(0)
}