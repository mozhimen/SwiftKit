package com.mozhimen.basick.utilk.kotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFactory

/**
 * @ClassName UtilKIntRes
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/31 14:08
 * @Version 1.0
 */
fun Int.intResDrawable2bitmapAny(context: Context): Bitmap =
    UtilKIntRes.intResDrawable2bitmapAny(this, context)

////////////////////////////////////////////////////////////////////////////////

object UtilKIntRes {
    @JvmStatic
    fun intResDrawable2bitmapAny(@DrawableRes intResDrawable: Int, context: Context): Bitmap =
        UtilKBitmapFactory.decodeResource(UtilKResources.get_ofApp(context), intResDrawable)
}