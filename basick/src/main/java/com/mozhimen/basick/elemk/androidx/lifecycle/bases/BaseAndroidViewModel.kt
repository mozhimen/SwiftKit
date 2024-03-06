package com.mozhimen.basick.elemk.androidx.lifecycle.bases

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseAndroidViewModel
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 21:50
 * @Version 1.0
 */
open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application), IUtilK {
    val context: Context
        get() = getApplication<Application>().applicationContext
}