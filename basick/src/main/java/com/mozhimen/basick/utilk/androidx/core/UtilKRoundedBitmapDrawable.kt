package com.mozhimen.basick.utilk.androidx.core

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.kotlin.intResDrawable2bitmapAny

/**
 * @ClassName UtilKRoundedBitmapDrawable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/31 13:58
 * @Version 1.0
 */
object UtilKRoundedBitmapDrawable {
    @JvmStatic
    fun get(context: Context, @DrawableRes intResDrawable: Int, @Px radius: Float): RoundedBitmapDrawable =
        get(UtilKResources.get_ofApp(context), intResDrawable.intResDrawable2bitmapAny(context)).apply { cornerRadius = radius }

    @JvmStatic
    fun get(context: Context, @DrawableRes intResDrawable: Int): RoundedBitmapDrawable =
        get(UtilKResources.get_ofApp(context), intResDrawable.intResDrawable2bitmapAny(context))

    @JvmStatic
    fun get(res: Resources, bitmap: Bitmap): RoundedBitmapDrawable =
        RoundedBitmapDrawableFactory.create(res, bitmap)
}