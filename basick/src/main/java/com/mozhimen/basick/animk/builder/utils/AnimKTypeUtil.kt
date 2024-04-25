package com.mozhimen.basick.animk.builder.utils

import android.view.View
import com.mozhimen.basick.animk.builder.commons.IAnimatorUpdateListener
import com.mozhimen.basick.animk.builder.impls.AnimatorIntType

/**
 * @ClassName AnimKTypeUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/4/24 23:40
 * @Version 1.0
 */
object AnimKTypeUtil {
    @JvmStatic
    fun get_ofHeight(view: View, startHeight: Int, endHeight: Int): AnimatorIntType =
        AnimatorIntType().setInt(startHeight, endHeight).addAnimatorUpdateListener(object : IAnimatorUpdateListener<Int> {
            override fun onChange(value: Int?) {
                value?.let {
                    val lp = view.layoutParams
                    lp.height = it
                    view.layoutParams = lp
                }
            }
        })
}