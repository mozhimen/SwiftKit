package com.mozhimen.basick.utilk.bases

/**
 * @ClassName IUtilK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 19:29
 * @Version 1.0
 */
/**
 * Util工具类的方法集可分为三类:
 *
 * 1. 获取 -> getXxx(不用带主语)
 * 2. 应用 -> applyXxx(不用带主语)
 * 3. 是否 -> isXxx/hasXxx(不用带主语)
 * 4. 转化 -> xxx2xxx(带主语和宾语即什么转化为什么)
 * 5. 其他 -> xxx
 *
 * 这里的主语即类名UtilKXXX中的XXX
 *
 * @property NAME String
 * @property TAG String
 */
interface IUtilK {
    val NAME: String get() = this.javaClass.simpleName
    val TAG: String get() = "$NAME>>>>>"
}