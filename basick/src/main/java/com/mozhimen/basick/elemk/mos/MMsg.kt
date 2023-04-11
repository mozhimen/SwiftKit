package com.mozhimen.basick.elemk.mos

data class MMsg<T>(
    val msg: String,
    val param: T
)