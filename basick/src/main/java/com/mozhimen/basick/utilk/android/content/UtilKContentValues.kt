package com.mozhimen.basick.utilk.android.content

import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore

/**
 * @ClassName UtilKContentValues
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
object UtilKContentValues {
    @JvmStatic
    fun get(mineType: String, filePath: String, fileName: String): ContentValues =
        ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName/*imageFileName*/)
            put(MediaStore.Images.Media.MIME_TYPE, mineType/*"image/jpeg"*/)
            put(MediaStore.Images.Media.RELATIVE_PATH, filePath/*Environment.DIRECTORY_PICTURES*/)
        }
}