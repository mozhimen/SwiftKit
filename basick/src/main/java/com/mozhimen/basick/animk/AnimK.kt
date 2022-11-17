package com.mozhimen.basick.animk

import com.mozhimen.basick.animk.helpers.AnimationBuilder
import com.mozhimen.basick.animk.helpers.AnimatorBuilder

/**
 * @ClassName AnimK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/24 23:58
 * @Version 1.0
 */
object AnimK {

    fun asAnimation(): AnimationBuilder {
        return AnimationBuilder()
    }

    fun asAnimator(): AnimatorBuilder {
        return AnimatorBuilder()
    }

//    init {
//        owner.lifecycle.addObserver(this)
//    }

//    fun with(view: View): UtilKAnim {
//        _views.append(view.id, view)
//        return UtilKAnim
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//        _views.forEach { _, view ->
//            view.stopAnim()
//        }
//        _views.clear()
//        owner.lifecycle.removeObserver(this)
//        super.onPause(owner)
//    }
}