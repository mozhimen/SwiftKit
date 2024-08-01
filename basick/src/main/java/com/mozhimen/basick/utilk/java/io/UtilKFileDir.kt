package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.android.content.UtilKContextDir
import com.mozhimen.basick.utilk.android.os.UtilKEnvironment
import com.mozhimen.basick.utilk.bases.BaseUtilK
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
            getFilesRoot()?.freeSpace ?: 0

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getFilesDownloads(): File? =
            UtilKContextDir.External.getFilesDownloadsDir(_context)

        //////////////////////////////////////////////////////////////

        @JvmStatic
        fun getStorage_ofEnvironment(): File =
            UtilKEnvironment.getExternalStorageDir()

        @JvmStatic
        fun getStoragePublic_ofPictures_ofEnvironment(): File =
            UtilKEnvironment.getExternalStoragePublicDir_ofPictures()

        @JvmStatic
        fun getStoragePublic_ofDCIM_ofEnvironment(): File =
            UtilKEnvironment.getExternalStoragePublicDir_ofDCIM()

        @JvmStatic
        fun getData_ofEnvironment(): File =
            UtilKEnvironment.getDataDir()
    }

    //////////////////////////////////////////////////////////////

    @JvmStatic
    fun delete(dir: File): Boolean {
        if (dir.isDirectory) {
            val childs = dir.list()
            if (childs != null) {
                for (i in childs.indices) {
                    if (!delete(File(dir, childs[i]))) {
                        return false
                    }
                }
            }
        }
        return dir.delete()// 目录现在为空，可以删除
    }
}