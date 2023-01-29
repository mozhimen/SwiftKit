package com.mozhimen.basick.utilk.exts

import android.view.View
import com.mozhimen.basick.utilk.anim.UtilKAnim

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