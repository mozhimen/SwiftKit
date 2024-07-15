package com.mozhimen.basick.utilk.android.graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.annotation.DrawableRes
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.FileDescriptor

/**
 * @ClassName UtilKBitmapFactory
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
object UtilKBitmapFactory : IUtilK {
    @JvmStatic
    fun decodeFileDescriptor(fd: FileDescriptor, outPadding: Rect? = null, opts: BitmapFactory.Options? = null): Bitmap =
        BitmapFactory.decodeFileDescriptor(fd, outPadding, opts)

    @JvmStatic
    fun decodeResource(res: Resources, @DrawableRes intResDrawable: Int): Bitmap {
        UtilKLogWrapper.d(TAG, "decodeResource: intResDrawable $intResDrawable")
        return BitmapFactory.decodeResource(res, intResDrawable)
    }

    @JvmStatic
    fun decodeResource(res: Resources, @DrawableRes intResDrawable: Int, opts: BitmapFactory.Options): Bitmap =
        BitmapFactory.decodeResource(res, intResDrawable, opts)
}