package com.mozhimen.basick.utilk.androidx.core

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.mozhimen.basick.utilk.android.content.UtilKResources

/**
 * @ClassName UtilKRoundedBitmapDrawable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/31 13:58
 * @Version 1.0
 */
object UtilKRoundedBitmapDrawable {
//    @JvmStatic
//    fun get(context: Context,@DrawableRes intResDrawable: Int): RoundedBitmapDrawable =
//        get(UtilKResources.getApp(context),)

    @JvmStatic
    fun get(res: Resources, bitmap: Bitmap): RoundedBitmapDrawable =
        RoundedBitmapDrawableFactory.create(res, bitmap)
}