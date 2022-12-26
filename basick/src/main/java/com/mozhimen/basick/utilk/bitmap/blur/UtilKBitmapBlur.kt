package com.mozhimen.basick.utilk.bitmap.blur

import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.mozhimen.basick.utilk.context.UtilKApplication
import kotlin.math.min
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * @ClassName UtilKBitmapBlur
 * @Description Created by paveld on 3/6/14.
 * https://github.com/paveldudka/blurring/blob/master/app/src/main/java/com/paveldudka/util/FastBlur.java
 * 模糊处理类
 * warn:renderscript即将遗弃，后期将前移到Vulkan，使用GPU更快
 * https://developer.android.com/guide/topics/renderscript/compute?hl=zh-cn#additional-code-samples
 * https://developer.android.com/guide/topics/renderscript/migrate?hl=zh-cn
 * @Author mozhimen / Kolin Zhao / 大灯泡
 * @Date 2022/11/30 22:07
 * @Version 1.0
 */
object UtilKBitmapBlur {
    private val _context = UtilKApplication.instance.get()

    /**
     * 模糊图片,API>=17
     * @param sourceBitmap Bitmap
     * @param bitmapScale Float 图片缩放比例
     * @param blurRadius Float 最大模糊度(0.0-25.0之间)
     * @return Bitmap
     */
    @JvmStatic
    fun blurBitmap(sourceBitmap: Bitmap, bitmapScale: Float = 0.4f, blurRadius: Float = 25f): Bitmap {
        //将缩小后的图片作为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(sourceBitmap, (sourceBitmap.width * bitmapScale).roundToInt(), (sourceBitmap.height * bitmapScale).roundToInt(), false)
        //创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        //创建RenderScript内核对象
        val renderScript = RenderScript.create(_context)
        //创建一个模糊效果的RenderScript的工具对象
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        //由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        //创建Allocation对象的时候其实内存时空的,需要使用copyTo()将数据填充进去
        val allocationIn = Allocation.createFromBitmap(renderScript, inputBitmap)
        val allocationOut = Allocation.createFromBitmap(renderScript, outputBitmap)
        //设置渲染的模糊程度,25f是最大模糊度
        scriptIntrinsicBlur.setRadius(blurRadius)
        //设置blurScript对象的输入内存
        scriptIntrinsicBlur.setInput(allocationIn)
        //将输出数据保存到输出内存中
        scriptIntrinsicBlur.forEach(allocationOut)
        //将数据填充到Allocation中
        allocationOut.copyTo(outputBitmap)

        return outputBitmap
    }

    @JvmStatic
    fun blurBitmap(sourceBitmap: Bitmap, radius: Int, canReuseInBitmap: Boolean): Bitmap? {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
        val bitmap: Bitmap = if (canReuseInBitmap) {
            sourceBitmap
        } else {
            sourceBitmap.copy(sourceBitmap.config, true)
        }
        if (radius < 1) {
            return null
        }

        val w = bitmap.width
        val h = bitmap.height

        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)

        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1

        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)

        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        val vmin = IntArray(max(w, h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        var yw: Int = yi

        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int

        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + min(wm, max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - kotlin.math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius

            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (y == 0) {
                    vmin[x] = min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]

                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi++
                x++
            }
            yw += w
            y++
        }

        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius

            while (i <= radius) {
                yi = max(0, yp) + x

                sir = stack[i + radius]

                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]

                rbs = r1 - kotlin.math.abs(i)

                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs

                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }

                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius

            y = 0
            while (y < h) {

                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (x == 0) {
                    vmin[y] = min(y + r1, hm) * w
                }
                p = x + vmin[y]

                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }
}