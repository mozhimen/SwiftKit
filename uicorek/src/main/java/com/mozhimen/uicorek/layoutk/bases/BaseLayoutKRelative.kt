package com.mozhimen.uicorek.layoutk.bases

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.mozhimen.uicorek.layoutk.commons.ILayoutK

/**
 * @ClassName LayoutKRelative
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class BaseLayoutKRelative @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr, defStyleRes), ILayoutK {

    protected var TAG = "${this.javaClass.simpleName}>>>>>"

    override fun initFlag() {}
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    override fun initView() {}
}