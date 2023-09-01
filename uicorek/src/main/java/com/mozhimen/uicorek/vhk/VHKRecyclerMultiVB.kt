package com.mozhimen.uicorek.vhk

import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * @ClassName BindKViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 10:25
 * @Version 1.0
 */
class VHKRecyclerMultiVB<VB : ViewDataBinding> : VHKRecycler {
    private var _vb: VB? = null
    val vb get() = _vb!!

    constructor(view: View) : super(view)

    constructor(vb: VB) : super(vb.root) {
        _vb = vb
    }
}

//class VHKRecyclerVB<ViewBinding : ViewDataBinding>(view: View, val vb: ViewBinding = DataBindingUtil.bind(view)!!) : VHKRecycler(vb.root)