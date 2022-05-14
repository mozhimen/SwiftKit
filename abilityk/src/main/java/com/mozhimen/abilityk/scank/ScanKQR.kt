package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

/**
 * @ClassName QRCodeK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/25 14:02
 * @Version 1.0
 */
object ScanKQR {
    fun createQRCode(str: String, widthAndHeight: Int): Bitmap {
        val hints = Hashtable<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "utf-8"
        val bitMatrix = QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight, hints)
        val pixels = IntArray(widthAndHeight * widthAndHeight)
        for (y in 0 until widthAndHeight) {
            for (x in 0 until widthAndHeight) {
                if (bitMatrix[x, y]) {
                    pixels[y * widthAndHeight + x] = -0x1000000
                } else {
                    pixels[y * widthAndHeight + x] = -0x1
                }
            }
        }
        val bitmap = Bitmap.createBitmap(
            widthAndHeight, widthAndHeight,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(pixels, 0, widthAndHeight, 0, 0, widthAndHeight, widthAndHeight)
        return bitmap
    }
}