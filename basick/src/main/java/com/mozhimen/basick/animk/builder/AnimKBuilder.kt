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
class AnimKBuilder {

    fun asAnimation(): AnimationBuilder {
        return AnimationBuilder()
    }

    fun asAnimator(): AnimatorBuilder {
        return AnimatorBuilder()
    }
}