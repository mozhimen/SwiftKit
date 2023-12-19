package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKContextDir
import com.mozhimen.basick.utilk.android.os.UtilKEnvironment
import java.io.File


/**
 * @ClassName UtilKDir
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 16:41
 * @Version 1.0
 */
object UtilKFileDir : BaseUtilK() {

    object Internal {
        @JvmStatic
        fun getFiles(): File =
            UtilKContextDir.Internal.getFilesDir(_context)

        @JvmStatic
        fun getCache(): File =
            UtilKContextDir.Internal.getCacheDir(_context)
    }

    object External {
        @JvmStatic
        fun getCache(): File? =
            UtilKContextDir.External.getCacheDir(_context)

        @JvmStatic
        fun getFilesRoot(): File? =
            UtilKContextDir.External.getFilesRootDir(_context)

        @JvmStatic
        fun getFilesRootFreeSpace(): Long =
            getFilesRoot()?.freeSpace ?: Long.MAX_VALUE

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getFilesDownloadsDir(): File? =
            UtilKContextDir.External.getFilesDownloadsDir(_context)

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getEnvStorage(): File =
            UtilKEnvironment.getExternalStorageDir()

        @JvmStatic
        fun getEnvStoragePublicPictures(): File =
            UtilKEnvironment.getExternalStoragePublicDirPictures()

        @JvmStatic
        fun getEnvStoragePublicDCIM(): File =
            UtilKEnvironment.getExternalStoragePublicDirDCIM()

        @JvmStatic
        fun getEnvData(): File =
            UtilKEnvironment.getDataDir()
    }
}