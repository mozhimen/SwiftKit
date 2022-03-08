package com.mozhimen.uicoremk.layoutmk.commons

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.mozhimen.basicsmk.utilmk.UtilMKDisplay

/**
 * @ClassName ILayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class LayoutMKLinear(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), ILayoutMK {

    var TAG = "LayoutMKLinear>>>>>"

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    override fun initView() {}

    fun Float.dp2px(): Int {
        return UtilMKDisplay.dp2px(this)
    }

    fun Float.sp2px(): Int {
        return UtilMKDisplay.sp2px(this)
    }
}