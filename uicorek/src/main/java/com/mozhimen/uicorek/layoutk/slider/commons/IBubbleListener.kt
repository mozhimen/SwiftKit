package com.mozhimen.uicorek.layoutk.slider.commons

import android.widget.EditText
import com.mozhimen.uicorek.layoutk.slider.LayoutKSlider

/**
 * @ClassName BubbleClickedListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:54
 * @Version 1.0
 */
interface IBubbleListener {
    fun onClick(layoutKSlider: LayoutKSlider)
    fun onEdit(editText: EditText)
}