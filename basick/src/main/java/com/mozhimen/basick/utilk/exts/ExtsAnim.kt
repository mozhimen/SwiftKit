package com.mozhimen.basick.utilk.exts

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.mozhimen.basick.utilk.UtilKAnim

/**
 * @ClassName ExtsKAnim
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/18 9:06
 * @Version 1.0
 */
/**
 * 结束动画
 * @receiver View
 */
fun View.stopAnim() {
    UtilKAnim.stopAnim(this)
}