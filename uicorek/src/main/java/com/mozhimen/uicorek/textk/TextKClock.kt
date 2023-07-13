package com.mozhimen.uicorek.textk

import android.content.Context
import android.util.AttributeSet
import android.widget.TextClock
import com.mozhimen.basick.elemk.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.uicorek.layoutk.commons.ILayoutK
import com.mozhimen.uicorek.R


/**
 * @ClassName TextKClock
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/11 17:47
 * @Version 1.0
 */
class TextKClock @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : TextClock(context, attrs, defStyleAttr, defStyleRes),
    ILayoutK {
    private var _timeFormat: String = getTimeFormat()

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKClock)
        _timeFormat = getTimeFormat(typedArray.getInt(R.styleable.TextKClock_textkClock_timeFormat, 0))
        typedArray.recycle()
    }

    override fun initView() {
        format24Hour = _timeFormat.also { it.dt(TAG) }
    }

    private fun getTimeFormat(index: Int = 0): String {
        return when (index) {
            0 -> CDateFormat.yyyyMMddHHmmss
            1 -> CDateFormat.yyyyMMddHHmm
            2 -> CDateFormat.yyyyMMdd
            3 -> CDateFormat.HHmmss
            4 -> CDateFormat.HHmm
            5 -> CDateFormat.mmss
            6 -> CDateFormat.yyyy
            7 -> CDateFormat.MM
            8 -> CDateFormat.dd
            9 -> CDateFormat.HH
            10 -> CDateFormat.mm
            11 -> CDateFormat.ss
            else -> CDateFormat.yyyyMMddHHmmss
        }
    }
}