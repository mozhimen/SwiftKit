package com.mozhimen.basick.utilk.android.graphics

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.ImageDecoder.Source
import android.net.Uri
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver

/**
 * @ClassName UtilKImageDecoder
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 2:22
 * @Version 1.0
 */
object UtilKImageDecoder {

    @JvmStatic
    @RequiresApi(CVersCode.V_28_9_P)
    fun createSource(contentResolver: ContentResolver, uri: Uri): Source =
        ImageDecoder.createSource(contentResolver, uri)

    @JvmStatic
    @RequiresApi(CVersCode.V_28_9_P)
    fun decodeBitmap(src: Source): Bitmap =
        ImageDecoder.decodeBitmap(src)

    @JvmStatic
    @RequiresApi(CVersCode.V_28_9_P)
    fun decodeBitmap(context: Context, uri: Uri): Bitmap =
        decodeBitmap(createSource(UtilKContentResolver.get(context), uri))
}