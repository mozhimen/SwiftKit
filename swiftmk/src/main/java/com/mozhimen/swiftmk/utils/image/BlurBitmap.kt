package com.mozhimen.swiftmk.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.RequiresApi

/**
 * @ClassName BlurBitmap
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:23
 * @Version 1.0
 */
object BlurBitmap {

    //图片缩放比例
    private const val BITMAP_SCALE = 0.4f

    //最大模糊度(0.0-25.0之间)
    private const val BLUR_RADIUS = 25f

    /**
     * 模糊图片
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun blur(context: Context, bitmap: Bitmap): Bitmap? {
        //计算图片缩小后的长宽
        val width = Math.round(bitmap.width * BITMAP_SCALE)
        val height = Math.round(bitmap.height * BITMAP_SCALE)

        //将缩小后的图片作为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        //创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        //创建RenderScript内核对象
        val renderScript = RenderScript.create(context)
        //创建一个模糊效果的RenderScript的工具对象
        val scriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        //由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        //创建Allocation对象的时候其实内存时空的,需要使用copyTo()将数据填充进去
        val allocationIn = Allocation.createFromBitmap(renderScript, inputBitmap)
        val allocationOut = Allocation.createFromBitmap(renderScript, outputBitmap)

        //设置渲染的模糊程度,25f是最大模糊度
        scriptIntrinsicBlur.setRadius(BLUR_RADIUS)
        //设置blurScript对象的输入内存
        scriptIntrinsicBlur.setInput(allocationIn)
        //将输出数据保存到输出内存中
        scriptIntrinsicBlur.forEach(allocationOut)

        //将数据填充到Allocation中
        allocationOut.copyTo(outputBitmap)

        return outputBitmap
    }
}