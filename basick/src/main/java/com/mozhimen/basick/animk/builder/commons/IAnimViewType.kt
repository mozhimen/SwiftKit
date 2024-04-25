package com.mozhimen.basick.animk.builder.commons

import android.view.View

/**
 * @ClassName IAnimViewType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/25
 * @Version 1.0
 */
interface IAnimViewType<T> {
    fun setViewRef(view: View): T
}