package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.res.UtilKDisplay

/**
 * @ClassName ExtskDisplay
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 19:00
 * @Version 1.0
 */
/**
 * dp转px
 */
val Float.dp2px
    get() = UtilKDisplay.dp2px(this)

/**
 * dp转px
 * @receiver Float
 * @return Int
 */
fun Float.dp2px(): Float =
    UtilKDisplay.dp2px(this)

/**
 * dp转px
 * @receiver Int
 * @return Int
 */
fun Int.dp2px(): Float =
    UtilKDisplay.dp2px(this.toFloat())

/**
 * sp转px
 * @receiver Float
 * @return Int
 */
fun Float.sp2px(): Float =
    UtilKDisplay.sp2px(this)

/**
 * sp转px
 * @receiver Int
 * @return Int
 */
fun Int.sp2px(): Float =
    UtilKDisplay.sp2px(this.toFloat())

/**
 * px转dp
 * @receiver Int
 * @return Float
 */
fun Int.px2dp(): Float =
    UtilKDisplay.px2dp(this.toFloat())

/**
 * px转dp
 * @receiver Float
 * @return Float
 */
fun Float.px2dp(): Float =
    UtilKDisplay.px2dp(this)

/**
 * px转sp
 * @receiver Int
 * @return Int
 */
fun Int.px2sp(): Float =
    UtilKDisplay.px2sp(this.toFloat())

/**
 * px转sp
 * @receiver Float
 * @return Int
 */
fun Float.px2sp(): Float =
    UtilKDisplay.px2sp(this)