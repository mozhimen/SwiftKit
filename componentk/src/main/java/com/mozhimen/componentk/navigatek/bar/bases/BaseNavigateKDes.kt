package com.mozhimen.componentk.navigatek.bar.bases

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

open class BaseNavigateKDes(
    val id: String,
    val name: String,
    val fragment: Fragment,
    @DrawableRes val stateResId: Int = 0,
    @DrawableRes val selectedResId: Int = 0,
    @DrawableRes val unSelectResId: Int = 0
)