package com.mozhimen.basick.elemk.viewmodel.bases

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

/**
 * @ClassName BaseAndroidViewModel
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 21:50
 * @Version 1.0
 */
open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    val context: Context
        get() = getApplication<Application>().applicationContext
}