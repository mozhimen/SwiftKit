package com.mozhimen.basick.utilk.os

import android.os.Environment


/**
 * @ClassName UtilKEnvironment
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 17:04
 * @Version 1.0
 */
object UtilKEnvironment {
    @JvmStatic
    fun getExternalStorageAbsolutePath(): String =
        Environment.getExternalStorageDirectory().absolutePath

    @JvmStatic
    fun getExternalStoragePath(): String =
        Environment.getExternalStorageDirectory().path

    @JvmStatic
    fun getDataAbsolutePath(): String =
        Environment.getDataDirectory().absolutePath

    @JvmStatic
    fun getDataPath(): String =
        Environment.getDataDirectory().path

    @JvmStatic
    fun isExternalStorageMounted():Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}