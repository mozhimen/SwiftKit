package com.mozhimen.uicorek.recyclerk

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
class RecyclerKVBViewHolder<VB : ViewDataBinding>(view: View, val vb: VB = DataBindingUtil.bind(view)!!) :
    RecyclerKViewHolder(vb.root)