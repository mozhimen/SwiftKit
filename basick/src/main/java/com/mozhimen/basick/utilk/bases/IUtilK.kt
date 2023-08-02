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
 * 1. 获取 -> getXxx
 * 2. 应用 -> applyXxx
 * 3. 是否 -> isXxx/hasXxx
 * 4. 转化 -> xxx2xxx
 * 5. 其他 -> xxx
 * @property NAME String
 * @property TAG String
 */
interface IUtilK {
    val NAME: String get() = this.javaClass.simpleName
    val TAG: String get() = "$NAME>>>>>"
}