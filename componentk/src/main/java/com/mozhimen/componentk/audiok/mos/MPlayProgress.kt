package com.mozhimen.componentk.audiok.mos

import com.mozhimen.componentk.audiok.cons.EPlayStatus

data class MPlayProgress(
    val status: EPlayStatus,
    val currentPos: Int,
    val duration: Int
)