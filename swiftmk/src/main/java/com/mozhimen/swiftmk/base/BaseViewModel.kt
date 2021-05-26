package com.mozhimen.swiftmk.base

import androidx.lifecycle.ViewModel

/**
 * @ClassName BaseViewModel
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/26 17:42
 * @Version 1.0
 */
class BaseViewModel : ViewModel() {
    val tag = this.javaClass.canonicalName.toString()
}