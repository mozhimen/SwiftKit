package com.mozhimen.basick.elemk.android.view.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.view.cons.CGravity


/**
 * @ClassName AGravity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 17:44
 * @Version 1.0
 */
@IntDef(
    CGravity.NO_GRAVITY,
    CGravity.AXIS_PULL_BEFORE,
    CGravity.AXIS_PULL_AFTER,
    CGravity.TOP,
    CGravity.BOTTOM,
    CGravity.LEFT,
    CGravity.RIGHT,
    CGravity.CENTER_VERTICAL,
    CGravity.FILL_VERTICAL,
    CGravity.CENTER_HORIZONTAL,
    CGravity.FILL_HORIZONTAL,
    CGravity.CENTER,
    CGravity.FILL,
    CGravity.CLIP_VERTICAL,
    CGravity.CLIP_HORIZONTAL,
    CGravity.RELATIVE_LAYOUT_DIRECTION,
    CGravity.DISPLAY_CLIP_VERTICAL,
    CGravity.DISPLAY_CLIP_HORIZONTAL,
    CGravity.START,
    CGravity.END,
    CGravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
)
annotation class AGravity()
