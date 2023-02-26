package com.mozhimen.uicorek.textk

import android.content.Context
import android.util.AttributeSet
import android.widget.TextClock
import com.mozhimen.uicorek.layoutk.commons.ILayoutK
import com.mozhimen.basick.utilk.device.UtilKDate
import com.mozhimen.uicorek.R


/**
 * @ClassName TextKClock
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/11 17:47
 * @Version 1.0
 */
class TextKClock @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : TextClock(context, attrs, defStyleAttr, defStyleRes), ILayoutK {
    private var _timeFormat: String = getTimeFormat()

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initFlag() {

    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKClock)
            _timeFormat = getTimeFormat(typedArray.getInt(R.styleable.TextKClock_textkClock_timeFormat, 0))
            typedArray.recycle()
        }
    }

    override fun initView() {
        format24Hour = _timeFormat
    }

    private fun getTimeFormat(index: Int = 0): String {
        return when (index) {
            1 -> UtilKDate.FORMAT_yyyyMMddHHmm
            2 -> UtilKDate.FORMAT_yyyyMMdd
            3 -> UtilKDate.FORMAT_HHmmss
            4 -> UtilKDate.FORMAT_HHmm
            5 -> UtilKDate.FORMAT_mmss
            6 -> UtilKDate.FORMAT_yyyy
            7 -> UtilKDate.FORMAT_MM
            8 -> UtilKDate.FORMAT_dd
            9 -> UtilKDate.FORMAT_HH
            10 -> UtilKDate.FORMAT_mm
            11 -> UtilKDate.FORMAT_ss
            else -> UtilKDate.FORMAT_yyyyMMddHHmmss
        }
    }
}