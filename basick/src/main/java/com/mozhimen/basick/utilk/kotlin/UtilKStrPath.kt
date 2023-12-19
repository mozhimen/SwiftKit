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
            fun getFiles(): String =
                UtilKFileDir.Internal.getFiles().absolutePath

            @JvmStatic
            fun getCache(): String =
                UtilKFileDir.Internal.getCache().absolutePath
        }

        object External {
            @JvmStatic
            fun getCache(): String? =
                UtilKFileDir.External.getCache()?.absolutePath

            @JvmStatic
            fun getFilesRoot(): String =
                UtilKFileDir.External.getFilesRoot()!!.absolutePath

            @JvmStatic
            fun getEnvStorage(): String =
                UtilKFileDir.External.getEnvStorage().absolutePath

            @JvmStatic
            fun getEnvStoragePublicPictures(): String =
                UtilKFileDir.External.getEnvStoragePublicPictures().absolutePath

            @JvmStatic
            fun getEnvStoragePublicDCIM(): String =
                UtilKFileDir.External.getEnvStoragePublicDCIM().absolutePath

            @JvmStatic
            fun getEnvData(): String =
                UtilKFileDir.External.getEnvData().absolutePath
        }
    }
}