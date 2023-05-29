package com.mozhimen.componentk.mediak.audio.mos

import com.mozhimen.componentk.mediak.status.cons.EPlayStatus

data class MAudioKProgress(
    val status: EPlayStatus,
    val currentPos: Int,
    val duration: Int,
    val audioInfo: MAudioK?
)