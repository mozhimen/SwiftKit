package com.mozhimen.blurk

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RSRuntimeException
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.mozhimen.kotlin.elemk.android.os.cons.CVersCode
import com.mozhimen.kotlin.utilk.android.graphics.UtilKBitmapDeal
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import kotlin.math.roundToInt


/**
 * @ClassName ImageKBlurRenderScript
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
object BlurKRenderScript {
    /**
     * 模糊图片,API>=17
     * @param sourceBitmap Bitmap
     * @param bitmapScale Float 图片缩放比例
     * @param blurRadius Float 最大模糊度(0.0-25.0之间)
     * @return Bitmap
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_17_42_J1)
    fun blurBitmapOfAndroid1(context: Context, sourceBitmap: Bitmap, @FloatRange(from = 1.0, to = 25.0) radius: Float = 25f, bitmapScale: Float = 0.4f): Bitmap {
        val inputBitmap = UtilKBitmapDeal.applyBitmapAnyResize(sourceBitmap, (sourceBitmap.width * bitmapScale).roundToInt(), (sourceBitmap.height * bitmapScale).roundToInt(), false)//将缩小后的图片作为预渲染的图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)//创建一张渲染后的输出图片
        val renderScript = android.renderscript.RenderScript.create(context)//创建RenderScript内核对象
        val scriptIntrinsicBlur = android.renderscript.ScriptIntrinsicBlur.create(renderScript, android.renderscript.Element.U8_4(renderScript))//创建一个模糊效果的RenderScript的工具对象
        //由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        //创建Allocation对象的时候其实内存时空的,需要使用copyTo()将数据填充进去
        val input = android.renderscript.Allocation.createFromBitmap(renderScript, inputBitmap)
        val output = android.renderscript.Allocation.createFromBitmap(renderScript, outputBitmap)

        scriptIntrinsicBlur.setInput(input)//设置blurScript对象的输入内存
        scriptIntrinsicBlur.setRadius(radius)//设置渲染的模糊程度,25f是最大模糊度
        scriptIntrinsicBlur.forEach(output)//将输出数据保存到输出内存中
        output.copyTo(outputBitmap)//将数据填充到Allocation中
        return outputBitmap
    }

    @JvmStatic
    @Throws(RSRuntimeException::class)
    fun blurBitmapOfAndroid2(context: Context, bitmap: Bitmap, @FloatRange(from = 1.0, to = 25.0) radius: Float = 25f): Bitmap {
        var renderScript: RenderScript? = null
        var input: Allocation? = null
        var output: Allocation? = null
        var scriptIntrinsicBlur: ScriptIntrinsicBlur? = null
        try {
            renderScript = RenderScript.create(context)
            renderScript.messageHandler = RenderScript.RSMessageHandler()
            input = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT)
            output = Allocation.createTyped(renderScript, input.type)
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

            scriptIntrinsicBlur.setInput(input)
            scriptIntrinsicBlur.setRadius(radius)
            scriptIntrinsicBlur.forEach(output)
            output.copyTo(bitmap)
        } finally {
            if (renderScript != null) {
                if (UtilKBuildVersion.isAfterV_23_6_M()) {
                    RenderScript.releaseAllContexts()
                } else {
                    renderScript.destroy()
                }
            }
            input?.destroy()
            output?.destroy()
            scriptIntrinsicBlur?.destroy()
        }
        return bitmap
    }
}