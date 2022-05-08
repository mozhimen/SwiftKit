package com.mozhimen.uicorek.bindk

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mozhimen.uicorek.datak.helpers.DataKViewHolder

/**
 * @ClassName BindKViewHolder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/22 10:25
 * @Version 1.0
 */
class BindKViewHolder<T : ViewDataBinding>(view: View, val binding: T = DataBindingUtil.bind(view)!!) :
    DataKViewHolder(binding.root)