package com.mozhimen.uicorek.textk

import android.content.Context
import android.util.AttributeSet
import android.widget.TextClock
import com.mozhimen.uicorek.layoutk.commons.ILayoutK
import com.mozhimen.basick.utilk.os.UtilKDate
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
        format24Hour = _timeFormat
    }

    private fun getTimeFormat(index: Int = 0): String {
        return when (index) {
            1 -> UtilKDate.Format.yyyyMMddHHmm
            2 -> UtilKDate.Format.yyyyMMdd
            3 -> UtilKDate.Format.HHmmss
            4 -> UtilKDate.Format.HHmm
            5 -> UtilKDate.Format.mmss
            6 -> UtilKDate.Format.yyyy
            7 -> UtilKDate.Format.MM
            8 -> UtilKDate.Format.dd
            9 -> UtilKDate.Format.HH
            10 -> UtilKDate.Format.mm
            11 -> UtilKDate.Format.ss
            else -> UtilKDate.Format.yyyyMMddHHmmss
        }
    }
}