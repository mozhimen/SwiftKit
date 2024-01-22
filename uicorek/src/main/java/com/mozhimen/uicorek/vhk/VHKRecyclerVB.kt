package com.mozhimen.uicorek.vhk

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName VHKRecyclerVB2
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/31 19:28
 * @Version 1.0
 */
open class VHKRecyclerVB<VB : ViewDataBinding>(view: View, val vb: VB = DataBindingUtil.bind(view)!!) : VHKRecycler(view), IUtilK