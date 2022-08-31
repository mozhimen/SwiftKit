package com.mozhimen.uicorek.textk

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.basick.basek.commons.IBaseKView

/**
 * @ClassName TextKMarquee
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/31 17:41
 * @Version 1.0
 */
class TextKMarquee @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr), IBaseKView {

    init {
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {

    }

    override fun initPaint() {

    }

    override fun initData() {

    }

    override fun initView() {
        this.ellipsize = TextUtils.TruncateAt.MARQUEE;
        this.isSingleLine = true;
        this.marqueeRepeatLimit = -1;
    }

    override fun isFocused(): Boolean =
        true
}