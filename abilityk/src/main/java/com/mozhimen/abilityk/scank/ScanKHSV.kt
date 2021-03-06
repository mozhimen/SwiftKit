package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import com.mozhimen.abilityk.scank.mos.ColorHSV
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.extsk.toJson
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.utilk.UtilKRes
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @ClassName ScanKHSV
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/10 18:23
 * @Version 1.0
 */
object ScanKHSV {

    private const val TAG = "ScanKHSV>>>>>"

    private val _colorMap = HashMap<ColorHSV, Int>()

    /**
     * 检查图片资源的颜色占比
     * @param resId Int 资源文件夹中的索引id
     * @return Map<String, Int>?
     */
    @JvmStatic
    fun colorAnalyze(resId: Int): List<Pair<ColorHSV, Int>>? {
        return colorAnalyze(UtilKRes.getDrawable(resId)!!.drawable2Bitmap())
    }

    /**
     * 检查图片资源的颜色占比
     * @param path String
     * @return Map<String, Int>?
     */
    fun colorAnalyze(path: String): List<Pair<ColorHSV, Int>>? {
        val outputStream: FileOutputStream? = null
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(path)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            return colorAnalyze(bitmap)//开始分析颜色
        } catch (e: Exception) {
            LogK.et(TAG, "colorAnalyze Exception ${e.message}")
            e.printStackTrace()
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush()
                    outputStream.close()
                } catch (e: Exception) {
                    LogK.et(TAG, "colorAnalyze read outputStream Exception $e")
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: Exception) {
                    LogK.et(TAG, "colorAnalyze read inputStream Exception $e")
                }
            }
        }
        return null
    }

    /**
     * 检查图片资源的颜色占比
     * @param bitmap Bitmap
     * @return Map<String, Int>?
     */
    @JvmStatic
    fun colorAnalyze(bitmap: Bitmap): List<Pair<ColorHSV, Int>>? {
        _colorMap.clear()
        try {
            val rows = bitmap.width
            val cols = bitmap.height
            var key: ColorHSV?
            var r: Int
            var g: Int
            var b: Int
            for (x in 0 until rows) {
                for (y in 0 until cols) {
                    r = Color.red(bitmap.getPixel(x, y))
                    g = Color.green(bitmap.getPixel(x, y))
                    b = Color.blue(bitmap.getPixel(x, y))
                    val hsv = rgb2Hsv(intArrayOf(r, g, b))
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
            Log.d(TAG, "colorAnalyze: colorMap ${_colorMap.toJson()}")
            return colorPercentage(rows * cols)
        } catch (e: Exception) {
            LogK.et(TAG, "colorAnalyze Exception $e")
        }
        return null
    }

    fun rgb2Hsv(rgb: IntArray): FloatArray {
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
    private fun colorPercentage(total: Int): List<Pair<ColorHSV, Int>> {
        val result = ArrayList<Pair<ColorHSV, Int>>()
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
    private fun colorMatch(h: Int, s: Int, v: Int): ColorHSV? {
        return if (h <= ColorHSV.COLOR_RED.hMax && h >= ColorHSV.COLOR_RED.hMin && s <= ColorHSV.COLOR_RED.sMax && s >= ColorHSV.COLOR_RED.sMin && v <= ColorHSV.COLOR_RED.vMax && v >= ColorHSV.COLOR_RED.vMin) {
            ColorHSV.COLOR_RED //红色
        } else if (h <= ColorHSV.COLOR_ORANGE.hMax && h >= ColorHSV.COLOR_ORANGE.hMin && s <= ColorHSV.COLOR_ORANGE.sMax && s >= ColorHSV.COLOR_ORANGE.sMin && v <= ColorHSV.COLOR_ORANGE.vMax && v >= ColorHSV.COLOR_ORANGE.vMin) {
            ColorHSV.COLOR_ORANGE //橙色
        } else if (h <= ColorHSV.COLOR_YELLOW.hMax && h >= ColorHSV.COLOR_YELLOW.hMin && s <= ColorHSV.COLOR_YELLOW.sMax && s >= ColorHSV.COLOR_YELLOW.sMin && v <= ColorHSV.COLOR_YELLOW.vMax && v >= ColorHSV.COLOR_YELLOW.vMin) {
            ColorHSV.COLOR_YELLOW //黄色
        } else if (h <= ColorHSV.COLOR_GREEN.hMax && h >= ColorHSV.COLOR_GREEN.hMin && s <= ColorHSV.COLOR_GREEN.sMax && s >= ColorHSV.COLOR_GREEN.sMin && v <= ColorHSV.COLOR_GREEN.vMax && v >= ColorHSV.COLOR_GREEN.vMin) {
            ColorHSV.COLOR_GREEN //绿色
        } else if (h <= ColorHSV.COLOR_CYAN.hMax && h >= ColorHSV.COLOR_CYAN.hMin && s <= ColorHSV.COLOR_CYAN.sMax && s >= ColorHSV.COLOR_CYAN.sMin && v <= ColorHSV.COLOR_CYAN.vMax && v >= ColorHSV.COLOR_CYAN.vMin) {
            ColorHSV.COLOR_CYAN //青色
        } else if (h <= ColorHSV.COLOR_BLUE.hMax && h >= ColorHSV.COLOR_BLUE.hMin && s <= ColorHSV.COLOR_BLUE.sMax && s >= ColorHSV.COLOR_BLUE.sMin && v <= ColorHSV.COLOR_BLUE.vMax && v >= ColorHSV.COLOR_BLUE.vMin) {
            ColorHSV.COLOR_BLUE //蓝色
        } else if (h <= ColorHSV.COLOR_PURPLE.hMax && h >= ColorHSV.COLOR_PURPLE.hMin && s <= ColorHSV.COLOR_PURPLE.sMax && s >= ColorHSV.COLOR_PURPLE.sMin && v <= ColorHSV.COLOR_PURPLE.vMax && v >= ColorHSV.COLOR_PURPLE.vMin) {
            ColorHSV.COLOR_PURPLE //紫色
        } else if (h <= ColorHSV.COLOR_PINK.hMax && h >= ColorHSV.COLOR_PINK.hMin && s <= ColorHSV.COLOR_PINK.sMax && s >= ColorHSV.COLOR_PINK.sMin && v <= ColorHSV.COLOR_PINK.vMax && v >= ColorHSV.COLOR_PINK.vMin) {
            ColorHSV.COLOR_PINK //粉色
        } else if (h <= ColorHSV.COLOR_PINKISH_RED.hMax && h >= ColorHSV.COLOR_PINKISH_RED.hMin && s <= ColorHSV.COLOR_PINKISH_RED.sMax && s >= ColorHSV.COLOR_PINKISH_RED.sMin && v <= ColorHSV.COLOR_PINKISH_RED.vMax && v >= ColorHSV.COLOR_PINKISH_RED.vMin) {
            ColorHSV.COLOR_PINKISH_RED //品红色
        } else null
    }
}