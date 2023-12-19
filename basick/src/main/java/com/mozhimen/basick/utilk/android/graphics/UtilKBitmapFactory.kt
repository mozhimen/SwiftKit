package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.io.FileDescriptor

/**
 * @ClassName UtilKBitmapFactory
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
object UtilKBitmapFactory {
    @JvmStatic
    fun decodeFileDescriptor(fd: FileDescriptor, outPadding: Rect? = null, opts: BitmapFactory.Options? = null): Bitmap =
        BitmapFactory.decodeFileDescriptor(fd, outPadding, opts)
}