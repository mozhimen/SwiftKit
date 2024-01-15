package com.mozhimen.componentk.navigatek.bar.bases

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.commons.I_AListener

open class BaseNavigateKDes(
    val id: String,
    val name: String,
    var fragmentClazzName: String,
    var onInvokeFragment: I_AListener<Fragment>,
    @DrawableRes val stateResId: Int = 0,
    @DrawableRes val selectedResId: Int = 0,
    @DrawableRes val unSelectResId: Int = 0
){
    override fun toString(): String {
        return "BaseNavigateKDes(id='$id', name='$name', fragmentClazzName='$fragmentClazzName', onInvokeFragment=$onInvokeFragment, stateResId=$stateResId, selectedResId=$selectedResId, unSelectResId=$unSelectResId)"
    }
}