package com.mozhimen.componentk.navigatek.bar.mos

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

data class DestinationWrapper(
    val routePath: String,
    val name: String,
    val fragment: Fragment,
    @DrawableRes val resId: Int,
    @DrawableRes var selectedResId: Int = 0,
    @DrawableRes var unSelectResId: Int = 0,
    @ColorRes val fragmentPrimaryColor: Int = android.R.color.white,
)