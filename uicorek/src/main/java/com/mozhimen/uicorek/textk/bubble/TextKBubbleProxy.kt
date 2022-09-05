package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubble
import com.mozhimen.uicorek.textk.bubble.commons.ITextKBubbleListener
import java.lang.Exception

/**
 * @ClassName TextKBubbleProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/5 13:19
 * @Version 1.0
 */
class TextKBubbleProxy : ITextKBubble {
    //region # variate
    private lateinit var _parentView: View
    private var _bubbleListener: ITextKBubbleListener? = null
    override var arrowDirection: ITextKBubble.ArrowDirection
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowHeight: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowWidth: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowPosPolicy: ITextKBubble.ArrowPosPolicy
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowPosDelta: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var arrowTo: View
        get() = TODO("Not yet implemented")
        set(value) {}
    override var fillColor: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var borderColor: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var borderWidth: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override var fillPadding: Float
        get() = TODO("Not yet implemented")
        set(value) {}
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
    override var paddingLeft: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingTop: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingRight: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var paddingBottom: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    //endregion
    fun init(view: View, context: Context, attrs: AttributeSet?) {
        try {
            _parentView = view
            _bubbleListener = view as ITextKBubbleListener
            attrs?.let {

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setArrowTo(viewId: Int) {
        TODO("Not yet implemented")
    }

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        TODO("Not yet implemented")
    }

    override fun setCornerRadius(radius: Float) {
        TODO("Not yet implemented")
    }

    override fun setPadding(padding: Int) {
        TODO("Not yet implemented")
    }

    override fun setPadding(paddingHorizontal: Int, paddingVertical: Int) {
        TODO("Not yet implemented")
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        TODO("Not yet implemented")
    }

    override fun requestUpdateBubble() {
        TODO("Not yet implemented")
    }
}