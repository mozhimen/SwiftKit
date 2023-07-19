package com.mozhimen.basick.utilk.bases

/**
 * @ClassName IUtilK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 19:29
 * @Version 1.0
 */
interface IUtilK {
    val NAME :String get() = this.javaClass.simpleName
    val TAG: String get() = "$NAME>>>>>"
}