package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubbleListener

/**
 * @ClassName TextKBubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:09
 * @Version 1.0
 */
class TextKBubble @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr), ITextKBubble, ITextKBubbleListener {

    //region # variate
    private val TAG = "TextKBubble>>>>>"
    private var _textKBubbleProxy: TextKBubbleProxy = TextKBubbleProxy()
    override var arrowDirection: ITextKBubble.ArrowDirection
        get() = _textKBubbleProxy.arrowDirection
        set(value) {
            _textKBubbleProxy.arrowDirection = value
        }
    override var arrowHeight: Float
        get() = _textKBubbleProxy.arrowHeight
        set(value) {
            _textKBubbleProxy.arrowHeight = value
        }
    override var arrowWidth: Float
        get() = _textKBubbleProxy.arrowWidth
        set(value) {
            _textKBubbleProxy.arrowWidth = value
        }
    override var arrowPosPolicy: ITextKBubble.ArrowPosPolicy
        get() = _textKBubbleProxy.arrowPosPolicy
        set(value) {
            _textKBubbleProxy.arrowPosPolicy = value
        }
    override var arrowPosOffset: Float
        get() = _textKBubbleProxy.arrowPosOffset
        set(value) {
            _textKBubbleProxy.arrowPosOffset = value
        }
    override var arrowToViewId: Int
        get() = _textKBubbleProxy.arrowToViewId
        set(value) {
            _textKBubbleProxy.arrowToViewId = value
        }
    override var arrowToView: View?
        get() = _textKBubbleProxy.arrowToView
        set(value) {
            _textKBubbleProxy.arrowToView = value
        }
    override var bgColor: Int
        get() = _textKBubbleProxy.bgColor
        set(value) {
            _textKBubbleProxy.bgColor = value
        }
    override var borderColor: Int
        get() = _textKBubbleProxy.borderColor
        set(value) {
            _textKBubbleProxy.borderColor = value
        }
    override var borderWidth: Float
        get() = _textKBubbleProxy.borderWidth
        set(value) {
            _textKBubbleProxy.borderWidth = value
        }
    override var gapPadding: Float
        get() = _textKBubbleProxy.gapPadding
        set(value) {
            _textKBubbleProxy.gapPadding = value
        }
    override var cornerTopLeftRadius: Float
        get() = _textKBubbleProxy.cornerTopLeftRadius
        set(value) {
            _textKBubbleProxy.cornerTopLeftRadius = value
        }
    override var cornerTopRightRadius: Float
        get() = _textKBubbleProxy.cornerTopRightRadius
        set(value) {
            _textKBubbleProxy.cornerTopRightRadius = value
        }
    override var cornerBottomLeftRadius: Float
        get() = _textKBubbleProxy.cornerBottomLeftRadius
        set(value) {
            _textKBubbleProxy.cornerBottomLeftRadius = value
        }
    override var cornerBottomRightRadius: Float
        get() = _textKBubbleProxy.cornerBottomRightRadius
        set(value) {
            _textKBubbleProxy.cornerBottomRightRadius = value
        }
    override var paddingLeft: Float
        get() = _textKBubbleProxy.paddingLeft
        set(value) {
            _textKBubbleProxy.paddingLeft = value
        }
    override var paddingTop: Float
        get() = _textKBubbleProxy.paddingTop
        set(value) {
            _textKBubbleProxy.paddingTop = value
        }
    override var paddingRight: Float
        get() = _textKBubbleProxy.paddingRight
        set(value) {
            _textKBubbleProxy.paddingRight = value
        }
    override var paddingBottom: Float
        get() = _textKBubbleProxy.paddingBottom
        set(value) {
            _textKBubbleProxy.paddingBottom = value
        }

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _textKBubbleProxy.setCornerRadius(topLeft, topRight, bottomRight, bottomLeft)
    }

    override fun setCornerRadius(radius: Float) {
        _textKBubbleProxy.setCornerRadius(radius)
    }

    override fun setPadding(padding: Float) {
        _textKBubbleProxy.setPadding(padding)
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        _textKBubbleProxy.setPadding(paddingHorizontal, paddingVertical)
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        _textKBubbleProxy.setPadding(left, top, right, bottom)
    }

    override fun requestUpdateBubble() {
        _textKBubbleProxy.requestUpdateBubble()
    }

    override fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
    }

    override fun getSuperPaddingLeft(): Int {
        return super.getPaddingLeft()
    }

    override fun getSuperPaddingTop(): Int {
        return super.getPaddingTop()
    }

    override fun getSuperPaddingRight(): Int {
        return super.getPaddingRight()
    }

    override fun getSuperPaddingBottom(): Int {
        return super.getPaddingBottom()
    }
    //endregion

    init {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        _textKBubbleProxy.init(this, context, attrs)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        _textKBubbleProxy.updateDrawable(right - left, bottom - top, true)
    }
}