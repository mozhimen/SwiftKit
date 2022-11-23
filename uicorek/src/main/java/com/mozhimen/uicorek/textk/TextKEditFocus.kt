package com.mozhimen.uicorek.textk

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.commons.ILayoutK

/**
 * @ClassName TextKEditBorder
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 11:21
 * @Version 1.0
 */
class TextKEditFocus @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatEditText(context, attrs, defStyleAttr), View.OnFocusChangeListener,
    ILayoutK {

    private var _focusBackground: Int = R.drawable.textk_edit_form_focus
    private var _unFocusBackground: Int = R.drawable.textk_edit_form

    init {
        initAttrs(attrs, defStyleAttr)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TextKEditFocus)
            _focusBackground = typedArray.getResourceId(R.styleable.TextKEditFocus_textKEditFocus_focusBackground, 0)
            _unFocusBackground = typedArray.getResourceId(R.styleable.TextKEditFocus_textKEditFocus_unFocusBackground, 0)
            typedArray.recycle()
        }
    }

    override fun initView() {
        this.onFocusChangeListener = this
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p1) {
            this.setBackgroundResource(_focusBackground)
        } else {
            this.setBackgroundResource(_unFocusBackground)
        }
    }
}