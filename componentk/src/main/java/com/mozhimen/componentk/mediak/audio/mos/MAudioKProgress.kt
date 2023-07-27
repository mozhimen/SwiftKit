package com.mozhimen.componentk.mediak.audio.mos

import com.mozhimen.componentk.mediak.player.status.cons.EMediaKPlayerStatus

data class MAudioKProgress(
    val status: EMediaKPlayerStatus,
    val currentPos: Int,
    val duration: Int,
    val audioInfo: MAudioKInfo?
)