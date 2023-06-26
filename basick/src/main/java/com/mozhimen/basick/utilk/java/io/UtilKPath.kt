package com.mozhimen.basick.utilk.os

import com.mozhimen.basick.utilk.java.io.UtilKDir
import java.io.File

/**
 * @ClassName UtilKPath
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/17 15:46
 * @Version 1.0
 */
object UtilKPath {
    object Absolute {
        object Internal {
            @JvmStatic
            fun getFilesDir(): String =
                UtilKDir.Internal.getFilesDir().absolutePath

            @JvmStatic
            fun getCacheDir(): String =
                UtilKDir.Internal.getCacheDir().absolutePath
        }

        object External {
            @JvmStatic
            fun getCacheDir(): String? =
                UtilKDir.External.getCacheDir()?.absolutePath

            @JvmStatic
            fun getDataDir(): String =
                UtilKDir.External.getDataDir().absolutePath

            @JvmStatic
            fun getStorageDir(): String =
                UtilKDir.External.getStorageDir().absolutePath

            @JvmStatic
            fun getFilesRootDir(): String =
                UtilKDir.External.getFilesRootDir()!!.absolutePath

            @JvmStatic
            fun getStoragePublicDCIMDir(): File =
                UtilKDir.External.getStoragePublicDCIMDir()
        }
    }
}