package com.mozhimen.componentk.navigatek.bar.bases

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_AListener

open class BaseNavigateKDes(
    val id: String,
    val name: String,
    var onInvokeFragment: I_AListener<Fragment>,
    @DrawableRes val stateResId: Int = 0,
    @DrawableRes val selectedResId: Int = 0,
    @DrawableRes val unSelectResId: Int = 0
)