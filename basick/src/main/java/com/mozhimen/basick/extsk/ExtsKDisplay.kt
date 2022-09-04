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
 * sp转px
 * @receiver Float
 * @return Int
 */
fun Float.dp2px(): Int = UtilKDisplay.dp2px(this)

/**
 * sp转px
 * @receiver Float
 * @return Int
 */
fun Float.sp2px(): Int = UtilKDisplay.sp2px(this)

/**
 * dp转px
 * @receiver Int
 * @return Int
 */
fun Int.dp2px(): Int = UtilKDisplay.dp2px(this.toFloat())

/**
 * sp转px
 * @receiver Int
 * @return Int
 */
fun Int.sp2px(): Int = UtilKDisplay.sp2px(this.toFloat())

/**
 * px转dp
 * @receiver Int
 * @return Float
 */
fun Int.px2dp(): Float = UtilKDisplay.px2dp(this.toFloat())

/**
 * px转dp
 * @receiver Float
 * @return Float
 */
fun Float.px2dp(): Float = UtilKDisplay.px2dp(this)