package com.mozhimen.basick.elemk.androidx.databinding.commons

import androidx.databinding.ViewDataBinding

/**
 * @ClassName IViewDataBinding
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 23:58
 * @Version 1.0
 */
interface IViewDataBinding<VDB : ViewDataBinding> {
    /**
     * vdb.vm=vm
     */
    fun bindViewVM(vdb: VDB)
}