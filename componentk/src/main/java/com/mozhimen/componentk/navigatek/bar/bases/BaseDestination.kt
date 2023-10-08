package com.mozhimen.componentk.navigatek.bar.bases

import androidx.annotation.DrawableRes

open class BaseDestination(
    val id: String,
    val name: String?,
    @DrawableRes val selectedResId: Int,
    @DrawableRes val unSelectResId: Int
)