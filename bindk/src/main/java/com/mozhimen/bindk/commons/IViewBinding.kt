package com.mozhimen.bindk.commons

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @ClassName IViewDataBinding
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 23:58
 * @Version 1.0
 */
interface IViewBinding<VB : ViewBinding> {
    fun getViewModelProviderFactory(): ViewModelProvider.Factory? = null
}