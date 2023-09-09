package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.java.io.UtilKFileDir

/**
 * @ClassName UtilKStringPath
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 15:42
 * @Version 1.0
 */
object UtilKStrPath {


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
            fun getFilesRootDir(): String =
                UtilKFileDir.External.getFilesRootDir()!!.absolutePath

            @JvmStatic
            fun getEnvStorageDir(): String =
                UtilKFileDir.External.getEnvStorageDir().absolutePath

            @JvmStatic
            fun getEnvStoragePublicDCIMDir(): String =
                UtilKFileDir.External.getEnvStoragePublicDCIMDir().absolutePath

            @JvmStatic
            fun getEnvDataDir(): String =
                UtilKFileDir.External.getEnvDataDir().absolutePath
        }
    }
}