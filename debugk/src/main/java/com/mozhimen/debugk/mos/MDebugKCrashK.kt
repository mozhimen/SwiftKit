package com.mozhimen.debugk.mos

import java.io.File
import java.io.Serializable

/**
 * @ClassName DebugKCrashKMo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 12:57
 * @Version 1.0
 */
data class MDebugKCrashK(
    val name: String,
    val file: File
) : Serializable