package com.mozhimen.uicorek.layoutk.commons

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.mozhimen.basicsk.utilk.UtilKDisplay

/**
 * @ClassName ILayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class LayoutKLinear(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), ILayoutK {

    var TAG = "LayoutKLinear>>>>>"

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    override fun initView() {}

    fun Float.dp2px(): Int {
        return UtilKDisplay.dp2px(this)
    }

    fun Float.sp2px(): Int {
        return UtilKDisplay.sp2px(this)
    }
}