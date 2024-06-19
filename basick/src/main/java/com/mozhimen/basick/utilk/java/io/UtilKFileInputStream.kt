package com.mozhimen.basick.utilk.java.io

import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.File
import java.io.FileInputStream

/**
 * @ClassName UtilKFileInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKFileInputStream : IUtilK {
    @JvmStatic
    fun get(file: File): FileInputStream? =
        try {
            FileInputStream(file)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "get: ", e)
            null
        }
}