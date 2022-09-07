package com.mozhimen.uicorek.textk.bubble

import android.content.Context
import android.util.AttributeSet
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
    override fun setArrowDirection(arrowDirection: ITextKBubble.ArrowDirection) {
        _textKBubbleProxy.setArrowDirection(arrowDirection)
    }

    override fun getArrowDirection(): ITextKBubble.ArrowDirection =
        _textKBubbleProxy.getArrowDirection()

    override fun setArrowHeight(arrowHeight: Float) {
        _textKBubbleProxy.setArrowHeight(arrowHeight)
    }

    override fun getArrowHeight(): Float =
        _textKBubbleProxy.getArrowHeight()

    override fun setArrowWidth(arrowWidth: Float) {
        _textKBubbleProxy.setArrowWidth(arrowWidth)
    }

    override fun getArrowWidth(): Float =
        _textKBubbleProxy.getArrowWidth()

    override fun setArrowPosPolicy(arrowPosPolicy: ITextKBubble.ArrowPosPolicy) {
        _textKBubbleProxy.setArrowPosPolicy(arrowPosPolicy)
    }

    override fun getArrowPosPolicy(): ITextKBubble.ArrowPosPolicy =
        _textKBubbleProxy.getArrowPosPolicy()

    override fun setArrowPosOffset(offset: Float) {
        _textKBubbleProxy.setArrowPosOffset(offset)
    }

    override fun getArrowPosOffset(): Float =
        _textKBubbleProxy.getArrowPosOffset()

    override fun setArrowToByViewId(targetViewId: Int) {
        _textKBubbleProxy.setArrowToByViewId(targetViewId)
    }

    override fun getArrowToByViewId(): Int =
        _textKBubbleProxy.getArrowToByViewId()

    override fun setArrowTo(targetView: View) {
        _textKBubbleProxy.setArrowTo(targetView)
    }

    override fun getArrowTo(): View? =
        _textKBubbleProxy.getArrowTo()

    override fun setBgColor(bgColor: Int) {
        _textKBubbleProxy.setBgColor(bgColor)
    }

    override fun getBgColor(): Int =
        _textKBubbleProxy.getBgColor()

    override fun setBorderColor(borderColor: Int) {
        _textKBubbleProxy.setBorderColor(borderColor)
    }

    override fun getBorderColor(): Int =
        _textKBubbleProxy.getBorderColor()

    override fun setBorderWidth(borderWidth: Float) {
        _textKBubbleProxy.setBorderWidth(borderWidth)
    }

    override fun getBorderWidth(): Float =
        _textKBubbleProxy.getBorderWidth()

    override fun setGapPadding(gapPadding: Float) {
        _textKBubbleProxy.setGapPadding(gapPadding)
    }

    override fun getGapPadding(): Float =
        _textKBubbleProxy.getGapPadding()

    override fun setCornerRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        _textKBubbleProxy.setCornerRadius(topLeft, topRight, bottomRight, bottomLeft)
    }

    override fun setCornerRadius(radius: Float) {
        _textKBubbleProxy.setCornerRadius(radius)
    }

    override fun getCornerTopLeftRadius(): Float =
        _textKBubbleProxy.getCornerTopLeftRadius()

    override fun getCornerTopRightRadius(): Float =
        _textKBubbleProxy.getCornerTopRightRadius()

    override fun getCornerBottomLeftRadius(): Float =
        _textKBubbleProxy.getCornerBottomLeftRadius()

    override fun getCornerBottomRightRadius(): Float =
        _textKBubbleProxy.getCornerBottomRightRadius()

    override fun setPadding(padding: Float) {
        _textKBubbleProxy.setPadding(padding)
    }

    override fun setPadding(paddingHorizontal: Float, paddingVertical: Float) {
        _textKBubbleProxy.setPadding(paddingHorizontal, paddingVertical)
    }

    override fun setPadding(left: Float, top: Float, right: Float, bottom: Float) {
        _textKBubbleProxy.setPadding(left, top, right, bottom)
    }

    override fun getPaddingLeft(): Int =
        _textKBubbleProxy.getPaddingLeft()

    override fun getPaddingTop(): Int =
        _textKBubbleProxy.getPaddingTop()

    override fun getPaddingRight(): Int =
        _textKBubbleProxy.getPaddingRight()

    override fun getPaddingBottom(): Int =
        _textKBubbleProxy.getPaddingBottom()

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