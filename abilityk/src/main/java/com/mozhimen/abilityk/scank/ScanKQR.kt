package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.underlayk.logk.LogK
import java.util.*

/**
 * @ClassName QRCodeK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 14:02
 * @Version 1.0
 */
object ScanKQR : BaseUtilK() {

    /**
     * 创建QRCode
     * @param str String
     * @param width Int
     * @param height Int
     * @param charset String
     * @param errorCorrection String
     * @param margin String
     * @param colorBg Int
     * @param colorCode Int
     * @return Bitmap?
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createQRCodeBitmap(
        str: String,
        width: Int,
        height: Int = width,
        charset: String = "UTF-8",
        errorCorrection: String = "L",
        margin: String = "2",
        @ColorInt colorBg: Int = UtilKRes.getColor(android.R.color.white),
        @ColorInt colorCode: Int = UtilKRes.getColor(android.R.color.black)
    ): Bitmap? {
        require(str.isNotEmpty()) { "$TAG str must not be mull" }
        require(width > 0 && height > 0) { "$TAG width and height must be > 0" }
        try {
            //设置二维码相关配置,生成BitMatrix(位矩阵)对象
            val hints = Hashtable<EncodeHintType, String>()
            if (charset.isNotEmpty()) {
                hints[EncodeHintType.CHARACTER_SET] = charset // 字符转码格式设置
            }
            if (errorCorrection.isNotEmpty()) {
                hints[EncodeHintType.ERROR_CORRECTION] = errorCorrection // 容错级别设置
            }
            if (margin.isNotEmpty()) {
                hints[EncodeHintType.MARGIN] = margin // 空白边距设置
            }
            val bitMatrix = QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height, hints)

            //创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (bitMatrix[x, y]) {
                        pixels[y * width + x] = colorBg // 黑色色块像素设置
                    } else {
                        pixels[y * width + x] = colorCode // 白色色块像素设置
                    }
                }
            }

            //创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            LogK.et(TAG, e.message ?: "")
        }
        return null
    }
}