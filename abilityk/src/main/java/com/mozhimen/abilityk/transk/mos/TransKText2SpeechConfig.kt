package com.mozhimen.abilityk.transk.mos

import java.util.*

/**
 * @ClassName TransKText2SpeechConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/5 21:24
 * @Version 1.0
 */
data class TransKText2SpeechConfig(
    val language: Locale,
    val pitch: Float,
    val speechRate: Float//设定语速,默认1.0正常语速
)
