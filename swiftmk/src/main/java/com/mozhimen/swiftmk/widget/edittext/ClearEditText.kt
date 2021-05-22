package com.mozhimen.swiftmk.widget.edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import com.mozhimen.swiftmk.R

/**
 * @ClassName ClearEditText
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/22 14:58
 * @Version 1.0
 */
class ClearEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) :
    AppCompatEditText(context, attrs, defStyle), OnFocusChangeListener, TextWatcher {

    //EditText右侧的删除按钮
    private var clearDrawable: Drawable? = compoundDrawables[2]
    private var hasFoucs = false

    init {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,获取图片的顺序是左上右下（0,1,2,3,）
        if (clearDrawable == null) {
            clearDrawable = resources.getDrawable(R.drawable.icon_round_delete)
        }
        clearDrawable!!.let {
            it.setBounds(
                0,
                0,
                it.intrinsicWidth,
                it.intrinsicHeight
            )
        }
        // 默认设置隐藏图标
        setClearIconVisible(false)
        // 设置焦点改变的监听
        onFocusChangeListener = this
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    /**
     * @说明：isInnerWidth, isInnerHeight为ture，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     * getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     * getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     * distance 删除图标顶部边缘到控件顶部边缘的距离
     * distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val rect = compoundDrawables[2].bounds
                val height = rect.height()
                val distance = (getHeight() - height) / 2
                val isInnerWidth =
                    x > width - totalPaddingRight - 30 && x < width - paddingRight + 30
                val isInnerHeight = y > distance - 30 && y < distance + height + 30
                if (isInnerWidth && isInnerHeight) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 当ClearEditText焦点发生变化的时候，
     * 输入长度为零，隐藏删除图标，否则，显示删除图标
     */
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        hasFoucs = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    protected fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) clearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1], right, compoundDrawables[3]
        )
    }

    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (hasFoucs) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
}