package com.mozhimen.abilityk.transk.mos

import java.util.*

/**
 * @ClassName TransKText2SpeechConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/5 21:24
 * @Version 1.0
 */
data class MText2SpeechConfig(
    val language: Locale = Locale.CHINA,
    val pitch: Float = 1.5f,
    val speechRate: Float = 1.5f//设定语速,默认1.0正常语速
)
