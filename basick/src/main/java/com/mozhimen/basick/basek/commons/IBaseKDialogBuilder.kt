package com.mozhimen.basick.basek.commons

import android.view.ViewGroup

/**
 * @ClassName BaseKDialogBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/20 17:03
 * @Version 1.0
 */
interface IBaseKDialogBuilder {
    var layoutId: Int

    var width: Float//单位: dp

    var height: Float//单位: dp

    var layoutParams: ViewGroup.LayoutParams

    var animStyleId: Int?

    var styleId: Int

    var cancelable: Boolean
}