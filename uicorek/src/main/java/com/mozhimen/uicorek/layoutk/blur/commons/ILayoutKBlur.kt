package com.mozhimen.uicorek.layoutk.blur.commons

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.IntRange

/**
 * @ClassName ILayoutKBlur
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/7 18:39
 * @Version 1.0
 */
internal interface ILayoutKBlur {
    /**
     * 添加待模糊图片
     */
    fun setBlurImageView(blurBitmap: Bitmap)

    /**
     * 添加待模糊图片2
     */
    fun setBlurImageView(blurDrawable: Drawable)

    /**
     * 设置模糊程度
     */
    @Throws(Exception::class)
    fun setBlurLevel(@IntRange(from = 0, to = 100) level: Int)

    /**
     * 设置图片上移的距离
     */
    fun setBlurOffset(offset: Int)

    /**
     * 模糊效果开关设置
     * @param enable Boolean
     */
    fun setBlurEnable(enable: Boolean)

    /**
     * 显示模糊的图片
     */
    fun showBlurImageView()
}