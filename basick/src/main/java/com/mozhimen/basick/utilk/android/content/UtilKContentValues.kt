package com.mozhimen.basick.utilk.android.content

import android.content.ContentValues
import android.provider.MediaStore
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKContentValues
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
object UtilKContentValues {
    @JvmStatic
    fun get(strFileName: String, mineType: String, strFilePath: String): ContentValues =
        ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, strFileName/*imageFileName*/)
            put(MediaStore.Images.Media.MIME_TYPE, mineType/*"image/jpeg"*/)
            if (UtilKBuildVersion.isAfterV_28_9_P())
                put(MediaStore.Images.Media.RELATIVE_PATH, strFilePath/*Environment.DIRECTORY_PICTURES*/)
        }
}