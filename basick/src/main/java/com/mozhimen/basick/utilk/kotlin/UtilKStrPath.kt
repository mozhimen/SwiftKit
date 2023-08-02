package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import java.io.File

/**
 * @ClassName UtilKStringPath
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 15:42
 * @Version 1.0
 */
object UtilKStrPath {
    @JvmStatic
    fun strPath2bitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName)

    object Absolute {
        object Internal {
            @JvmStatic
            fun getFilesDir(): String =
                UtilKFileDir.Internal.getFilesDir().absolutePath

            @JvmStatic
            fun getCacheDir(): String =
                UtilKFileDir.Internal.getCacheDir().absolutePath
        }

        object External {
            @JvmStatic
            fun getCacheDir(): String? =
                UtilKFileDir.External.getCacheDir()?.absolutePath

            @JvmStatic
            fun getDataDir(): String =
                UtilKFileDir.External.getDataDir().absolutePath

            @JvmStatic
            fun getStorageDir(): String =
                UtilKFileDir.External.getStorageDir().absolutePath

            @JvmStatic
            fun getFilesRootDir(): String =
                UtilKFileDir.External.getFilesRootDir()!!.absolutePath

            @JvmStatic
            fun getStoragePublicDCIMDir(): File =
                UtilKFileDir.External.getStoragePublicDCIMDir()
        }
    }
}