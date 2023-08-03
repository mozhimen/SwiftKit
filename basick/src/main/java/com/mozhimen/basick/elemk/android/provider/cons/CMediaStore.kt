package com.mozhimen.basick.elemk.android.provider.cons

import android.provider.MediaStore

/**
 * @ClassName MediaStore
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 17:22
 * @Version 1.0
 */
object CMediaStore {
    object Images {
        object Media {
            val EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val DATA = MediaStore.Images.Media.DATA
        }

        object ImageColumns {
            val DATA = MediaStore.Images.ImageColumns.DATA
            val DISPLAY_NAME = MediaStore.Images.ImageColumns.DISPLAY_NAME
            val MIME_TYPE = MediaStore.Images.ImageColumns.MIME_TYPE
            val DATE_TAKEN = MediaStore.Images.ImageColumns.DATE_TAKEN

        }
    }
}