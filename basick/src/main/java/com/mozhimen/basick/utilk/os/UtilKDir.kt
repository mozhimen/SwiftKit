package com.mozhimen.basick.utilk.os

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContextDir
import java.io.File


/**
 * @ClassName UtilKDir
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 16:41
 * @Version 1.0
 */
object UtilKDir : BaseUtilK() {

    object Internal {
        @JvmStatic
        fun getFilesDir(): File =
            UtilKContextDir.Internal.getFilesDir(_context)

        @JvmStatic
        fun getCacheDir(): File =
            UtilKContextDir.Internal.getCacheDir(_context)
    }

    object External {
        @JvmStatic
        fun getCacheDir(): File? =
            UtilKContextDir.External.getCacheDir(_context)

        @JvmStatic
        fun getFilesRootDir(): File? =
            UtilKContextDir.External.getFilesRootDir(_context)

        @JvmStatic
        fun getStorageDir(): File =
            UtilKEnvironment.getExternalStorageDir()

        @JvmStatic
        fun getStoragePublicDCIMDir(): File =
            UtilKEnvironment.getExternalStoragePublicDCIMDir()

        @JvmStatic
        fun getDataDir(): File =
            UtilKEnvironment.getDataDir()
    }
}