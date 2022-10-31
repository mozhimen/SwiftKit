package com.mozhimen.abilityk.opencvk.cons

/**
 * @ClassName OpenCVKColorHSV
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 12:51
 * @Version 1.0.0
 */
enum class EColorHSV(
    val hMin: Double,
    val hMax: Double,
    val sMin: Double,
    val sMax: Double,
    val vMin: Double,
    val vMax: Double,
    val colorName: String
) {
    COLOR_BLACK(0.0, 180.0, 0.0, 255.0, 0.0, 46.0, "黑色"),
    COLOR_GRAY(0.0, 180.0, 0.0, 43.0, 46.0, 220.0, "灰色"),
    COLOR_WHITE(0.0, 180.0, 0.0, 30.0, 221.0, 255.0, "白色"),
    COLOR_RED(0.0, 10.0, 43.0, 255.0, 46.0, 255.0, "红色"),
    COLOR_ORANGE(11.0, 25.0, 43.0, 255.0, 46.0, 255.0, "橙色"),
    COLOR_YELLOW(26.0, 34.0, 43.0, 255.0, 46.0, 255.0, "黄色"),
    COLOR_GREEN(35.0, 77.0, 43.0, 255.0, 46.0, 255.0, "绿色"),
    COLOR_CYAN(78.0, 99.0, 43.0, 255.0, 46.0, 255.0, "青色"),
    COLOR_BLUE(100.0, 124.0, 43.0, 255.0, 46.0, 255.0, "蓝色"),
    COLOR_PURPLE(125.0, 150.0, 43.0, 255.0, 46.0, 255.0, "紫色"),
    COLOR_PINKISH_RED(156.0, 180.0, 43.0, 255.0, 46.0, 255.0, "品红色")
}