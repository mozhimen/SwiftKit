package com.mozhimen.basick.animk.builders

import com.mozhimen.basick.animk.builders.helpers.AnimationBuilder
import com.mozhimen.basick.animk.builders.helpers.AnimatorBuilder

/**
 * @ClassName AnimK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 23:58
 * @Version 1.0
 */
object AnimKBuilder {

    fun asAnimation(): AnimationBuilder {
        return AnimationBuilder()
    }

    fun asAnimator(): AnimatorBuilder {
        return AnimatorBuilder()
    }
}