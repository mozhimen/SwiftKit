package com.mozhimen.basick.animk.builder

import com.mozhimen.basick.animk.builder.helpers.AnimationBuilder
import com.mozhimen.basick.animk.builder.helpers.AnimatorBuilder

/**
 * @ClassName AnimK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 23:58
 * @Version 1.0
 */
object AnimKBuilder {

    @JvmStatic
    fun asAnimation(): AnimationBuilder {
        return AnimationBuilder()
    }

    @JvmStatic
    fun asAnimator(): AnimatorBuilder {
        return AnimatorBuilder()
    }
}