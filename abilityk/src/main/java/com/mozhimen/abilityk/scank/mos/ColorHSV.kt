package com.mozhimen.abilityk.scank.mos

/**
 * @ClassName OpenCVKColorHSV
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/10 18:15
 * @Version 1.0
 */
enum class ColorHSV(
    val hMin: Int,
    val hMax: Int,
    val sMin: Int,
    val sMax: Int,
    val vMin: Int,
    val vMax: Int,
    val colorName: String
) {
    COLOR_RED(0, 15, 40, 255, 40, 255, "红色"),
    COLOR_ORANGE(15, 30, 40, 255, 40, 255, "橙色"),
    COLOR_YELLOW(30, 60, 40, 255, 40, 255, "黄色"),
    COLOR_GREEN(60, 150, 40, 255, 40, 255, "绿色"),
    COLOR_CYAN(150, 180, 40, 255, 40, 255, "青色"),
    COLOR_BLUE(180, 240, 40, 255, 40, 255, "蓝色"),
    COLOR_PURPLE(240, 300, 40, 255, 40, 255, "紫色"),
    COLOR_PINK(300, 330, 40, 255, 40, 255, "粉红色"),
    COLOR_PINKISH_RED(330, 335, 40, 255, 40, 255, "品红色")
}