package com.mozhimen.componentk.netk.file.mos

import androidx.annotation.IntRange
import com.mozhimen.componentk.netk.file.cons.CTaskStatus
import java.io.Serializable


/**
 * @ClassName MNetKFileTask
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 16:35
 * @Version 1.0
 */
data class MTaskInfo(
    val url: String,
    val fileName: String,
    val filePathWithName: String,
    val status: Int = CTaskStatus.IDLE,
    @IntRange(from = 1, to = 10)
    val childTaskCount: Int = 1,
    val lastModify: String = "",
    val currentSize: Long = 0,
    val totalSize: Long = 0,
    val isSupportRange: Boolean = false
) : Serializable