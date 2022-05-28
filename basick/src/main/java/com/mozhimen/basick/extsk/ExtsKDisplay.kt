package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.UtilKDisplay

/**
 * @ClassName ExtskDisplay
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 19:00
 * @Version 1.0
 */
/**
 *
 * @receiver Float
 * @return Int
 */
fun Float.dp2px(): Int = UtilKDisplay.dp2px(this)

/**
 *
 * @receiver Float
 * @return Int
 */
fun Float.sp2px(): Int = UtilKDisplay.sp2px(this)

/**
 *
 * @receiver Int
 * @return Int
 */
fun Int.dp2px(): Int = UtilKDisplay.dp2px(this.toFloat())

/**
 *
 * @receiver Int
 * @return Int
 */
fun Int.sp2px(): Int = UtilKDisplay.sp2px(this.toFloat())