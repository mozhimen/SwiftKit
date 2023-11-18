package com.mozhimen.componentk.navigatek.bar.mos

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

data class DestinationWrapper(
    val routePath: String,
    val name: String,
    val fragment: Fragment,
    @DrawableRes val stateDrawableId: Int,
    @DrawableRes var selectedDrawableId: Int = 0,
    @DrawableRes var unSelectDrawableId: Int = 0/*,
    @ColorRes val fragmentPrimaryColor: Int = android.R.color.white,*/
)