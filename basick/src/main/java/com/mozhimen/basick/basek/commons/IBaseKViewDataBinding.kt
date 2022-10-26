package com.mozhimen.basick.basek.commons

import androidx.databinding.ViewDataBinding

/**
 * @ClassName IBaseKBinding
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 23:58
 * @Version 1.0
 */
interface IBaseKViewDataBinding<VB : ViewDataBinding> {
    /**
     * vb.vm=vm
     */
    fun bindViewVM(vb: VB)
}