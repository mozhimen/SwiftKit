package com.mozhimen.componentk.audiok.mos

import com.mozhimen.componentk.audiok.cons.EPlayStatus

data class MAudioKProgress(
    val status: EPlayStatus,
    val currentPos: Int,
    val duration: Int,
    val audioInfo: MAudioK?
)