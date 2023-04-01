package com.mozhimen.uicorek.vhk

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @ClassName BindKViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 10:25
 * @Version 1.0
 */
class VHKRecyclerVB<VB : ViewDataBinding>(view: View, val vb: VB = DataBindingUtil.bind(view)!!) : VHKRecycler(vb.root)