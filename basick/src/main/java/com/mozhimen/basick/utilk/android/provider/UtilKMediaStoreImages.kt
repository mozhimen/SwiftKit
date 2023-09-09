package com.mozhimen.basick.utilk.android.provider

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver

/**
 * @ClassName UtilKMediaStoreImages
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/6 0:34
 * @Version 1.0
 */
object UtilKMediaStoreImages {
    @JvmStatic
    fun getMediaBitmap(context: Context, uri: Uri): Bitmap =
        MediaStore.Images.Media.getBitmap(UtilKContentResolver.get(context), uri)
}