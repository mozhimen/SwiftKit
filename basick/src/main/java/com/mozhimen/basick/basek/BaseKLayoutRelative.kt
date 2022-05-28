package com.mozhimen.basick.basek

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.mozhimen.basick.basek.commons.IBaseKLayout

/**
 * @ClassName ILayout
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/24 14:39
 * @Version 1.0
 */
abstract class BaseKLayoutRelative @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr), IBaseKLayout {

    var TAG = "${this.javaClass.simpleName}>>>>>"

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}
    override fun initView() {}
}