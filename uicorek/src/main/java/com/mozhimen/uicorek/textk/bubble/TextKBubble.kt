package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.mozhimen.basick.basek.commons.IBaseKView
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
    private var _textKBubbleProxy: TextKBubbleProxy = TextKBubbleProxy()
    override var arrowDirection: ITextKBubble.ArrowDirection
        get() = _textKBubbleProxy.arrowDirection
        set(value) {
            _textKBubbleProxy.arrowDirection = value
        }
    override var arrowHeight: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowWidth: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowPosPolicy: ITextKBubble.ArrowPosPolicy
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowPosOffset: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowToViewId: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowToView: View?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var bgColor: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var borderColor: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var borderWidth: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var gapPadding: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        TODO("Not yet implemented")
    }

    override fun setCornerRadius(radius: Float) {
        TODO("Not yet implemented")
    }

    override var cornerTopLeftRadius: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cornerTopRightRadius: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cornerBottomLeftRadius: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cornerBottomRightRadius: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun setPadding(padding: Float) {
        TODO("Not yet implemented")
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        TODO("Not yet implemented")
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        TODO("Not yet implemented")
    }

    override var paddingLeft: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingTop: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingRight: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingBottom: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun requestUpdateBubble() {
        TODO("Not yet implemented")
    }

    override fun setSuperPadding(left: Int, top: Int, right: Int, bottom: Int) {
        TODO("Not yet implemented")
    }

    override fun getSuperPaddingLeft(): Int {
        TODO("Not yet implemented")
    }

    override fun getSuperPaddingTop(): Int {
        TODO("Not yet implemented")
    }

    override fun getSuperPaddingRight(): Int {
        TODO("Not yet implemented")
    }

    override fun getSuperPaddingBottom(): Int {
        TODO("Not yet implemented")
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