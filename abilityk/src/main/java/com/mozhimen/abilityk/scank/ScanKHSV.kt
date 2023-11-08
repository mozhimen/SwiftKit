package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.mozhimen.abilityk.scank.cons.EColorHSV
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.graphics.drawable2bitmap
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapAny
import com.mozhimen.basick.utilk.squareup.moshi.t2strJsonMoshi
import java.io.FileInputStream

/**
 * @ClassName ScanKHSV
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/10 18:23
 * @Version 1.0
 */
object ScanKHSV : BaseUtilK() {

    private val _colorMap = HashMap<EColorHSV, Int>()

    /**
     * 检查图片资源的颜色占比
     * @param resId Int 资源文件夹中的索引id
     * @return Map<String, Int>?
     */
    @JvmStatic
    fun colorAnalyze(resId: Int): List<Pair<EColorHSV, Int>>? {
        return colorAnalyze(UtilKRes.getDrawable(resId)!!.drawable2bitmap())
    }

    /**
     * 检查图片资源的颜色占比
     * @param bitmapPathName String
     * @return Map<String, Int>?
     */
    @JvmStatic
    fun colorAnalyze(bitmapPathName: String): List<Pair<EColorHSV, Int>>? =
        colorAnalyze(bitmapPathName.strFilePath2fileInputStream().inputStream2bitmapAny())

    /**
     * 检查图片资源的颜色占比
     * @param bitmap Bitmap
     * @return Map<String, Int>?
     */
    @JvmStatic
    fun colorAnalyze(bitmap: Bitmap): List<Pair<EColorHSV, Int>>? {
        _colorMap.clear()
        try {
            val rows = bitmap.width
            val cols = bitmap.height
            var key: EColorHSV?
            var r: Int
            var g: Int
            var b: Int
            for (x in 0 until rows) {
                for (y in 0 until cols) {
                    r = Color.red(bitmap.getPixel(x, y))
                    g = Color.green(bitmap.getPixel(x, y))
                    b = Color.blue(bitmap.getPixel(x, y))
                    val hsv = rgbArray2HsvArray(intArrayOf(r, g, b))
                    key = colorMatch(hsv[0].toInt(), (hsv[1] * 255).toInt(), (hsv[2] * 255).toInt())
                    key?.let {
                        if (!_colorMap.containsKey(key)) {
                            _colorMap[key] = 1
                        } else {
                            _colorMap[key] = _colorMap[key]!! + 1
                        }
                    }
                }
            }
            Log.d(TAG, "colorAnalyze: colorMap ${_colorMap.t2strJsonMoshi()}")
            return colorPercentage(rows * cols)
        } catch (e: Exception) {
            LogK.etk(TAG, "colorAnalyze Exception $e")
        }
        return null
    }

    /**
     * rgb转换hsv
     * @param rgb IntArray
     * @return FloatArray
     */
    @JvmStatic
    private fun rgbArray2HsvArray(rgb: IntArray): FloatArray {
        //切割rgb数组
        val r = rgb[0]
        val g = rgb[1]
        val b = rgb[2]
        //公式运算 /255
        val r1 = r / 255f
        val g1 = g / 255f
        val b1 = b / 255f
        //重新拼接运算用数组
        val all = floatArrayOf(r1, g1, b1)
        var max = all[0]
        var min = all[0]
        //循环查找最大值和最小值
        for (i in all.indices) {
            if (max <= all[i]) {
                max = all[i]
            }
            if (min >= all[i]) {
                min = all[i]
            }
        }
        val cMax = max
        val cMin = min
        //计算差值
        val diff = cMax - cMin
        var hue = 0f
        //判断情况计算色调H
        if (diff == 0f) {
            hue = 0f
        } else {
            if (cMax == r1) {
                hue = (g1 - b1) / diff % 6 * 60f
            }
            if (cMax == g1) {
                hue = ((b1 - r1) / diff + 2f) * 60f
            }
            if (cMax == b1) {
                hue = ((r1 - g1) / diff + 4f) * 60f
            }
        }
        //计算饱和度S
        val saturation: Float = if (cMax == 0f) {
            0f
        } else {
            diff / cMax
        }
        //计算明度V
        return floatArrayOf(hue, saturation, cMax)
    }

    /**
     * 计算百分比
     * @param total Int
     * @return Map<String, Int>
     */
    @JvmStatic
    private fun colorPercentage(total: Int): List<Pair<EColorHSV, Int>> {
        val result = ArrayList<Pair<EColorHSV, Int>>()
        var colorPercent: Double
        for ((key, value) in _colorMap) {
            colorPercent = value * 1.0 / total
            Log.d(TAG, "colorPercentage ${key.colorName} ${(colorPercent * 100).toInt()}%")
            result.add(key to (colorPercent * 100).toInt())
        }
        return result
    }

    /**
     * 颜色配对
     * @param h Int
     * @param s Int
     * @param v Int
     * @return OpenCVKColorHSV?
     */
    @JvmStatic
    private fun colorMatch(h: Int, s: Int, v: Int): EColorHSV? {
        return if (h <= EColorHSV.COLOR_RED.hMax && h >= EColorHSV.COLOR_RED.hMin && s <= EColorHSV.COLOR_RED.sMax && s >= EColorHSV.COLOR_RED.sMin && v <= EColorHSV.COLOR_RED.vMax && v >= EColorHSV.COLOR_RED.vMin) {
            EColorHSV.COLOR_RED //红色
        } else if (h <= EColorHSV.COLOR_ORANGE.hMax && h >= EColorHSV.COLOR_ORANGE.hMin && s <= EColorHSV.COLOR_ORANGE.sMax && s >= EColorHSV.COLOR_ORANGE.sMin && v <= EColorHSV.COLOR_ORANGE.vMax && v >= EColorHSV.COLOR_ORANGE.vMin) {
            EColorHSV.COLOR_ORANGE //橙色
        } else if (h <= EColorHSV.COLOR_YELLOW.hMax && h >= EColorHSV.COLOR_YELLOW.hMin && s <= EColorHSV.COLOR_YELLOW.sMax && s >= EColorHSV.COLOR_YELLOW.sMin && v <= EColorHSV.COLOR_YELLOW.vMax && v >= EColorHSV.COLOR_YELLOW.vMin) {
            EColorHSV.COLOR_YELLOW //黄色
        } else if (h <= EColorHSV.COLOR_GREEN.hMax && h >= EColorHSV.COLOR_GREEN.hMin && s <= EColorHSV.COLOR_GREEN.sMax && s >= EColorHSV.COLOR_GREEN.sMin && v <= EColorHSV.COLOR_GREEN.vMax && v >= EColorHSV.COLOR_GREEN.vMin) {
            EColorHSV.COLOR_GREEN //绿色
        } else if (h <= EColorHSV.COLOR_CYAN.hMax && h >= EColorHSV.COLOR_CYAN.hMin && s <= EColorHSV.COLOR_CYAN.sMax && s >= EColorHSV.COLOR_CYAN.sMin && v <= EColorHSV.COLOR_CYAN.vMax && v >= EColorHSV.COLOR_CYAN.vMin) {
            EColorHSV.COLOR_CYAN //青色
        } else if (h <= EColorHSV.COLOR_BLUE.hMax && h >= EColorHSV.COLOR_BLUE.hMin && s <= EColorHSV.COLOR_BLUE.sMax && s >= EColorHSV.COLOR_BLUE.sMin && v <= EColorHSV.COLOR_BLUE.vMax && v >= EColorHSV.COLOR_BLUE.vMin) {
            EColorHSV.COLOR_BLUE //蓝色
        } else if (h <= EColorHSV.COLOR_PURPLE.hMax && h >= EColorHSV.COLOR_PURPLE.hMin && s <= EColorHSV.COLOR_PURPLE.sMax && s >= EColorHSV.COLOR_PURPLE.sMin && v <= EColorHSV.COLOR_PURPLE.vMax && v >= EColorHSV.COLOR_PURPLE.vMin) {
            EColorHSV.COLOR_PURPLE //紫色
        } else if (h <= EColorHSV.COLOR_PINK.hMax && h >= EColorHSV.COLOR_PINK.hMin && s <= EColorHSV.COLOR_PINK.sMax && s >= EColorHSV.COLOR_PINK.sMin && v <= EColorHSV.COLOR_PINK.vMax && v >= EColorHSV.COLOR_PINK.vMin) {
            EColorHSV.COLOR_PINK //粉色
        } else if (h <= EColorHSV.COLOR_PINKISH_RED.hMax && h >= EColorHSV.COLOR_PINKISH_RED.hMin && s <= EColorHSV.COLOR_PINKISH_RED.sMax && s >= EColorHSV.COLOR_PINKISH_RED.sMin && v <= EColorHSV.COLOR_PINKISH_RED.vMax && v >= EColorHSV.COLOR_PINKISH_RED.vMin) {
            EColorHSV.COLOR_PINKISH_RED //品红色
        } else null
    }
}