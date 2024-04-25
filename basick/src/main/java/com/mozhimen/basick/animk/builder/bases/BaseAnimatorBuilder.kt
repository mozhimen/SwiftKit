package com.mozhimen.basick.animk.builder.bases

import android.animation.Animator
import android.animation.AnimatorSet

/**
 * @ClassName BaseAnimatorBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 18:46
 * @Version 1.0
 */
abstract class BaseAnimatorBuilder<BUILDER> : BaseAnimBuilder<BUILDER, Animator, AnimatorSet>()